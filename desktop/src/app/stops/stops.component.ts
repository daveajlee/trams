import {Component, OnDestroy, OnInit} from '@angular/core';
import {Stop} from './stop.model';
import {Subscription} from 'rxjs';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {ServerService} from "../shared/server.service";
import {Scenario} from "../shared/scenario.model";
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";

@Component({
  selector: 'app-stops',
  templateUrl: './stops.component.html',
  styleUrls: ['./stops.component.css']
})
/**
 * This class implements the functionality for the stops component which retrieves stop data from the server and sends it to the
 * frontend component for rendering.
 */
export class StopsComponent implements OnInit, OnDestroy {

  private stops: Stop[];
  private subscription: Subscription;
  private alphabet: string[];
  private scenarioName: string;

  /**
   * Create a new stops component which constructs a data service and a stop service to retreive data from the server.
   * @param serverService which contains the HTTP connection to the server
   * @param gameService a service which retrieves game information
   * @param router the router object from Angular to move screens.
   */
  constructor(private serverService: ServerService, private gameService: GameService, public router: Router) {
    if ( !this.gameService.isOfflineMode() ) {
      this.serverService.getScenarioName().then((name) => {
        this.scenarioName = name;
        this.stops = this.retrieveAllStops();
      });
    }
  }

  /**
   * Initialise a new stops component which maintains a list of stops that can be updated and set from the server calls.
   */
  ngOnInit(): void {
    this.alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
    this.stops = this.retrieveAllStops();
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
    if ( this.subscription ) {
      this.subscription.unsubscribe();
    }
  }

  /**
   * Retrieve the alphabet as a list.
   * @return the
   */
  getAlphabet(): string[] {
    return this.alphabet;
  }

  /**
   * Retrieve the current list of stops (which may be all or filtered).
   * @return the list of stops as an array of Stops objects.
   */
  getStops(): Stop[] {
    return this.stops;
  }

  /**
   * Helper method to retrieve all stops.
   */
  retrieveAllStops(): Stop[] {
    if ( this.gameService.isOfflineMode() ) {
      let stops = [];
      let allStops =  this.gameService.getGame().getScenario().getStopDistances();
      for ( let i = 0; i < allStops.length; i++ ) {
        stops.push(new Stop('' + i, allStops[i].split(":")[0], 0, 0));
      }
      return stops;
    } else {
      let stops = [];
      if ( this.scenarioName ) {
        let retrievedStops = this.loadScenario(this.scenarioName).getStopDistances();
        for (let i = 0; i < retrievedStops.length; i++ ) {
          stops.push(new Stop('' + i, retrievedStops[i].split(":")[0], 0, 0));
        }
        return stops;
      }
    }
  }

  /**
   * Filter the stops based on the supplied letter.
   * @param letter the letter to filter the results for.
   */
  filterByLetter(letter: string): void {
    let stops = this.retrieveAllStops();
    this.stops = stops.filter((stop: Stop) =>
      stop.getName().toLowerCase().startsWith(letter));
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

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
