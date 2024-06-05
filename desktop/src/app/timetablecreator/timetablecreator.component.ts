import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {DatePipe} from "@angular/common";
import {FrequencyPattern} from "../shared/frequencypattern.model";
import {Timetable} from "../shared/timetable.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";
import {ServiceModel} from "../stops/stop-detail/service.model";
import {TimeHelper} from "../shared/time.helper";

@Component({
  selector: 'app-timetablecreator',
  templateUrl: './timetablecreator.component.html',
  styleUrls: ['./timetablecreator.component.css']
})
export class TimetablecreatorComponent {

  private routeNumber: string;
  timetableName: string;
  validFromDate: string;
  validToDate: string;
  private gameService: GameService;

  frequencyPatternName: string;
  frequencyPatternStartStop: string;
  frequencyPatternEndStop: string;
  frequencyPatternStartTime: string;
  frequencyPatternEndTime: string;
  frequencyPatternFrequency: number;

  private frequencyPatterns: FrequencyPattern[];

  constructor(private route: ActivatedRoute, private gameService2: GameService,
              public router: Router, private datePipe: DatePipe) {
    this.gameService = gameService2;
    // Valid from date is current date.
    this.validFromDate = this.datePipe.transform(this.gameService.getGame().getCurrentDateTime(), 'yyyy-MM-dd');
    // Valid to date is current date + 1 year.
    let oneFromYearNow = new Date(this.gameService.getGame().getCurrentDateTime());
    oneFromYearNow.setFullYear(oneFromYearNow.getFullYear() + 1)
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
    return ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Public Holidays", "Schooldays", "School Holidays"]
  }

  /**
   * Get the list of frequency patterns.
   * @return the list of frequency patterns as an array of FrequencyPattern objects.
   */
  getFrequencyPatterns(): FrequencyPattern[] {
    return this.frequencyPatterns;
  }

  getFrequencyPatternStartStops(): string[] {
    let possibleStops = [];
    let route = this.gameService.getGame().getRoute(this.routeNumber);
    if ( !this.frequencyPatternStartStop ) {
      this.frequencyPatternStartStop = route.getStartStop();
    }
    possibleStops.push(route.getStartStop());
    possibleStops = possibleStops.concat(route.getStops());
    return possibleStops;
  }

  getFrequencyPatternEndStops(): string[] {
    let possibleStops = [];
    let route = this.gameService.getGame().getRoute(this.routeNumber);
    if ( !this.frequencyPatternEndStop ) {
      this.frequencyPatternEndStop = route.getEndStop();
    }
    possibleStops.push(route.getEndStop());
    possibleStops = possibleStops.concat(route.getStops());
    return possibleStops;
  }

  getNumberVehicles() : number {
    // Calculate the duration.
    var duration = 0;
    let route = this.gameService.getGame().getRoute(this.routeNumber);
    let stops = route.getStops();
    if ( stops.length > 0 ) {
      duration += this.getDistanceBetweenStop(route.getStartStop(), route.getStops()[0]);
      if ( stops.length > 1 ) {
        for ( var i = 0; i < stops.length-1; i++ ) {
          duration += this.getDistanceBetweenStop(stops[i], stops[i+1]);
        }
        duration += this.getDistanceBetweenStop(stops[stops.length-1], route.getEndStop());
      } else {
        duration += this.getDistanceBetweenStop(stops[0], route.getEndStop());
      }
    } else {
      duration += this.getDistanceBetweenStop(route.getStartStop(), route.getEndStop());
    }
    // Check that duration is greater than 0.
    if ( duration > 0 ) {
      // The duration is one-way so double it for both directions.
      duration *= 2;
      // Now calculate vehicles by dividing duration through frequency.
      return Math.ceil(duration/this.frequencyPatternFrequency);
    }
    return 0;
  }

  getDistanceBetweenStop (stop1, stop2): number {
    var stopDistances = this.gameService.getGame().getScenario().getStopDistances();
    var stop1Pos: number; var stop2Pos: number;
    for ( var i = 0; i < stopDistances.length; i++ ) {
      if ( stopDistances[i].split(":")[0] == stop1 ) {
        stop1Pos = i;
      } else if ( stopDistances[i].split(":")[0] == stop2 ) {
        stop2Pos = i;
      }
    }
    if ( stop1Pos >= 0 && stop2Pos >= 0 ) {
      return parseInt(stopDistances[stop1Pos].split(":")[1].split(",")[stop2Pos]);
    }
  }

