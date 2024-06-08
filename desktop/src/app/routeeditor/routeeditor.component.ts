import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {Timetable} from "../shared/timetable.model";

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
   * @param route a variable which contains the current stop that the user clicked on.
   */
  constructor(private gameService: GameService, public router: Router, private route: ActivatedRoute) {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.routeNumber = params['routeNumber'];
      let route = this.gameService.getGame().getRoute(this.routeNumber);
      this.startStop = route.getStartStop();
      this.endStop = route.getEndStop();
      this.stops = route.getStops();
      this.timetables = route.getTimetables();
    });
  }

  onSubmitRoute(): void {
    this.router.navigate(['management']);
  }

  onCreateTimetable(): void {
    this.router.navigate(['timetablecreator'], { queryParams: { routeNumber: this.routeNumber } });
  }

  onDeleteTimetable(timetableName: string): void {
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
