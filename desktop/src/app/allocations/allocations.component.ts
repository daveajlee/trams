import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Route} from "../routes/route.model";
import {Allocation} from "./allocation.model";

@Component({
  selector: 'app-allocations',
  templateUrl: './allocations.component.html',
  styleUrls: ['./allocations.component.css']
})
export class AllocationsComponent {

  public selectedRouteNumber: string;
  public selectedFleetNumber: string;
  public selectedTourNumber: string;

  /**
   * Construct a new Allocations component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService: GameService, public router: Router) {
    if ( this.gameService.getGame().doRoutesExist()) {
        this.selectedRouteNumber = this.gameService.getGame().getFirstRouteNumber();
    }
    if ( this.gameService.getGame().doVehiclesExist() ) {
      this.selectedFleetNumber = this.gameService.getGame().getFirstFleetNumber();
    }
  }

  /**
   * Retrieve the list of defined route numbers.
   */
  getDefinedRouteNumbers(): string[] {
    return this.gameService.getGame().getRouteNumbers();
  }

  /**
   * Retrieve the list of defined fleet numbers.
   */
  getDefinedFleetNumbers(): string[] {
    return this.gameService.getGame().getFleetNumbers();
  }

  /**
   * Retrieve the list of defined tour numbers for the selected route.
   */
  getDefinedTourNumbers(): string[] {
    if ( this.selectedRouteNumber ) {
      var selectedRouteObject: Route = this.gameService.getGame().getRoute(this.selectedRouteNumber);
      if ( selectedRouteObject ) {
        // We take the first timetable at the moment.
        if ( selectedRouteObject.doTimetablesExist() && selectedRouteObject.doFrequencyPatternsExist(0) ) {
          var tours = [];
          for (var k = 0; k < selectedRouteObject.getNumberTours(0, 0); k++) {
            tours.push((k + 1));
          }
          return tours;
        }
        return [];
      } else {
        return [];
      }
    } else {
      return [];
    }
  }

  /**
   * When we click on the save allocations button, we should assign the tour to the vehicle and return to management screen.
   */
  onSaveAllocation(): void {
    console.log('I want to allocate ' + this.selectedRouteNumber + '/' + this.selectedTourNumber + " to " + this.selectedFleetNumber);
    this.gameService.getGame().addAllocation(new Allocation(this.selectedRouteNumber, this.selectedFleetNumber, this.selectedTourNumber));
    this.gameService.getGame().getVehicleByFleetNumber(this.selectedFleetNumber).allocatedTour = this.selectedRouteNumber + "/" + this.selectedTourNumber;
    this.router.navigate(['management']);
  }

  /**
   * When we click the back button, we should return to the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