  getFrequencyPatternFrequency() {
    return this.frequencyPatternFrequency;
  }

  onSubmitFrequencyPattern(): void {
    // Process days of operation from checkboxes.
    var daysOfOperation = [];
    for ( let i = 0; i < this.getDaysOfWeek().length; i++ ) {
      if ( (document.getElementById('checkbox-' + i) as HTMLInputElement).checked ) {
        daysOfOperation.push(this.getDaysOfWeek()[i]);
      }
    }
    // Create frequency pattern.
    var frequencyPattern = new FrequencyPattern(this.frequencyPatternName, daysOfOperation,
        this.frequencyPatternStartStop, this.frequencyPatternEndStop, this.frequencyPatternStartTime,
        this.frequencyPatternEndTime, this.frequencyPatternFrequency, this.getNumberVehicles());
    this.frequencyPatterns.push(frequencyPattern);
    // Now we reset the form.
    this.frequencyPatternName = "";
    // Set start time to 06:00
    this.frequencyPatternStartTime = "06:00";
    // set end time to 18:30
    this.frequencyPatternEndTime = "18:30";
    // Set frequency to 10.
    this.frequencyPatternFrequency = 10;
  }

  onEditFrequencyPattern(frequencyPatternName: string): void {
    for ( let i = 0; i < this.frequencyPatterns.length; i++ ) {
      if ( this.frequencyPatterns[i].getName() === frequencyPatternName ) {
        this.frequencyPatternName = this.frequencyPatterns[i].getName();
        this.frequencyPatternStartTime = this.frequencyPatterns[i].getStartTime();
        this.frequencyPatternEndTime = this.frequencyPatterns[i].getEndTime();
        this.frequencyPatternStartStop = this.frequencyPatterns[i].getStartStop();
        this.frequencyPatternEndStop = this.frequencyPatterns[i].getEndStop();
        this.frequencyPatternFrequency = this.frequencyPatterns[i].getFrequencyInMinutes();
      }
    }
    console.log('I would like to edit ' + frequencyPatternName);
  }

  onDeleteFrequencyPattern(frequencyPatternName: string): void {
    for ( let i = 0; i < this.frequencyPatterns.length; i++ ) {
      if ( this.frequencyPatterns[i].getName() === frequencyPatternName ) {
        this.frequencyPatterns.splice(i, 1);
      }
    }
  }

  onSubmitTimetable(): void {
    // Create Timetable.
    var timetable = new Timetable(this.timetableName, this.validFromDate, this.validToDate, this.frequencyPatterns);
    // Add it to the route.
    this.gameService.getGame().getRoute(this.routeNumber).addTimetable(timetable);
    // This is where we should now generate the schedules for the relevant timetable.
    if ( timetable.getValidFromDate() <= this.gameService.getGame().getCurrentDateTime() && timetable.getValidToDate() >= this.gameService.getGame().getCurrentDateTime() ) {
      // Timetable is relevant so get frequency pattern.
      for ( let i = 0; i < timetable.getFrequencyPatterns().length; i++ ) {
          // Now we go through the tours which are the schedules.
          for ( let j = 0; j < timetable.getFrequencyPatterns()[i].getNumTours(); j++ ) {
            let routeSchedule = new ScheduleModel(this.routeNumber, "" + (i+1) * (j+1));
            console.log('Generating schedule ' + routeSchedule.getRouteNumberAndScheduleId());
            // Note the duration which is the frequency * num Tours
            let duration = timetable.getFrequencyPatterns()[i].getFrequencyInMinutes() * timetable.getFrequencyPatterns()[i].getNumTours();
            // Set the loop time to the starting time.
            let loopTime = timetable.getFrequencyPatterns()[i].getStartTime();
            let serviceCounter = 0;
            // Now repeat until we reach the end time.
            while ( this.isBeforeTime(loopTime, timetable.getFrequencyPatterns()[i].getEndTime()) ) {
              // Add an outgoing service.
              routeSchedule.addService(this.generateService(timetable.getFrequencyPatterns()[i], loopTime, serviceCounter, j, true));
              // Add half of duration to cover outgoing service.
              loopTime = TimeHelper.addTime(loopTime, (duration/2));
              // Increase service counter
              serviceCounter++;
              // Add return service.
              routeSchedule.addService(this.generateService(timetable.getFrequencyPatterns()[i], loopTime, serviceCounter, j, false));
              // Add half of duration to cover return service.
              loopTime = TimeHelper.addTime(loopTime, (duration/2));
              // Increase service counter
              serviceCounter++;
            }
            // Now we add the route schedule.
            this.gameService.getGame().getRoute(this.routeNumber).addSchedule(routeSchedule);
          }
      }
    }
    // Now go to route editor screen.
    this.router.navigate(['routeeditor', this.routeNumber]);
  }

