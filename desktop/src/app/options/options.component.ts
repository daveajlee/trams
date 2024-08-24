import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {ServerService} from "../shared/server.service";

@Component({
  selector: 'app-options',
  templateUrl: './options.component.html',
  styleUrls: ['./options.component.css']
})
export class OptionsComponent {

  level: string;
  simulationInterval: number;

  /**
   * Create a new options component.
   * @param gameService a service which retrieves game information
   * @param serverService a service which manages the http calls
   * @param router a router service provided by Angular
   */
  constructor(private gameService: GameService, private serverService: ServerService, private router:Router) {
    if ( this.gameService.isOfflineMode() ) {
      this.level = this.gameService.getGame().getDifficultyLevel().toLowerCase();
      this.simulationInterval = this.gameService.getGame().getSimulationInterval();
    } else {
      this.serverService.getDifficultyLevel().then((level) => {
        this.level = level.toLowerCase();
      })
      this.serverService.getSimulationInterval().then((interval) => {
        this.simulationInterval = interval;
      })
    }
  }

  /**
   * Return to the management screen when clicking on the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

  /**
   * Change the difficulty level for the game to the new difficulty level that the user selected.
   * @param e the event triggered by the user clicking on a radio button which must contain a target and a value.
   */
  changeLevel(e): void {
    if ( this.gameService.isOfflineMode() ) {
      this.gameService.getGame().setDifficultyLevel(e.target.value);
    } else {
      this.serverService.adjustDifficultyLevel(e.target.value).then(() => {
        alert('Difficulty level was adjusted successfully');
      });
    }
  }

  /**
   * Change the simulation interval for the game to match the simulation interval that the user entered.
   */
  changeSimulationInterval(): void {
    if ( this.gameService.isOfflineMode() ) {
      this.gameService.getGame().setSimulationInterval(this.simulationInterval);
    } else {
     this.serverService.adjustSimulationInterval(this.simulationInterval).then(() => {
       alert('Simulation interval was adjusted successfully');
     });
    }
  }

}
