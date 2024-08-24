import {Component, OnInit} from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Route} from "../routes/route.model";
import {ServerService} from "../shared/server.service";
import {Scenario} from "../shared/scenario.model";
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";

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
  private scenarioName: string;

  /**
   * Construct a new Route Creator component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   * @param serverService the server service managing the http calls.
   */
  constructor(private gameService: GameService, public router: Router, private serverService: ServerService) {
    if ( !this.gameService.isOfflineMode() ) {
      this.serverService.getScenarioName().then((name) => {
        this.scenarioName = name;
        this.startStop = this.getScenarioStops()[0].split(":")[0];
        this.endStop = this.getScenarioStops()[1].split(":")[0];
        this.stops = [];
        this.nightRoute = false;
      });
    } else {
      this.startStop = this.getScenarioStops()[0].split(":")[0];
      this.endStop = this.getScenarioStops()[1].split(":")[0];
      this.stops = [];
      this.nightRoute = false;
    }
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
    if ( !this.gameService.isOfflineMode() ) {
      return this.scenarioName;
    } else {
      return this.gameService.getGame().getScenario().getScenarioName();
    }
  }

  /**
   * Retrieve the list of stops that this scenario contains.
   */
  getScenarioStops(): string[] {
    if ( !this.gameService.isOfflineMode() ) {
      if ( this.scenarioName ) {
        return this.loadScenario(this.scenarioName).getStopDistances();
      }
    } else {
      return this.gameService.getGame().getScenario().getStopDistances();
    }

  }

  /**
   * Returns true iff the stop name is either a start stop or a end stop.
   * @param stopName the stop name to check.
   */
  disableStop(stopName: string): boolean {
    return stopName === this.startStop || stopName === this.endStop;
  }

  /**
   * This is a helper method to load the correct scenario based on the supplied scenario name.
   * @param scenario which contains the name of the scenario that the user chose.
   * @returns the scenario object corresponding to the supplied name.
   */
  loadScenario(scenario: string): Scenario {
    if ( scenario === SCENARIO_LANDUFF.getScenarioName() ) {
      return SCENARIO_LANDUFF;
    } else if ( scenario === SCENARIO_LONGTS.getScenarioName()) {
      return SCENARIO_LONGTS;
    } else if ( scenario === SCENARIO_MDORF.getScenarioName() ) {
      return SCENARIO_MDORF;
    } else {
      return null;
    }
  }

  onSubmitRoute(): void {
    var scenarioStops = this.getScenarioStops();
    for ( var i = 0; i < scenarioStops.length; i++ ) {
      if ( (document.getElementById('checkbox-' + i) as HTMLInputElement).checked ) {
        this.stops.push(scenarioStops[i].split(":")[0]);
      }
    }
    let companyName = this.gameService.isOfflineMode() ? this.gameService.getGame().getCompanyName() : this.serverService.getCompanyName();
    var route = new Route(this.routeNumber, this.startStop, this.endStop, this.stops,
        companyName);
    if ( (document.getElementById('checkbox-night') as HTMLInputElement).checked ) {
      route.setNightRoute(true);
    }
    if ( this.gameService.isOfflineMode() ) {
      this.gameService.getGame().addRoute(route);
      this.router.navigate(['timetablecreator'], { queryParams: { routeNumber: this.routeNumber } });
    } else {
      this.serverService.addRoute(route).then(() => {
        this.router.navigate(['timetablecreator'], { queryParams: { routeNumber: this.routeNumber } });
      })
    }
  }

}
