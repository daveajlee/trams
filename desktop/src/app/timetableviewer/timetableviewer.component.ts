import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-timetableviewer',
  templateUrl: './timetableviewer.component.html',
  styleUrls: ['./timetableviewer.component.css']
})
export class TimetableviewerComponent {

  routeNumber: string;
  gameService: GameService;
  paramsSubscription: Subscription;

  /**
   * Construct a new Timetable Viewer component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router to navigate to other pages.
   * @param route a variable which contains the current route that the user wants to view timetables for.
   */
  constructor(private gameService2: GameService, public router: Router, private route: ActivatedRoute) {
    this.paramsSubscription = this.route.params.subscribe((params: Params) => {
      this.routeNumber = params['routeNumber'];
      this.gameService = gameService2;
      let route = this.gameService.getGame().getRoute(this.routeNumber);
    });
  }

  /**
   * Return the route number that we want to retrieve the timetable for.
   */
  getRouteNumber(): string {
    return this.routeNumber;
  }

  /**
   * This method returns to the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
