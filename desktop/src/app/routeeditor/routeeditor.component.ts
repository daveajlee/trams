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

  routeNumber: string;
  startStop: string;
  endStop: string;
  stops: string[];
  timetables: Timetable[];
  gameService: GameService;

  idSubscription: Subscription;

  /**
   * Construct a new Route Editor component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router to navigate to other pages.
   * @param route a variable which contains the current stop that the user clicked on.
   */
  constructor(private gameService2: GameService, public router: Router, private route: ActivatedRoute) {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.routeNumber = params['routeNumber'];
      this.gameService = gameService2;
      let route = this.gameService.getGame().getRoute(this.routeNumber);
      this.startStop = route.startStop;
      this.endStop = route.endStop;
      this.stops = route.stops;
      this.timetables = route.timetables;
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
      if ( this.timetables[i].name === timetableName ) {
        this.timetables.splice(i, 1);
      }
    }
  }



}
