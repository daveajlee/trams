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

  allocations: Allocation[];

  /**
   * Construct a new Allocations list component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService: GameService, public router: Router) {
  }

  /**
   * Initialise a new allocations component which maintains a list of allocations.
   */
  ngOnInit(): void {
    this.allocations = this.gameService.getGame().getAllocations();
  }

  deleteAllocation(routeNumber: string, fleetNumber: string, tourNumber: string): void {
    if ( this.gameService.getGame().deleteAllocation(fleetNumber, routeNumber, tourNumber) ) {
        alert('Allocation was deleted successfully');
        this.router.navigate(['allocationslist']);
    }

  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
