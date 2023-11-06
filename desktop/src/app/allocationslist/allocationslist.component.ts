import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-allocationslist',
  templateUrl: './allocationslist.component.html',
  styleUrls: ['./allocationslist.component.css']
})
export class AllocationslistComponent {

  gameService: GameService;

  /**
   * Construct a new Allocations list component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService2: GameService, public router: Router) {
    this.gameService = gameService2;
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
