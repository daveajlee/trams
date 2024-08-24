import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {Timetable} from "../shared/timetable.model";
import {ServerService} from "../shared/server.service";

@Component({
  selector: 'app-routeeditor',
  templateUrl: './routeeditor.component.html',
  styleUrls: ['./routeeditor.component.css']
})
export class RouteeditorComponent {

  private routeNumber: string;
  private startStop: string;
  private endStop: string;
  private stops: string[];
  private timetables: Timetable[];

  private idSubscription: Subscription;

  /**
   * Construct a new Route Editor component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router to navigate to other pages.
   * @param route a variable which contains the current stop that the user clicked on
   * @param serverService the server service that manages the http calls
   */
  constructor(private gameService: GameService, public router: Router, private route: ActivatedRoute,
              private serverService: ServerService) {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.routeNumber = params['routeNumber'];
      if ( this.gameService.isOfflineMode() ) {
        let route = this.gameService.getGame().getRoute(this.routeNumber);
        this.startStop = route.getStartStop();
        this.endStop = route.getEndStop();
        this.stops = route.getStops();
        this.timetables = route.getTimetables();
      } else {
        this.serverService.getRoute(this.routeNumber).then((route) => {
          this.startStop = route.startStop;
          this.endStop = route.endStop;
          this.stops = route.stops;
          this.serverService.getTimetables(this.routeNumber).then((timetables) => {
            this.timetables = [];
            console.log(timetables);
            let timetableResponses = timetables.timetableResponses;
            for ( let i = 0; i < timetableResponses.length; i++ ) {
              this.timetables.push(new Timetable(timetableResponses[i].name, timetableResponses[i].validFromDate, timetableResponses[i].validToDate, timetableResponses[i].frequencyPatterns))
            }
          })
        });
      }



    });
  }

  onSubmitRoute(): void {
    this.router.navigate(['management']);
  }

  onCreateTimetable(): void {
    this.router.navigate(['timetablecreator'], { queryParams: { routeNumber: this.routeNumber } });
  }

  async onDeleteTimetable(timetableName: string): Promise<void> {
    if ( !this.gameService.isOfflineMode() ) {
      await this.serverService.deleteTimetable(this.routeNumber, timetableName);
    }
    for ( let i = 0; i < this.timetables.length; i++ ) {
      if ( this.timetables[i].getName() === timetableName ) {
        this.timetables.splice(i, 1);
      }
    }
  }

  /**
   * Get the route number.
   * @return the route number as a String.
   */
  getRouteNumber(): string {
    return this.routeNumber;
  }

  /**
   * Get the start stop for this route.
   * @return the start stop as a String.
   */
  getStartStop(): string {
    return this.startStop;
  }

  /**
   * Get the end stop for this route.
   * @return the end stop as a String.
   */
  getEndStop(): string {
    return this.endStop;
  }

  /**
   * Get the list of stops served by this route.
   * @return the array of stops served.
   */
  getStops(): string[] {
    return this.stops;
  }

  /**
   * Get the timetables that exist for this route.
   * @return the timetables as an array of Timetable objects.
   */
  getTimetables(): Timetable[] {
    return this.timetables;
  }

}
