import { Component, OnInit } from '@angular/core';
import { GameService } from '../shared/game.service';
import {Router} from '@angular/router';
import {ServerService} from "../shared/server.service";
import {Scenario} from "../shared/scenario.model";
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";

@Component({
  selector: 'app-scenariomap',
  templateUrl: './scenariomap.component.html',
  styleUrls: ['./scenariomap.component.css']
})
export class ScenariomapComponent implements OnInit {

  private scenarioName: string;

  constructor(private gameService: GameService, public router: Router, private serverService: ServerService) {
    if ( !this.gameService.isOfflineMode() ) {
      this.serverService.getScenarioName().then((name) => {
        this.scenarioName = name;
      });
    }
  }

  ngOnInit(): void {

  }

  getScenarioName(): string {
    if ( this.gameService.isOfflineMode() ) {
      return this.gameService.getGame().getScenario().getScenarioName();
    } else {
      return this.scenarioName;
    }
  }

  getScenarioImage(): string {
    if ( this.gameService.isOfflineMode() ) {
      if ( this.gameService.getGame().getScenario().getImagePath() && this.gameService.getGame().getScenario().getImagePath() != 'assets/') {
        return this.gameService.getGame().getScenario().getImagePath();
      } else {
        return '';
      }
    } else {
      let scenario = this.loadScenario(this.scenarioName);
      if ( scenario && scenario.getImagePath() && scenario.getImagePath() != 'assets/') {
        return scenario.getImagePath();
      } else {
        return '';
      }
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

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
