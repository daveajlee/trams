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
              private gameService: GameService, private router: Router, private dom: DomSanitizer) { }

  /**
   * Initialise the stop information during construction and ensure all variables are set to the correct data.
   */
  ngOnInit(): void {
    // Save the stop information based on the id.
    if ( this.gameService.getGame().scenario.stopDistances ) {

    } else {

    }
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.id = +params['id'];
      if ( this.gameService.getGame().scenario.stopDistances ) {
        this.stop = new Stop('' + this.id, this.gameService.getGame().scenario.stopDistances[this.id].split(":")[0], 0, 0)
      } else {
        this.stop = this.stopsService.getStop(this.id);
        this.mapUrl = this.dom.bypassSecurityTrustResourceUrl('http://www.openstreetmap.org/export/embed.html?bbox='
            + (this.stop.longitude - 0.003) + ',' + (this.stop.latitude - 0.003) + ',' + (this.stop.longitude + 0.003) +
            ',' + (this.stop.latitude + 0.003) + '&layer=mapnik');
      }
    });
    // Determine current date & time
    const time = new Date(Date.now());
    const month = time.getMonth() + 1;
    let monthStr = String(month);
    if ( month < 10 ) { monthStr = '0' + month; }
    const dayOfMonth = time.getDate();
    this.today = time.getFullYear() + '-' + monthStr + '-' + dayOfMonth;
    this.hours = this.leftPadZero(time.getHours());
    this.minutes = this.leftPadZero(time.getMinutes());
    // Retrieve departures for first stop id.
    this.departuresSubscription = this.http.get<StopTimesResponse>(
        'http://localhost:8084/api/stopTimes/?arrivals=false&company=Company&departures=true&stopName=' + this.stop.name + '&date=' + this.today + '&startingTime=' +
        this.hours + ':' + this.minutes).subscribe(realTimeInfos => {
      this.departures = realTimeInfos.stopTimeResponses;
    });
    // Retrieve arrivals for first stop id.
    this.arrivalsSubscription = this.http.get<StopTimesResponse>('http://localhost:8084/api/stopTimes/?arrivals=true&company=Company&departures=false&stopName=' + this.stop.name + '&date=' + this.today + '&startingTime=' + this.hours + ':' + this.minutes).subscribe(realTimeInfos => {
      this.arrivals = realTimeInfos.stopTimeResponses;
    });
    // Retrieve departures for complete day.

    this.todaysDeparturesSubscription = this.http.get<StopTimesResponse>('http://localhost:8084/api/stopTimes/?arrivals=false&company=Company&departures=true&stopName=' + this.stop.name + '&date=' + this.today).subscribe(departureInfos => {
      this.todayDepartures = departureInfos.stopTimeResponses;
    });
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
    this.idSubscription.unsubscribe();
    this.departuresSubscription.unsubscribe();
    this.arrivalsSubscription.unsubscribe();
    this.todaysDeparturesSubscription.unsubscribe();
  }

}
