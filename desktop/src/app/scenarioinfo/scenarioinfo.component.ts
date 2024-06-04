import { Component, OnInit } from '@angular/core';
import { GameService } from '../shared/game.service';
import { Router } from '@angular/router';
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

  /**
   * Constructor to create the screen
   * @param gameService the game service
   * @param router router to enable us to move other screens
   */
  constructor(private gameService: GameService, public router: Router ) {
  }

  ngOnInit(): void {
  }

  /**
   * Retrieve the company name
   * @returns the company name
   */
  getCompanyName(): string {
    return this.gameService.getGame().getCompanyName();
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
    return this.gameService.getGame().getScenario();
  }

  /**
   * Retrieve the player name
   * @returns the player name
   */
  getPlayerName(): string {
    return this.gameService.getGame().getPlayerName();
  }

}
