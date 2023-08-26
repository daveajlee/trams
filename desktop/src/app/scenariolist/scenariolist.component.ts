import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ScenarioService} from './scenario.service';
import {GameService} from '../shared/game.service';
import {Game} from '../game/game.model';
import { Scenario } from '../shared/scenario.model';
import { SCENARIOS } from 'src/data/scenario.data';
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";

@Component({
  selector: 'app-scenariolist',
  templateUrl: './scenariolist.component.html',
  styleUrls: ['./scenariolist.component.css']
})
export class ScenariolistComponent implements OnInit {

  company: string;
  playerName: string;
  difficultyLevel: string;
  startingDate: string;

  scenarios: Scenario[];

  /**
   * Create a new scenario list component which displays a series of scenarios that the user can choose from.
   * @param scenarioService which manages the creation of a new company and scenario.
   */
  constructor(private route: ActivatedRoute, private scenarioService: ScenarioService, private gameService: GameService,
              public router: Router) { }

    /**
     * Copy parameters from last request so that we do not lose the information that the user has provided.
     */
  ngOnInit(): void {
      this.route.queryParams
        .subscribe(params => {
              this.company = params.company;
              this.playerName = params.playerName;
              this.difficultyLevel = params.difficultyLevel;
              this.startingDate = params.startingDate;
            }
        );
      this.scenarios = SCENARIOS;
  }

    /**
     * When the user has chosen a scenario, we should create the company via API.
     * @param scenario which contains the name of the scenario that the user chose.
     */
  onScenarioSelect(scenario: string): void {
      this.gameService.setGame(new Game(this.company, this.playerName, this.startingDate, this.loadScenario(scenario), this.difficultyLevel,));
      console.log(this.gameService.getGame().scenario.scenarioName);
      this.router.navigate(['management']);
      // this.scenarioService.createCompany(this.company, this.playerName, this.difficultyLevel, this.startingDate, scenario);
  }

    /**
     * This is a helper method to load the correct scenario based on the supplied scenario name.
     * @param scenario which contains the name of the scenario that the user chose.
     * @returns the scenario object corresponding to the supplied name.
     */
    loadScenario(scenario: string): Scenario {
        if ( scenario === SCENARIO_LANDUFF.scenarioName ) {
            return SCENARIO_LANDUFF;
        } else if ( scenario === SCENARIO_LONGTS.scenarioName) {
            return SCENARIO_LONGTS;
        } else if ( scenario === SCENARIO_MDORF.scenarioName ) {
            return SCENARIO_MDORF;
        } else {
            return null;
        }
    }

  

}
