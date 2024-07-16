import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {DatePipe} from "@angular/common";
import {FrequencyPattern} from "../shared/frequencypattern.model";
import {Timetable} from "../shared/timetable.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";
import {ServiceModel} from "../stops/stop-detail/service.model";
import {TimeHelper} from "../shared/time.helper";
import {Route} from "../routes/route.model";
import {Scenario} from "../shared/scenario.model";
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";
import {ServerService} from "../shared/server.service";
import {TimetableRequest} from "../shared/timetable.request";
import {RouteResponse} from "../routes/route.response";
import {RoutesService} from "../routes/routes.service";
import {GenerateStopTimesRequest} from "./generatestoptimes.request";

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

  frequencyPatternName: string;
  frequencyPatternStartStop: string;
  frequencyPatternEndStop: string;
  frequencyPatternStartTime: string;
  frequencyPatternEndTime: string;
  frequencyPatternFrequency: number;

  private frequencyPatterns: FrequencyPattern[];

  private currentDateTime: Date;
  private route: Route;
  private routeResponse: RouteResponse;
  private scenarioName: string;

  constructor(private activatedRoute: ActivatedRoute, private gameService: GameService,
              public router: Router, private datePipe: DatePipe, private serverService: ServerService,
              private routeService: RoutesService) {
    // Set the current date time.
    if ( this.gameService.isOfflineMode() ) {
      this.currentDateTime = this.gameService.getGame().getCurrentDateTime();
      // Valid from date is current date.
      this.validFromDate = this.datePipe.transform(this.currentDateTime, 'yyyy-MM-dd');
      // Valid to date is current date + 1 year.
      let oneFromYearNow = new Date(this.currentDateTime);
      oneFromYearNow.setFullYear(oneFromYearNow.getFullYear() + 1)
      this.validToDate = this.datePipe.transform(oneFromYearNow, 'yyyy-MM-dd');
    } else {
      this.serverService.getCurrentDateTime().then((dateTime) => {
        this.currentDateTime = TimeHelper.formatStringAsDateObject(dateTime);
        // Valid from date is current date.
        this.validFromDate = this.datePipe.transform(this.currentDateTime, 'yyyy-MM-dd');
        // Valid to date is current date + 1 year.
        let oneFromYearNow = new Date(this.currentDateTime);
        oneFromYearNow.setFullYear(oneFromYearNow.getFullYear() + 1)
        this.validToDate = this.datePipe.transform(oneFromYearNow, 'yyyy-MM-dd');
      })
    }

    // Set the scenario name.
    if ( this.gameService.isOfflineMode() ) {
      this.scenarioName = this.gameService.getGame().getScenario().getScenarioName();
    } else {
      this.serverService.getScenarioName().then((scenarioName) => {
        this.scenarioName = scenarioName;
      })
    }
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
    this.activatedRoute.queryParams
        .subscribe(params => {
              this.routeNumber = params.routeNumber;
              console.log('Route number is ' + this.routeNumber);
              // Set the current route.
              if ( this.gameService.isOfflineMode() ) {
                this.route = this.gameService.getGame().getRoute(this.routeNumber);
              } else {
                if ( this.routeNumber ) {
                  this.serverService.getRoute(this.routeNumber).then((route) => {
                    this.routeResponse = route;
                  });
                }
              }
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
    if ( this.gameService.isOfflineMode() ) {
      if ( !this.frequencyPatternStartStop ) {
        this.frequencyPatternStartStop = this.route.getStartStop();
      }
      possibleStops.push(this.route.getStartStop());
      possibleStops = possibleStops.concat(this.route.getStops());
    } else {
      if ( this.routeResponse ) {
        if ( !this.frequencyPatternStartStop ) {
          this.frequencyPatternStartStop = this.routeResponse.startStop;
        }
        possibleStops.push(this.routeResponse.startStop);
        possibleStops = possibleStops.concat(this.routeResponse.stops);
      }
    }
    return possibleStops;
  }

  getFrequencyPatternEndStops(): string[] {
    let possibleStops = [];
    if ( this.gameService.isOfflineMode() ) {
      if ( !this.frequencyPatternEndStop ) {
        this.frequencyPatternEndStop = this.route.getEndStop();
      }
      possibleStops.push(this.route.getEndStop());
      possibleStops = possibleStops.concat(this.route.getStops());
    } else {
      if ( this.routeResponse ) {
        if ( !this.frequencyPatternEndStop ) {
          this.frequencyPatternEndStop = this.routeResponse.endStop;
        }
        possibleStops.push(this.routeResponse.endStop);
        possibleStops = possibleStops.concat(this.routeResponse.stops);
      }
    }
    return possibleStops;
  }

  getNumberVehicles() : number {
    if ( this.scenarioName ) {
      // Calculate the duration.
      let duration;
      if( this.gameService.isOfflineMode() ) {
        duration = this.routeService.getDuration(this.loadScenario(this.scenarioName), this.route.getStartStop(), this.route.getStops(), this.route.getEndStop());
      } else {
        if ( this.routeResponse ) {
          duration = this.routeService.getDuration(this.loadScenario(this.scenarioName), this.routeResponse.startStop, this.routeResponse.stops, this.routeResponse.endStop);
        }
      }
      // Check that duration is greater than 0.
      if ( duration > 0 ) {
        // Now calculate vehicles by dividing duration through frequency.
        return Math.ceil(duration / this.frequencyPatternFrequency);
      }
    }
    return 0;
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
    if ( this.gameService.isOfflineMode() ) {
      this.route.addTimetable(timetable);
      // This is where we should now generate the schedules for the relevant timetable.
      if ( timetable.getValidFromDate() <= this.currentDateTime && timetable.getValidToDate() >= this.currentDateTime ) {
        // Timetable is relevant so get frequency pattern.
        for ( let i = 0; i < timetable.getFrequencyPatterns().length; i++ ) {
          // Now we go through the tours which are the schedules.
          for (let j = 0; j < timetable.getFrequencyPatterns()[i].getNumTours(); j++) {
            let routeSchedule = new ScheduleModel(this.routeNumber, "" + (i + 1) * (j + 1));
            console.log('Generating schedule ' + routeSchedule.getRouteNumberAndScheduleId());
            // Note the duration which is the frequency * num Tours
            let duration = timetable.getFrequencyPatterns()[i].getFrequencyInMinutes() * timetable.getFrequencyPatterns()[i].getNumTours();
            // Set the loop time to the starting time.
            let loopTime = timetable.getFrequencyPatterns()[i].getStartTime();
            let serviceCounter = 0;
            // Now repeat until we reach the end time.
            while (this.isBeforeTime(loopTime, timetable.getFrequencyPatterns()[i].getEndTime())) {
              // Add an outgoing service.
              routeSchedule.addService(this.generateService(timetable.getFrequencyPatterns()[i], loopTime, serviceCounter, j, true));
              // Add half of duration to cover outgoing service.
              loopTime = TimeHelper.addTime(loopTime, (duration / 2));
              // Increase service counter
              serviceCounter++;
              // Add return service.
              routeSchedule.addService(this.generateService(timetable.getFrequencyPatterns()[i], loopTime, serviceCounter, j, false));
              // Add half of duration to cover return service.
              loopTime = TimeHelper.addTime(loopTime, (duration / 2));
              // Increase service counter
              serviceCounter++;
            }
            // Now we add the route schedule.
            this.route.addSchedule(routeSchedule);
          }
        }
      }
      // Now go to route editor screen.
      this.router.navigate(['routeeditor', this.routeNumber]);
    } else {
      console.log(this.convertTimestampToDateString(this.validFromDate));
      console.log(this.convertTimestampToDateString(this.validToDate));
      this.serverService.addTimetable(new TimetableRequest(this.timetableName, this.convertTimestampToDateString(this.validFromDate), this.convertTimestampToDateString(this.validToDate), this.frequencyPatterns, this.serverService.getCompanyName(), this.routeNumber)).then(() => {
        // This is where we should now generate the schedules for the relevant timetable.
        if (timetable.getValidFromDate() <= this.currentDateTime && timetable.getValidToDate() >= this.currentDateTime) {
          // Timetable is relevant so get frequency pattern.
          for (let i = 0; i < timetable.getFrequencyPatterns().length; i++) {
            // Now we send the generate request to the server.
            this.serverService.generateStopTimes(new GenerateStopTimesRequest(this.serverService.getCompanyName(),
                this.getStopNames(timetable.getFrequencyPatterns()[i].getStartStop(), this.routeResponse.stops, timetable.getFrequencyPatterns()[i].getEndStop()), this.routeNumber, timetable.getFrequencyPatterns()[i].getStartTime(),
                timetable.getFrequencyPatterns()[i].getEndTime(), timetable.getFrequencyPatterns()[i].getFrequencyInMinutes(),
                TimeHelper.formatDateTimeAsString(timetable.getValidFromDate()),
                TimeHelper.formatDateTimeAsString(timetable.getValidToDate()), this.getOperatingDays(timetable.getFrequencyPatterns()[i].getDaysOfOperation()))).then(() => {
              // Now go to route editor screen.
              this.router.navigate(['routeeditor', this.routeNumber]);
            });
          }
        }
      });
    }
  }

  /**
   * Get the list of operating days as a comma-separated string.
   * @param operatingDays operating days as an array
   * @return the operating days as a comma-separated string.
   */
  getOperatingDays(operatingDays: string[]): string {
    let myOperatingDays = "";
    operatingDays.forEach((operatingDay) => {
      myOperatingDays += operatingDay + ",";
    })
    return myOperatingDays;
  }

  /**
   * This creates an array of stop names that should be served.
   * @param startStop the start stop to be served as a string
   * @param stops the list of intermediate stops to be served as a string array
   * @param endStop the end stop to be served as a string
   * @return the array of stop names
   */
  getStopNames(startStop: string, stops: string[], endStop: string): string[] {
    let stopNames = [];
    stopNames.push(startStop);
    stopNames = stopNames.concat(stops);
    stopNames.push(endStop);
    return stopNames;
  }

  /**
   * This is a helper method to convert the date timestamp e.g. 2025-07-04T00:00:00.000Z
   * to dd-MM-yyyy HH:mm
   */
  convertTimestampToDateString(value: string): string {
    console.log('value: ' + value);
    let dateParts = value.split('-');
    let dateFormat = dateParts[2].split('T')[0] + '-' + dateParts[1] + '-' + dateParts[0];
    let timeFormat;
    if ( value.includes('T') ) {
      let timeParts = value.split('T');
      let timeParts2 = timeParts[0].split(':');
      timeFormat = timeParts2[0] + ':' + timeParts2[1];
    } else {
      timeFormat = "00:00";
    }
    return dateFormat + ' ' + timeFormat;
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
      for ( let k = 0; k < this.route.getStops().length; k++ ) {
        // Get the distance between this stop and the last stop.
        distance += (k == 0 ) ? this.loadScenario(this.scenarioName).getDistanceBetweenStop(frequencyPattern.getStartStop(), this.route.getStops()[k])
            : this.loadScenario(this.scenarioName).getDistanceBetweenStop(this.route.getStops()[k-1], this.route.getStops()[k]);
        service.addStop(TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), this.route.getStops()[k]);
      }
    } else {
      for ( let m = this.route.getStops().length - 1; m >= 0; m-- ) {
        // Get the distance between this stop and the last stop.
        distance += ( m == this.route.getStops().length - 1 ) ? this.loadScenario(this.scenarioName).getDistanceBetweenStop(this.route.getStops()[m], frequencyPattern.getEndStop())
            : this.loadScenario(this.scenarioName).getDistanceBetweenStop(this.route.getStops()[m], this.route.getStops()[m+1]);
        console.log('Distance is ' + distance);
        service.addStop(TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), TimeHelper.addTime(startTime, ((tourNumber * frequencyPattern.getFrequencyInMinutes())) + distance), this.route.getStops()[m]);
      }
    }
    // Now we need to do the end stop.
    distance += this.loadScenario(this.scenarioName).getDistanceBetweenStop(frequencyPattern.getEndStop(), this.route.getStops()[this.route.getStops().length-1]);
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

  /**
   * This is a helper method to load the correct scenario based on the supplied scenario name.
   * @param scenario which contains the name of the scenario that the user chose.
   * @returns the scenario object corresponding to the supplied name.
   */
  loadScenario(scenario: string): Scenario {
    if ( scenario === SCENARIO_LANDUFF.getScenarioName() ) {
      return SCENARIO_LANDUFF;
    } else if ( scenario === SCENARIO_LONGTS.getScenarioName()) {
      return SCENARIO_LONGTS;
    } else if ( scenario === SCENARIO_MDORF.getScenarioName() ) {
      return SCENARIO_MDORF;
    } else {
      return null;
    }
  }

}
