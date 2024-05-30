import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../shared/game.service";

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
   * @param router a router service provided by Angular
   */
  constructor(private gameService: GameService, private router:Router) {
    if ( this.gameService.getGame() ) {
      this.level = this.gameService.getGame().getDifficultyLevel().toLowerCase();
      this.simulationInterval = this.gameService.getGame().getSimulationInterval();
    } else {
      this.level = "easy";
    }
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

  changeLevel(e): void {
    this.gameService.getGame().setDifficultyLevel(e.target.value);
  }

  changeSimulationInterval(): void {
    this.gameService.getGame().setSimulationInterval(this.simulationInterval);
  }

}
