import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Route} from "../routes/route.model";

@Component({
  selector: 'app-allocations',
  templateUrl: './allocations.component.html',
  styleUrls: ['./allocations.component.css']
})
export class AllocationsComponent {

  gameService: GameService;
  selectedRouteNumber: string;
  selectedFleetNumber: string;
  selectedTourNumber: string;

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

  onSaveAllocation(): void {
    alert('I want to use vehicle ' + this.selectedFleetNumber + ' for ' + this.selectedRouteNumber + '/' + this.selectedTourNumber);
    this.router.navigate(['management']);
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
