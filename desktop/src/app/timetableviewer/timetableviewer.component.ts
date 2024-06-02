import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {Route} from "../routes/route.model";
import {StopTimeModel} from "../stops/stop-detail/stoptime.model";

@Component({
  selector: 'app-timetableviewer',
  templateUrl: './timetableviewer.component.html',
  styleUrls: ['./timetableviewer.component.css']
})
export class TimetableviewerComponent {

  routeNumber: string;
  paramsSubscription: Subscription;
  route: Route;
  selectedSchedule: string;
  selectedDate: string;

  /**
   * Construct a new Timetable Viewer component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router to navigate to other pages.
   * @param activatedRoute a variable which contains the current route that the user wants to view timetables for.
   */
  constructor(private gameService: GameService, public router: Router, private activatedRoute: ActivatedRoute) {
    this.paramsSubscription = this.activatedRoute.params.subscribe((params: Params) => {
      this.routeNumber = params['routeNumber'];
      this.route = this.gameService.getGame().getRoute(this.routeNumber);
      this.selectedSchedule = this.route.getSchedules()[0].routeNumber + "/" + this.route.getSchedules()[0].scheduleId;
      let currentDateTime = this.gameService.getGame().getCurrentDateTime();
      this.selectedDate = currentDateTime.getFullYear() + "-" + (currentDateTime.getMonth() < 10 ? "0"
      + currentDateTime.getMonth() : currentDateTime.getMonth() )  + "-" +
          (currentDateTime.getDate() < 10 ? "0"
              + currentDateTime.getDate() : currentDateTime.getDate() );
    });
  }

  /**
   * Return the route number that we want to retrieve the timetable for.
   */
  getRouteNumber(): string {
    return this.routeNumber;
  }

  /**
   * Return the list of schedules that are served by this route.
   */
  getSchedules(): string[] {
    let schedules = [];
    this.route.getSchedules().forEach((element) => {
      schedules.push(element.routeNumber + "/" + element.scheduleId);
    } );
    return schedules;
  }

  /**
   * Return a list of the stop times that are served by this schedule.
   */
  getStopTimes(): StopTimeModel[] {
    let stopTimeModels = [];
    for ( let i = 0; i < this.route.getSchedules().length; i++ ) {
      if ( (this.route.getSchedules()[i].routeNumber + "/" + this.route.getSchedules()[i].scheduleId) == this.selectedSchedule ){
        for ( let j = 0; j < this.route.getSchedules()[i].services.length; j++ ) {
          stopTimeModels = stopTimeModels.concat(this.route.getSchedules()[i].services[j].stopList);
        }
      }
    }
    return stopTimeModels;
  }

  /**
   * This method returns to the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
