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

  private gameService: GameService;
  public selectedRouteNumber: string;
  public selectedFleetNumber: string;
  public selectedTourNumber: string;

  /**
   * Construct a new Allocations component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService2: GameService, public router: Router) {
    this.gameService = gameService2;
    if ( this.gameService.getGame().routes.length > 0 ) {
        this.selectedRouteNumber = this.gameService.getGame().routes[0].routeNumber;
    }
    if ( this.gameService.getGame().vehicles.length > 0 ) {
      this.selectedFleetNumber = this.gameService.getGame().vehicles[0].fleetNumber;
    }
    this.selectedTourNumber = "1";
  }

  /**
   * Retrieve the list of defined route numbers.
   */
  getDefinedRouteNumbers(): string[] {
    var routeNumbers = [];
    for ( var i = 0; i < this.gameService.getGame().routes.length; i++ ) {
      routeNumbers[i] = this.gameService.getGame().routes[i].routeNumber;
    }
    return routeNumbers;
  }

  /**
   * Retrieve the list of defined fleet numbers.
   */
  getDefinedFleetNumbers(): string[] {
    var fleetNumbers = [];
    for ( var i = 0; i < this.gameService.getGame().vehicles.length; i++ ) {
      fleetNumbers[i] = this.gameService.getGame().vehicles[i].fleetNumber;
    }
    return fleetNumbers;
  }

  /**
   * Retrieve the list of defined tour numbers for the selected route.
   */
  getDefinedTourNumbers(): string[] {
    if ( this.selectedRouteNumber ) {
      var selectedRouteObject: Route;
      for ( var j = 0; j < this.gameService.getGame().routes.length; j++ ) {
        if ( this.selectedRouteNumber == this.gameService.getGame().routes[j].routeNumber ) {
          selectedRouteObject = this.gameService.getGame().routes[j];
          break;
        }
      }
      if ( selectedRouteObject ) {
        // We take the first timetable at the moment.
        if ( selectedRouteObject.timetables.length > 0 && selectedRouteObject.timetables[0].frequencyPatterns.length > 0 ) {
          var tours = [];
          for (var k = 0; k < selectedRouteObject.timetables[0].frequencyPatterns[0].numTours; k++) {
            tours.push((k + 1));
          }
          this.selectedTourNumber = tours[0];
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
    this.gameService.getGame().addAllocation(new Allocation(this.selectedRouteNumber, this.selectedFleetNumber, this.selectedTourNumber));
    this.gameService.getGame().retrieveVehicleByFleetNumber(this.selectedFleetNumber).allocatedTour = this.selectedRouteNumber + "/" + this.selectedTourNumber;
    this.router.navigate(['management']);
  }

  /**
   * When we click the back button, we should return to the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

  /**
   * Retrieve the route number which is currently selected.
   * @return the route number that is selected.
   */
  getSelectedRouteNumber(): string {
    return this.selectedRouteNumber;
  }

  /**
   * Retrieve the fleet number which is currently selected.
   * @return the fleet number that is selected.
   */
  getSelectedFleetNumber(): string {
    return this.selectedFleetNumber;
  }

  /**
   * Retrieve the tour number which is currently selected.
   * @return the tour number that is selected.
   */
  getSelectedTourNumber(): string {
    return this.selectedTourNumber;
  }

}
