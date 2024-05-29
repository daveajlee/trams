import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Allocation} from "../allocations/allocation.model";

@Component({
  selector: 'app-allocationslist',
  templateUrl: './allocationslist.component.html',
  styleUrls: ['./allocationslist.component.css']
})
export class AllocationslistComponent {

  gameService: GameService;
  allocations: Allocation[];

  /**
   * Construct a new Allocations list component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService2: GameService, public router: Router) {
    this.gameService = gameService2;
    this.allocations = this.gameService.getGame().allocations;
  }

  /**
   * Initialise a new allocations component which maintains a list of allocations.
   */
  ngOnInit(): void {
    this.allocations = this.gameService.getGame().allocations;
  }

  deleteAllocation(routeNumber: string, fleetNumber: string, tourNumber: string): void {
    var allAllocations = this.gameService.getGame().allocations;
    for ( var i = 0; i < allAllocations.length; i++ ) {
      if ( this.gameService.getGame().allocations[i].getFleetNumber().valueOf() === fleetNumber.valueOf() &&
          this.gameService.getGame().allocations[i].getRouteNumber().valueOf() === routeNumber.valueOf() &&
          this.gameService.getGame().allocations[i].getTourNumber().valueOf() === tourNumber.valueOf()
      ) {
        this.gameService.getGame().allocations.splice(i, 1);
        alert('Allocation was deleted successfully');
        this.router.navigate(['allocationslist']);
      }
    }

  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
