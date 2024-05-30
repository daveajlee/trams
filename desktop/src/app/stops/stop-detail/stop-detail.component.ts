import {Component, OnDestroy, OnInit} from '@angular/core';
import {Stop} from '../stop.model';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {StopsService} from '../stops.service';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {HttpClient} from '@angular/common/http';
import {RealTimeInfo} from './realtimeinfos.model';
import {Subscription} from 'rxjs';
import {StopTimesResponse} from "./stoptimes-response.model";
import {GameService} from "../../shared/game.service";
import {TimeHelper} from "../../shared/time.helper";

@Component({
  selector: 'app-stop-detail',
  templateUrl: './stop-detail.component.html',
  styleUrls: ['./stop-detail.component.css']
})
/**
 * This class implements the functionality for the stop-details component which retrieves departure and arrival data from the server and
 * sends it to the frontend component for rendering.
 */
export class StopDetailComponent implements OnInit, OnDestroy {

  stop: Stop;
  id: number;

  hours: string;
  minutes: string;
  today: string;

  mapUrl: SafeResourceUrl;

  departures: RealTimeInfo[];
  todayDepartures: RealTimeInfo[];
  arrivals: RealTimeInfo[];

  idSubscription: Subscription;

  departuresSubscription: Subscription;
  arrivalsSubscription: Subscription;
  todaysDeparturesSubscription: Subscription;

  selectedDate: string;

  /**
   * Construct a new stop-detail component based on the supplied information.
   * @param http a http client which can retrieve data via http calls from the server.
   * @param stopsService a service which can retrieve and format stop information
   * @param route a variable which contains the current stop that the user clicked on.
   * @param gameService a service which retrieves game information
   * @param router a navigational aid from Angular
   * @param dom a variable which ensures that security settings allow iframes.
   */
  constructor(private http: HttpClient, private stopsService: StopsService, private route: ActivatedRoute,
              private gameService: GameService, private router: Router, private dom: DomSanitizer) {
    let currentDateTime = this.gameService.getGame().getCurrentDateTime();
    this.selectedDate = currentDateTime.getFullYear() + "-" + (currentDateTime.getMonth() < 10 ? "0"
            + currentDateTime.getMonth() : currentDateTime.getMonth() )  + "-" +
        (currentDateTime.getDate() < 10 ? "0"
            + currentDateTime.getDate() : currentDateTime.getDate() );
  }

