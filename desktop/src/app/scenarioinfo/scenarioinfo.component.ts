import { Component, OnInit } from '@angular/core';
import { GameService } from '../shared/game.service';
import { Router } from '@angular/router';
import { SCENARIOS } from 'src/data/scenario.data';
import { Scenario } from '../shared/scenario.model';

@Component({
  selector: 'app-scenarioinfo',
  templateUrl: './scenarioinfo.component.html',
  styleUrls: ['./scenarioinfo.component.css']
})
/**
 * View Information screen from Management Screen.
 */
export class ScenarioinfoComponent implements OnInit {

  gameService: GameService;

  /**
   * Constructor to create the screen
   * @param gameService2 the game service
   * @param router router to enable us to move other screens
   */
  constructor(private gameService2: GameService, public router: Router ) { 
    this.gameService = gameService2;
  }

  ngOnInit(): void {
  }

  /**
   * Retrieve the company name
   * @returns the company name
   */
  getCompanyName(): string {
    return this.gameService.getGame().companyName;
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
    const scenarioName = this.gameService.getGame().scenario.scenarioName;
    for ( var i = 0; i < SCENARIOS.length; i++ ) {
      if ( SCENARIOS[i].scenarioName === scenarioName ) {
        return SCENARIOS[i];
      }
    }
  }

  /**
   * Retrieve the player name
   * @returns the player name
   */
  getPlayerName(): string {
    return this.gameService.getGame().playerName;
  }

}