  /**
   * This is a helper method to generate a service based on the information provided.
   */
  generateService(frequencyPattern: FrequencyPattern, startTime: string, serviceNumber: number, tourNumber: number, outgoing: boolean): ServiceModel {
    // Now we start generating the services.
    let service = new ServiceModel("" + serviceNumber);
    // Add the start stop.
    service.addStop(TimeHelper.addTime(startTime, tourNumber * frequencyPattern.getFrequencyInMinutes()), TimeHelper.addTime(startTime, tourNumber * frequencyPattern.getFrequencyInMinutes()), outgoing ? frequencyPattern.getStartStop() : frequencyPattern.getEndStop());
    // Go through remaining stops for the route.
    let distance = 0;
    if ( outgoing ) {
      for ( let k = 0; k < this.gameService.getGame().getRoute(this.routeNumber).getStops().length; k++ ) {
        // Get the distance between this stop and the last stop.
        distance += (k == 0 ) ? this.getDistanceBetweenStop(frequencyPattern.getStartStop(), this.gameService.getGame().getRoute(this.routeNumber).getStops()[k])
            : this.getDistanceBetweenStop(this.gameService.getGame().getRoute(this.routeNumber).getStops()[k-1], this.gameService.getGame().getRoute(this.routeNumber).getStops()[k]);
        service.addStop(TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), this.gameService.getGame().getRoute(this.routeNumber).getStops()[k]);
      }
    } else {
      for ( let m = this.gameService.getGame().getRoute(this.routeNumber).getStops().length - 1; m >= 0; m-- ) {
        // Get the distance between this stop and the last stop.
        distance += ( m == this.gameService.getGame().getRoute(this.routeNumber).getStops().length - 1 ) ? this.getDistanceBetweenStop(this.gameService.getGame().getRoute(this.routeNumber).getStops()[m], frequencyPattern.getEndStop())
            : this.getDistanceBetweenStop(this.gameService.getGame().getRoute(this.routeNumber).getStops()[m], this.gameService.getGame().getRoute(this.routeNumber).getStops()[m+1]);
        console.log('Distance is ' + distance);
        service.addStop(TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), this.gameService.getGame().getRoute(this.routeNumber).getStops()[m]);
      }
    }
    // Now we need to do the end stop.
    distance += this.getDistanceBetweenStop(frequencyPattern.getEndStop(), this.gameService.getGame().getRoute(this.routeNumber).getStops()[this.gameService.getGame().getRoute(this.routeNumber).getStops().length-1]);
    service.addStop(TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), outgoing ? frequencyPattern.getEndStop() : frequencyPattern.getStartStop());
    return service;
  }

  /**
   * This is a helper method which determines if the current time is before the supplied time.
   * If so, return true. Otherwise return false.
   */
  isBeforeTime(currentTime: string, suppliedTime: string) {
    // Extract the current time from the string.
    let currentHours = parseInt(currentTime.split(":")[0]);
    let currentMinutes = parseInt(currentTime.split(":")[1]);
    // Extract the supplied time from the string.
    let suppliedHours = parseInt(suppliedTime.split(":")[0]);
    let suppliedMinutes = parseInt(suppliedTime.split(":")[1]);
    // Return true if and only if current time is before supplied time.
    if ( currentHours > suppliedHours ) {
      return false;
    } else return !(currentHours == suppliedHours && currentMinutes >= suppliedMinutes);
  }

}
