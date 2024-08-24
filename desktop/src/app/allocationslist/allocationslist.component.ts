import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Allocation} from "../allocations/allocation.model";
import {ServerService} from "../shared/server.service";

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
   * @param serverService the service managing the http calls.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService: GameService, private serverService: ServerService, public router: Router) {
    if ( this.gameService.isOfflineMode() ) {
      this.allocations = this.gameService.getGame().getAllocations();
    } else {
      this.serverService.getAllocations().then(allocations => {
        this.allocations = allocations;
      });
    }
  }

  /**
   * Initialise a new allocations component which maintains a list of allocations.
   */
  ngOnInit(): void {

  }

  deleteAllocation(routeNumber: string, fleetNumber: string, tourNumber: string): void {
    if ( this.gameService.isOfflineMode() ) {
      if ( this.gameService.getGame().deleteAllocation(fleetNumber, routeNumber, tourNumber) ) {
        alert('Allocation was deleted successfully');
        this.router.navigate(['management']);
      }
    } else {
     this.serverService.deleteAllocation(fleetNumber).then(() => {
       alert('Allocation was deleted successfully');
       this.router.navigate(['management']);
     })
    }
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
