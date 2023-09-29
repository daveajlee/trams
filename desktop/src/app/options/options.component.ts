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

  /**
   * Create a new options component.
   * @param gameService a service which retrieves game information
   * @param router a router service provided by Angular
   */
  constructor(private gameService: GameService, private router:Router) {
    this.level = gameService.getGame().difficultyLevel.toLowerCase();
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

  changeLevel(e): void {
    this.gameService.getGame().difficultyLevel = e.target.value;
  }

}
