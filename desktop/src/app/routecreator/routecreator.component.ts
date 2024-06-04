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
  private stops: string[];
  nightRoute: boolean;

  /**
   * Construct a new Route Creator component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService: GameService, public router: Router) {
    this.startStop = this.getScenarioStops()[0].split(":")[0];
    this.endStop = this.getScenarioStops()[1].split(":")[0];
    this.stops = [];
    this.nightRoute = false;
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
    return this.gameService.getGame().getScenario().getScenarioName();
  }

  /**
   * Retrieve the list of stops that this scenario contains.
   */
  getScenarioStops(): string[] {
    return this.gameService.getGame().getScenario().getStopDistances();
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
    var route = new Route(this.routeNumber, this.startStop, this.endStop, this.stops,
        this.gameService.getGame().getCompanyName());
    if ( (document.getElementById('checkbox-night') as HTMLInputElement).checked ) {
      route.setNightRoute(true);
    }
    this.gameService.getGame().addRoute(route);
    this.router.navigate(['timetablecreator'], { queryParams: { routeNumber: this.routeNumber } });
  }

}
