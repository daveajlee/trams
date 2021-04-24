import {Component, OnDestroy, OnInit} from '@angular/core';
import {Stop} from '../stop.model';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {StopsService} from '../stops.service';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {HttpClient} from '@angular/common/http';
import {RealTimeInfo} from './realtimeinfos.model';
import {Subscription} from 'rxjs';

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
   * @param router a navigational aid from Angular
   * @param dom a variable which ensures that security settings allow iframes.
   */
  constructor(private http: HttpClient, private stopsService: StopsService, private route: ActivatedRoute,
              private router: Router, private dom: DomSanitizer) { }

  /**
   * Initialise the stop information during construction and ensure all variables are set to the correct data.
   */
  ngOnInit(): void {
    // Save the stop information based on the id.
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.id = +params['id'];
      this.stop = this.stopsService.getStop(this.id);
      this.mapUrl = this.dom.bypassSecurityTrustResourceUrl('http://www.openstreetmap.org/export/embed.html?bbox='
          + (this.stop.longitude - 0.003) + ',' + (this.stop.latitude - 0.003) + ',' + (this.stop.longitude + 0.003) +
          ',' + (this.stop.latitude + 0.003) + '&layer=mapnik');
    });
    // Retrieve departures for first stop id.
    const time = new Date(Date.now());
    this.hours = this.leftPadZero(time.getHours());
    this.minutes = this.leftPadZero(time.getMinutes());
    this.departuresSubscription = this.http.get<RealTimeInfo[]>(
        'http://localhost:8080/trams-operations/departures?stopName=' + this.stop.name + '&startingTime=' +
        this.hours + ':' + this.minutes).subscribe(realTimeInfos => {
      this.departures = realTimeInfos;
    });
    // Retrieve arrivals for first stop id.
    this.arrivalsSubscription = this.http.get<RealTimeInfo[]>('http://localhost:8080/trams-operations/arrivals?stopName=' + this.stop.name + '&startingTime=' + this.hours + ':' + this.minutes).subscribe(realTimeInfos => {
      this.arrivals = realTimeInfos;
    });
    // Retrieve arrivals for first stop id.
    const month = time.getMonth() + 1;
    let monthStr = String(month);
    if ( month < 10 ) { monthStr = '0' + month; }
    const dayOfMonth = time.getDate();
    let dayOfMonthStr = String(dayOfMonth);
    if ( dayOfMonth < 10 ) { dayOfMonthStr = '0' + dayOfMonth; }
    this.today = time.getFullYear() + '-' + monthStr + '-' + dayOfMonth;
    this.todaysDeparturesSubscription = this.http.get<RealTimeInfo[]>('http://localhost:8080/trams-operations/' +
        'departuresByDate?stopName=' + this.stop.name + '&date=' + this.today).subscribe(departureInfos => {
      this.todayDepartures = departureInfos;
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
