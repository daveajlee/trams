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
  gameService: GameService;
  paramsSubscription: Subscription;
  route: Route;
  selectedSchedule: string;
  selectedDate: string;

  /**
   * Construct a new Timetable Viewer component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router to navigate to other pages.
   * @param activatedRoute a variable which contains the current route that the user wants to view timetables for.
   */
  constructor(private gameService2: GameService, public router: Router, private activatedRoute: ActivatedRoute) {
    this.paramsSubscription = this.activatedRoute.params.subscribe((params: Params) => {
      this.routeNumber = params['routeNumber'];
      this.gameService = gameService2;
      this.route = this.gameService.getGame().getRoute(this.routeNumber);
      this.selectedSchedule = this.route.schedules[0].routeNumber + "/" + this.route.schedules[0].scheduleId;
      this.selectedDate = this.gameService.getGame().currentDateTime.getFullYear() + "-" + (this.gameService.getGame().currentDateTime.getMonth() < 10 ? "0"
      + this.gameService.getGame().currentDateTime.getMonth() : this.gameService.getGame().currentDateTime.getMonth() )  + "-" +
          (this.gameService.getGame().currentDateTime.getDate() < 10 ? "0"
              + this.gameService.getGame().currentDateTime.getDate() : this.gameService.getGame().currentDateTime.getDate() );
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
    this.route.schedules.forEach((element) => {
      schedules.push(element.routeNumber + "/" + element.scheduleId);
    } );
    return schedules;
  }

  /**
   * Return a list of the stop times that are served by this schedule.
   */
  getStopTimes(): StopTimeModel[] {
    let stopTimeModels = [];
    for ( let i = 0; i < this.route.schedules.length; i++ ) {
      if ( (this.route.schedules[i].routeNumber + "/" + this.route.schedules[i].scheduleId) == this.selectedSchedule ){
        for ( let j = 0; j < this.route.schedules[i].services.length; j++ ) {
          stopTimeModels = stopTimeModels.concat(this.route.schedules[i].services[j].stopList);
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
