import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ScenarioService} from './scenario.service';
import {GameService} from '../shared/game.service';
import {Game} from '../game/game.model';

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
  }

    /**
     * When the user has chosen a scenario, we should create the company via API.
     * @param scenario which contains the name of the scenario that the user chose.
     */
  onScenarioSelect(scenario: string): void {
      this.gameService.setGame(new Game(this.company, this.playerName, this.difficultyLevel, this.startingDate, scenario));
      this.router.navigate(['management']);
      // this.scenarioService.createCompany(this.company, this.playerName, this.difficultyLevel, this.startingDate, scenario);
  }

}
