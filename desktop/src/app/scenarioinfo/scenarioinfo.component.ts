import { Component, OnInit } from '@angular/core';
import { GameService } from '../shared/game.service';
import { Router } from '@angular/router';
import { Scenario } from '../shared/scenario.model';
import {ServerService} from "../shared/server.service";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";

@Component({
  selector: 'app-scenarioinfo',
  templateUrl: './scenarioinfo.component.html',
  styleUrls: ['./scenarioinfo.component.css']
})
/**
 * View Information screen from Management Screen.
 */
export class ScenarioinfoComponent implements OnInit {

  private scenarioName: string;

  /**
   * Constructor to create the screen
   * @param gameService the game service
   * @param serverService the server service
   * @param router router to enable us to move other screens
   */
  constructor(private gameService: GameService, public router: Router, private serverService: ServerService ) {
    if ( !this.gameService.isOfflineMode() ) {
      this.serverService.getScenarioName().then((name) => {
        this.scenarioName = name;
      });
    }
  }

  ngOnInit(): void {
  }

  /**
   * Retrieve the company name
   * @returns the company name
   */
  getCompanyName(): string {
    if ( this.gameService.isOfflineMode() ) {
      return this.gameService.getGame().getCompanyName();
    } else {
      return this.serverService.getCompanyName();
    }
  }

  /**
   * Retrieve the player name
   * @returns the player name
   */
  getPlayerName(): string {
    if ( this.gameService.isOfflineMode() ) {
      return this.gameService.getGame().getPlayerName();
    } else {
      return this.serverService.getPlayerName();
    }
  }

  /**
   * Go back to the management screen
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

  /** 
   * Retrieve the scenario based on the current scenario name
   * @returns the scenario object with all information about the selected scenario
   */
  getScenario(): Scenario {
    if ( this.gameService.isOfflineMode() ) {
      return this.gameService.getGame().getScenario();
    } else {
      return this.loadScenario(this.scenarioName);
    }

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

}
