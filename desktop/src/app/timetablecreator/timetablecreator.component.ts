import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {DatePipe} from "@angular/common";
import {FrequencyPattern} from "../shared/frequencypattern.model";
import {Timetable} from "../shared/timetable.model";

@Component({
  selector: 'app-timetablecreator',
  templateUrl: './timetablecreator.component.html',
  styleUrls: ['./timetablecreator.component.css']
})
export class TimetablecreatorComponent {

  routeNumber: string;
  timetableName: string;
  validFromDate: string;
  validToDate: string;
  gameService: GameService;

  frequencyPatternName: string;
  frequencyPatternStartStop: string;
  frequencyPatternEndStop: string;
  frequencyPatternStartTime: string;
  frequencyPatternEndTime: string;
  frequencyPatternFrequency: number;

  frequencyPatterns: FrequencyPattern[];

  constructor(private route: ActivatedRoute, private gameService2: GameService,
              public router: Router, private datePipe: DatePipe) {
    this.gameService = gameService2;
    // Valid from date is current date.
    this.validFromDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    // Valid to date is current date + 1 year.
    let oneFromYearNow = new Date(new Date().setFullYear(new Date().getFullYear() + 1));
    this.validToDate = this.datePipe.transform(oneFromYearNow, 'yyyy-MM-dd');
    // Set start time to 06:00
    this.frequencyPatternStartTime = "06:00";
    // set end time to 18:30
    this.frequencyPatternEndTime = "18:30";
    // Set frequency to 10.
    this.frequencyPatternFrequency = 10;
    // Create new array to store frequencies.
    this.frequencyPatterns = [];
  }

  /**
   * Copy parameters from last request so that we do not lose the information that the user has provided.
   */
  ngOnInit(): void {
    this.route.queryParams
        .subscribe(params => {
              this.routeNumber = params.routeNumber;
            }
        );
  }

  getRouteNumber(): string {
    return this.routeNumber;
  }

  getDaysOfWeek(): string[] {
    return ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]
  }

  getFrequencyPatternStartStops(): string[] {
    let possibleStops = [];
    let route = this.gameService.getGame().getRoute(this.routeNumber);
    if ( !this.frequencyPatternStartStop ) {
      this.frequencyPatternStartStop = route.startStop;
    }
    possibleStops.push(route.startStop);
    possibleStops = possibleStops.concat(route.stops);
    return possibleStops;
  }

  getFrequencyPatternEndStops(): string[] {
    let possibleStops = [];
    let route = this.gameService.getGame().getRoute(this.routeNumber);
    if ( !this.frequencyPatternEndStop ) {
      this.frequencyPatternEndStop = route.endStop;
    }
    possibleStops.push(route.endStop);
    possibleStops = possibleStops.concat(route.stops);
    return possibleStops;
  }

  getNumberVehicles() : number {
    // Calculate the duration.
    var duration = 0;
    let route = this.gameService.getGame().getRoute(this.routeNumber);
    if ( route.stops.length > 0 ) {
      duration += this.getDistanceBetweenStop(route.startStop, route.stops[0]);
      if ( route.stops.length > 1 ) {
        for ( var i = 0; i < route.stops.length-1; i++ ) {
          duration += this.getDistanceBetweenStop(route.stops[i], route.stops[i+1]);
        }
        duration += this.getDistanceBetweenStop(route.stops[route.stops.length-1], route.endStop);
      } else {
        duration += this.getDistanceBetweenStop(route.stops[0], route.endStop);
      }
    } else {
      duration += this.getDistanceBetweenStop(route.startStop, route.endStop);
    }
    // Check that duration is greater than 0.
    if ( duration > 0 ) {
      // The duration is one-way so double it for both directions.
      duration *= 2;
      // Now calculate vehicles by dividing duration through frequency.
      return Math.round(duration/this.frequencyPatternFrequency);
    }
    return 0;
  }

  getDistanceBetweenStop (stop1, stop2): number {
    var stopDistances = this.gameService.getGame().scenario.stopDistances;
    var stop1Pos: number; var stop2Pos: number;
    for ( var i = 0; i < stopDistances.length; i++ ) {
      if ( stopDistances[i].startsWith(stop1) ) {
        stop1Pos = i;
      } else if ( stopDistances[i].startsWith(stop2) ) {
        stop2Pos = i;
      }
    }
    if ( stop1Pos && stop2Pos ) {
      return parseInt(stopDistances[stop1Pos].split(":")[1].split(",")[stop2Pos]);
    }
  }

  getFrequencyPatternFrequency() {
    return this.frequencyPatternFrequency;
  }

  onSubmitFrequencyPattern(): void {
    // Process days of operation from checkboxes.
    var daysOfOperation = [];
    for ( var i = 0; i < this.getDaysOfWeek().length; i++ ) {
      if ( (document.getElementById('checkbox-' + i) as HTMLInputElement).checked ) {
        daysOfOperation.push(this.getDaysOfWeek()[i]);
      }
    }
    // Create frequency pattern.
    var frequencyPattern = new FrequencyPattern(this.frequencyPatternName, daysOfOperation,
        this.frequencyPatternStartStop, this.frequencyPatternEndStop, this.frequencyPatternStartTime,
        this.frequencyPatternEndTime, this.frequencyPatternFrequency);
    this.frequencyPatterns.push(frequencyPattern);
  }

  onSubmitTimetable(): void {
    // Create Timetable.
    var timetable = new Timetable(this.timetableName, this.validFromDate, this.validToDate, this.frequencyPatterns);
    // Add it to the route.
    this.gameService.getGame().getRoute(this.routeNumber).addTimetable(timetable);
    this.router.navigate(['management']);
  }

}
