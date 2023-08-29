import {Component, OnInit} from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Route} from "../routes/route.model";

@Component({
  selector: 'app-routecreator',
  templateUrl: './routecreator.component.html',
  styleUrls: ['./routecreator.component.css']
})
export class RoutecreatorComponent implements OnInit {

  routeNumber: string;
  startStop: string;
  endStop: string;
  stops: string[];
  gameService: GameService;

  /**
   * Construct a new Route Creator component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService2: GameService, public router: Router) {
    this.gameService = gameService2;
    this.startStop = this.getScenarioStops()[0].split(":")[0];
    this.endStop = this.getScenarioStops()[1].split(":")[0];
    this.stops = [];
  }

  /**
   * Things that Angular should do during initialisation.
   */
  ngOnInit(): void {
  }

  /**
   * Retrieve the scenario name as a string.
   */
  getScenarioName(): string {
    return this.gameService.getGame().scenario.scenarioName;
  }

  /**
   * Retrieve the list of stops that this scenario contains.
   */
  getScenarioStops(): string[] {
    return this.gameService.getGame().scenario.stopDistances;
  }

  /**
   * Returns true iff the stop name is either a start stop or a end stop.
   * @param stopName the stop name to check.
   */
  disableStop(stopName: string): boolean {
    return stopName === this.startStop || stopName === this.endStop;
  }

  onSubmitRoute(): void {
    var scenarioStops = this.getScenarioStops();
    for ( var i = 0; i < scenarioStops.length; i++ ) {
      if ( (document.getElementById('checkbox-' + i) as HTMLInputElement).checked ) {
        this.stops.push(scenarioStops[i].split(":")[0]);
      }
    }
    var route = new Route();
    route.routeNumber = this.routeNumber;
    route.startStop = this.startStop;
    route.endStop = this.endStop;
    route.stops = this.stops;
    this.gameService.getGame().addRoute(route);
    this.router.navigate(['timetablecreator'], { queryParams: { routeNumber: this.routeNumber } });
  }

}
