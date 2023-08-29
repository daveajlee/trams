import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {DatePipe} from "@angular/common";

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

  constructor(private route: ActivatedRoute, private gameService2: GameService,
              public router: Router, private datePipe: DatePipe) {
    this.gameService = gameService2;
    // Valid from date is current date.
    this.validFromDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    // Valid to date is current date + 1 year.
    let oneFromYearNow = new Date(new Date().setFullYear(new Date().getFullYear() + 1));
    this.validToDate = this.datePipe.transform(oneFromYearNow, 'yyyy-MM-dd');
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

  onSubmitTimetable(): void {
    console.log('Timetable submission coming soon!');
  }

}