  /**
   * Initialise the stop information during construction and ensure all variables are set to the correct data.
   */
  ngOnInit(): void {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.id = +params['id'];
      // Determine current date & time
      const time = this.gameService.getGame().getCurrentDateTime();
      const month = time.getMonth() + 1;
      let monthStr = String(month);
      if ( month < 10 ) { monthStr = '0' + month; }
      const dayOfMonth = time.getDate();
      this.today = time.getFullYear() + '-' + monthStr + '-' + dayOfMonth;
      this.hours = this.leftPadZero(time.getHours());
      this.minutes = this.leftPadZero(time.getMinutes());
      if ( this.gameService.isOfflineVersion() ) {
        this.stop = new Stop('' + this.id, this.gameService.getGame().getScenario().stopDistances[this.id].split(":")[0], 0, 0)
        console.log('Retrieving ' + this.stop.name);
        this.todayDepartures = this.retrieveDeparturesForDate(this.gameService.getGame().getCurrentDateTime());
        // Save the next 5 departures into the departures array and save the next 5 arrivals into the arrivals array.
        let currentTime = TimeHelper.formatTimeAsString(this.gameService.getGame().getCurrentDateTime());
        this.departures = []; this.arrivals = [];
        for ( let a = 0; a < this.todayDepartures.length; a++ ) {
          if ( this.todayDepartures[a].departureTime >= currentTime && this.departures.length < 5 ) {
            this.departures.push(this.todayDepartures[a]);
          }
          if ( this.todayDepartures[a].arrivalTime >= currentTime && this.arrivals.length < 5 ) {
            this.arrivals.push(this.todayDepartures[a]);
          }
        }
      } else {
        this.stop = this.stopsService.getStop(this.id);
        this.mapUrl = this.dom.bypassSecurityTrustResourceUrl('http://www.openstreetmap.org/export/embed.html?bbox='
            + (this.stop.longitude - 0.003) + ',' + (this.stop.latitude - 0.003) + ',' + (this.stop.longitude + 0.003) +
            ',' + (this.stop.latitude + 0.003) + '&layer=mapnik');
        this.departuresSubscription = this.http.get<StopTimesResponse>(
            this.gameService.getServerUrl() + '/stopTimes/?arrivals=false&company=Company&departures=true&stopName=' + this.stop.name + '&date=' + this.today + '&startingTime=' +
            this.hours + ':' + this.minutes).subscribe(realTimeInfos => {
          this.departures = realTimeInfos.stopTimeResponses;
        });
        // Retrieve arrivals for first stop id.
        this.arrivalsSubscription = this.http.get<StopTimesResponse>(this.gameService.getServerUrl() + '/stopTimes/?arrivals=true&company=Company&departures=false&stopName=' + this.stop.name + '&date=' + this.today + '&startingTime=' + this.hours + ':' + this.minutes).subscribe(realTimeInfos => {
          this.arrivals = realTimeInfos.stopTimeResponses;
        });
        // Retrieve departures for complete day.
        this.todaysDeparturesSubscription = this.http.get<StopTimesResponse>(this.gameService.getServerUrl() + '/stopTimes/?arrivals=false&company=Company&departures=true&stopName=' + this.stop.name + '&date=' + this.today).subscribe(departureInfos => {
          this.todayDepartures = departureInfos.stopTimeResponses;
        });
      }
    });
  }

  /**
   * Helper method to determine departures for a specific date.
   * @param date the date to retrieve departures for.
   */
  retrieveDeparturesForDate(date: Date): RealTimeInfo[] {
    console.log('I want to retrieve departures for ' + date);
    let departures = [];
    // Go through the routes,
    let routes = this.gameService.getGame().getRoutes();
    for ( let a = 0; a < routes.length; a++ ) {
      let schedules = routes[a].schedules;
      if ( routes[a].schedules ) {
        for ( let b = 0; b < schedules.length; b++ ) {
          let services = schedules[b].services;
          for ( let c = 0; c < services.length; c++ ) {
            let stops = services[c].stopList;
            for ( let d = 0; d < stops.length; d++ ) {
              if ( this.stop.name === stops[d].stop ) {
                // Exclude those stops which end here as they are not departures,
                if ( stops[d].stop != stops[stops.length-1].stop ) {
                  // This service stops here so now create the real time model and add it to today departures.
                  departures.push(new RealTimeInfo(stops[d].departureTime, stops[d].arrivalTime, routes[a].routeNumber, stops[stops.length-1].stop));
                }
              }
            }
          }
        }
      }
    }
    // Sort departures.
    departures.sort(this.sortDepartures);
    return departures;
  }

  /**
   * Retrieve the departures for the currently selected date.
   */
  getDeparturesForSpecificDate(): RealTimeInfo[] {
    var fullYear = this.selectedDate.split("-")[0];
    var month = this.selectedDate.split("-")[1];
    var date = this.selectedDate.split("-")[2];
    return this.retrieveDeparturesForDate(new Date(parseInt(fullYear), parseInt(month), parseInt(date), 0, 0, 0));
  }

  /**
   * Helper method to sort departures array.
   * @param first the first RealTimeInfo object to sort.
   * @param second the second RealTimeInfo object to sort.
   * @return an int which is -1 if the first before the second or 1 if second before first.
   */
  sortDepartures(first: RealTimeInfo, second: RealTimeInfo) {
    if ( first.departureTime < second.departureTime ) {
      return -1;
    } else if ( first.departureTime > second.departureTime ) {
      return 1;
    }
    return 0;
  }



  /**
   * Return the URL on OpenStreetMap which contains the map of this stop.
   */
  getOpenStreetMapUrl(): SafeResourceUrl {
    return this.mapUrl;
  }

  /**
   * Return the current departures for this stop.
   */
  getDepartures(): RealTimeInfo[] {
    return this.departures;
  }

  /**
   * Return the current arrivals for this stop.
   */
  getArrivals(): RealTimeInfo[] {
    return this.arrivals;
  }

  /**
   * Return all of today's departures for this stop.
   */
  getDeparturesByDate(): RealTimeInfo[] {
    return this.todayDepartures;
  }

  /**
   * Private helper method to add a 0 for numbers less than 10 in order to ensure two-digit numbers.
   * @param value a number which should be formatted into a two-digit number with leading 0 if needed.
   */
  private leftPadZero(value: number): string {
    return value < 10 ? `0${value}` : value.toString();
  }

  /**
   * When destroying this component we should ensure that all subscriptions are cancelled.
   */
  ngOnDestroy(): void {
    if ( !this.gameService.isOfflineVersion() ) {
      this.idSubscription.unsubscribe();
      this.departuresSubscription.unsubscribe();
      this.arrivalsSubscription.unsubscribe();
      this.todaysDeparturesSubscription.unsubscribe();
    }
  }

}
