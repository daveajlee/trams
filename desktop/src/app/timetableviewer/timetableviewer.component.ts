import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {Route} from "../routes/route.model";
import {StopTimeModel} from "../stops/stop-detail/stoptime.model";
import {ServerService} from "../shared/server.service";
import {RouteResponse} from "../routes/route.response";

@Component({
  selector: 'app-timetableviewer',
  templateUrl: './timetableviewer.component.html',
  styleUrls: ['./timetableviewer.component.css']
})
export class TimetableviewerComponent {

  private routeNumber: string;
  private paramsSubscription: Subscription;
  private route: Route;
  private routeResponse: RouteResponse;
  selectedSchedule: string;
  selectedDate: string;
  schedules: string[];

  /**
   * Construct a new Timetable Viewer component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router to navigate to other pages.
   * @param activatedRoute a variable which contains the current route that the user wants to view timetables for.
   * @param serverService the server service which manages the http calls.
   */
  constructor(private gameService: GameService, public router: Router, private activatedRoute: ActivatedRoute,
              private serverService: ServerService) {
    this.paramsSubscription = this.activatedRoute.params.subscribe((params: Params) => {
      this.routeNumber = params['routeNumber'];
      if ( this.gameService.isOfflineMode() ) {
        this.route = this.gameService.getGame().getRoute(this.routeNumber);
        this.selectedSchedule = this.route.getSchedules()[0].getRouteNumberAndScheduleId();
        let currentDateTime = this.gameService.getGame().getCurrentDateTime();
        this.selectedDate = currentDateTime.getFullYear() + "-" + (currentDateTime.getMonth() < 10 ? "0"
                + currentDateTime.getMonth() : currentDateTime.getMonth() )  + "-" +
            (currentDateTime.getDate() < 10 ? "0"
                + currentDateTime.getDate() : currentDateTime.getDate() );
        this.schedules = [];
        this.route.getSchedules().forEach((element) => {
          this.schedules.push(element.getRouteNumberAndScheduleId());
        } );
      } else {
        // Retrieve the route
        this.serverService.getRoute(this.routeNumber).then((route) => {
          this.routeResponse = route;
          // Retrieve the current date.
          this.serverService.getCurrentDateTime().then((currentDateTime) => {
            this.selectedDate = currentDateTime;
            // Retrieve the schedules.
            this.serverService.getStopTimes(this.routeNumber).then((stopTimes) => {
              this.schedules = [];
              for ( let i = 0; i < stopTimes.count; i++ ) {
                if ( !this.schedules.includes(this.routeNumber + "/" + stopTimes.stopTimeResponses[i].scheduleNumber) ) {
                  this.schedules.push(this.routeNumber + "/" + stopTimes.stopTimeResponses[i].scheduleNumber);
                }
              }
            })
          })
        })
      }

    });
  }

  /**
   * Return the route number that we want to retrieve the timetable for.
   */
  getRouteNumber(): string {
    return this.routeNumber;
  }

  /**
   * Return a list of the stop times that are served by this schedule.
   */
  getStopTimes(): StopTimeModel[] {
    if ( this.gameService.isOfflineMode() ) {
      let stopTimeModels = [];
      for ( let i = 0; i < this.route.getSchedules().length; i++ ) {
        if ( (this.route.getSchedules()[i].getRouteNumberAndScheduleId()) == this.selectedSchedule ){
          for ( let j = 0; j < this.route.getSchedules()[i].getServices().length; j++ ) {
            stopTimeModels = stopTimeModels.concat(this.route.getSchedules()[i].getServices()[j].getStopList());
          }
        }
      }
      return stopTimeModels;
    } else {
      return [];
    }
  }

  /**
   * This method returns to the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
