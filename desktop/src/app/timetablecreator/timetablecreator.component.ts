import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {DatePipe} from "@angular/common";
import {FrequencyPattern} from "../shared/frequencypattern.model";
import {Timetable} from "../shared/timetable.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";
import {ServiceModel} from "../stops/stop-detail/service.model";

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
    this.validFromDate = this.datePipe.transform(this.gameService.getGame().currentDateTime, 'yyyy-MM-dd');
    // Valid to date is current date + 1 year.
    let oneFromYearNow = new Date(this.gameService.getGame().currentDateTime);
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
      return Math.ceil(duration/this.frequencyPatternFrequency);
    }
    return 0;
  }

  getDistanceBetweenStop (stop1, stop2): number {
    var stopDistances = this.gameService.getGame().scenario.stopDistances;
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
      if ( this.frequencyPatterns[i].name === frequencyPatternName ) {
        this.frequencyPatternName = this.frequencyPatterns[i].name;
        this.frequencyPatternStartTime = this.frequencyPatterns[i].startTime;
        this.frequencyPatternEndTime = this.frequencyPatterns[i].endTime;
        this.frequencyPatternStartStop = this.frequencyPatterns[i].startStop;
        this.frequencyPatternEndStop = this.frequencyPatterns[i].endStop;
        this.frequencyPatternFrequency = this.frequencyPatterns[i].frequencyInMinutes;
      }
    }
    console.log('I would like to edit ' + frequencyPatternName);
  }

  onDeleteFrequencyPattern(frequencyPatternName: string): void {
    for ( let i = 0; i < this.frequencyPatterns.length; i++ ) {
      if ( this.frequencyPatterns[i].name === frequencyPatternName ) {
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
    if ( timetable.validFromDate <= this.gameService.getGame().currentDateTime && timetable.validToDate >= this.gameService.getGame().currentDateTime ) {
      // Timetable is relevant so get frequency pattern.
      for ( let i = 0; i < timetable.frequencyPatterns.length; i++ ) {
          // Now we go through the tours which are the schedules.
          for ( let j = 0; j < timetable.frequencyPatterns[i].numTours; j++ ) {
            let routeSchedule = new ScheduleModel(this.routeNumber, "" + (i+1) * (j+1));
            console.log('Generating schedule ' + routeSchedule.routeNumber + '/' + routeSchedule.scheduleId);
            // Note the duration which is the frequency * num Tours
            let duration = timetable.frequencyPatterns[i].frequencyInMinutes * timetable.frequencyPatterns[i].numTours;
            // Set the loop time to the starting time.
            let loopTime = timetable.frequencyPatterns[i].startTime;
            let serviceCounter = 0;
            // Now repeat until we reach the end time.
            while ( this.isBeforeTime(loopTime, timetable.frequencyPatterns[i].endTime) ) {
              // Add an outgoing service.
              routeSchedule.addService(this.generateService(timetable.frequencyPatterns[i], loopTime, serviceCounter, j, true));
              // Add half of duration to cover outgoing service.
              loopTime = this.addTime(loopTime, (duration/2));
              // Increase service counter
              serviceCounter++;
              // Add return service.
              routeSchedule.addService(this.generateService(timetable.frequencyPatterns[i], loopTime, serviceCounter, j, false));
              // Add half of duration to cover return service.
              loopTime = this.addTime(loopTime, (duration/2));
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
    service.addStop(this.addTime(startTime, tourNumber * frequencyPattern.frequencyInMinutes), this.addTime(startTime, tourNumber * frequencyPattern.frequencyInMinutes), outgoing ? frequencyPattern.startStop : frequencyPattern.endStop);
    // Go through remaining stops for the route.
    let distance = 0;
    if ( outgoing ) {
      for ( let k = 0; k < this.gameService.getGame().getRoute(this.routeNumber).stops.length; k++ ) {
        // Get the distance between this stop and the last stop.
        distance += (k == 0 ) ? this.getDistanceBetweenStop(frequencyPattern.startStop, this.gameService.getGame().getRoute(this.routeNumber).stops[k])
            : this.getDistanceBetweenStop(this.gameService.getGame().getRoute(this.routeNumber).stops[k-1], this.gameService.getGame().getRoute(this.routeNumber).stops[k]);
        service.addStop(this.addTime(startTime, ((tourNumber * frequencyPattern.frequencyInMinutes)) + distance), this.addTime(startTime, ((tourNumber * frequencyPattern.frequencyInMinutes)) + distance), this.gameService.getGame().getRoute(this.routeNumber).stops[k]);
      }
    } else {
      for ( let m = this.gameService.getGame().getRoute(this.routeNumber).stops.length - 1; m >= 0; m-- ) {
        // Get the distance between this stop and the last stop.
        distance += ( m == this.gameService.getGame().getRoute(this.routeNumber).stops.length - 1 ) ? this.getDistanceBetweenStop(this.gameService.getGame().getRoute(this.routeNumber).stops[m], frequencyPattern.endStop)
            : this.getDistanceBetweenStop(this.gameService.getGame().getRoute(this.routeNumber).stops[m], this.gameService.getGame().getRoute(this.routeNumber).stops[m+1]);
        console.log('Distance is ' + distance);
        service.addStop(this.addTime(startTime, ((tourNumber * frequencyPattern.frequencyInMinutes)) + distance), this.addTime(startTime, ((tourNumber * frequencyPattern.frequencyInMinutes)) + distance), this.gameService.getGame().getRoute(this.routeNumber).stops[m]);
      }
    }
    // Now we need to do the end stop.
    distance += this.getDistanceBetweenStop(frequencyPattern.endStop, this.gameService.getGame().getRoute(this.routeNumber).stops[this.gameService.getGame().getRoute(this.routeNumber).stops.length-1]);
    service.addStop(this.addTime(startTime, ((tourNumber * frequencyPattern.frequencyInMinutes)) + distance), this.addTime(startTime, ((tourNumber * frequencyPattern.frequencyInMinutes)) + distance), outgoing ? frequencyPattern.endStop : frequencyPattern.startStop);
    return service;
  }

  /**
   * This is a helper method which adds a number of minutes to the time.
   */
  addTime(time: string, addMinutes: number): string {
      // Extract the time from the string.
      var hours = parseInt(time.split(":")[0]);
      var minutes = parseInt(time.split(":")[1]);
      // Add the minutes to the current time.
      minutes += addMinutes;
      // Adjust the minutes if it is now higher than 59.
      while ( minutes > 59 ) {
        hours++;
        minutes -= 60;
      }
      // Adjust the hours if it is now higher than 23.
      while ( hours > 23 ) {
        hours -= 24;
      }
      // Return the time in format HH:mm
      return (hours < 10 ? "0" + hours : hours ) + ":" + (minutes < 10 ? "0" + minutes : minutes )
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
