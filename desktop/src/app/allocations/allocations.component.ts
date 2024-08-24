import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Route} from "../routes/route.model";
import {Allocation} from "./allocation.model";
import {ServerService} from "../shared/server.service";
import {AllocationRequest} from "./allocation.request";

@Component({
  selector: 'app-allocations',
  templateUrl: './allocations.component.html',
  styleUrls: ['./allocations.component.css']
})
export class AllocationsComponent {

  public selectedRouteNumber: string;
  public selectedFleetNumber: string;
  public selectedTourNumber: string;

  public routeNumbers: string[];
  public fleetNumbers: string[];
  public tours: string[];

  /**
   * Construct a new Allocations component
   * @param gameService the game service containing the currently loaded game.
   * @param serverService the service managing the http calls.
   * @param router the router for navigating to other pages.
   */
  constructor(private gameService: GameService, private serverService: ServerService, public router: Router) {
    if ( this.gameService.isOfflineMode() ) {
      if ( this.gameService.getGame().doRoutesExist()) {
        this.selectedRouteNumber = this.gameService.getGame().getFirstRouteNumber();
        this.routeNumbers = this.gameService.getGame().getRouteNumbers();
      }
      if ( this.gameService.getGame().doVehiclesExist() ) {
        this.selectedFleetNumber = this.gameService.getGame().getFirstFleetNumber();
        this.fleetNumbers = this.gameService.getGame().getFleetNumbers();
      }
    } else {
      this.serverService.getRoutes().then((routes) => {
        if ( routes.count > 0 ) {
          this.selectedRouteNumber = routes.routeResponses[0].routeNumber;
          this.routeNumbers = [];
          for ( let i = 0; i < routes.routeResponses.length; i++ ) {
            this.routeNumbers.push(routes.routeResponses[i].routeNumber);
          }
        }
        this.serverService.getVehicles().then((vehicles) => {
          if ( vehicles.count > 0 ) {
            this.fleetNumbers = [];
            for ( let i = 0; i < vehicles.vehicleResponses.length; i++ ) {
              this.fleetNumbers.push(vehicles.vehicleResponses[i].fleetNumber);
            }
            this.selectedFleetNumber = vehicles.vehicleResponses[0].fleetNumber;
            this.getDefinedTourNumbers();
          }
        })
      })
    }
  }

  /**
   * Retrieve the list of defined route numbers.
   */
  getDefinedRouteNumbers(): string[] {
    return this.routeNumbers;
  }

  /**
   * Retrieve the list of defined fleet numbers.
   */
  getDefinedFleetNumbers(): string[] {
    return this.fleetNumbers;
  }

  /**
   * Retrieve the list of defined tour numbers for the selected route.
   */
  async getDefinedTourNumbers(): Promise<void> {
    if ( this.selectedRouteNumber ) {
      if ( this.gameService.isOfflineMode() ) {
        let selectedRouteObject: Route = this.gameService.getGame().getRoute(this.selectedRouteNumber);
        if ( selectedRouteObject ) {
          // We take the first timetable at the moment.
          if ( selectedRouteObject.doTimetablesExist() && selectedRouteObject.doFrequencyPatternsExist(0) ) {
            this.tours = [];
            for (var k = 0; k < selectedRouteObject.getNumberTours(0, 0); k++) {
              this.tours.push("" + (k + 1));
            }
          }
        } else {
          this.tours = [];
        }
      } else {
        // Retrieve the route
        this.serverService.getRoute(this.selectedRouteNumber).then((route) => {
          // Retrieve the current date.
          this.serverService.getCurrentDateTime().then((currentDateTime) => {
            // Retrieve the schedules.
            this.serverService.getStopTimes(this.selectedRouteNumber, currentDateTime.split(" ")[0], route.startStop, "").then((stopTimes) => {
              this.tours = [];
              for ( let i = 0; i < stopTimes.count; i++ ) {
                if ( !this.tours.includes(this.selectedRouteNumber + "/" + stopTimes.stopTimeResponses[i].scheduleNumber) ) {
                  this.tours.push(this.selectedRouteNumber + "/" + stopTimes.stopTimeResponses[i].scheduleNumber);
                }
              }
              if ( this.tours ) {
                this.selectedTourNumber = this.tours[0];
              }
            })
          })
        })
      }
    } else {
      this.tours = [];
    }
  }

  /**
   * When we click on the save allocations button, we should assign the tour to the vehicle and return to management screen.
   */
  onSaveAllocation(): void {
    console.log('I want to allocate ' + this.selectedRouteNumber + '/' + this.selectedTourNumber + " to " + this.selectedFleetNumber);
    if ( this.gameService.isOfflineMode() ) {
      this.gameService.getGame().addAllocation(new Allocation(this.selectedRouteNumber, this.selectedFleetNumber, this.selectedTourNumber));
      this.gameService.getGame().getVehicleByFleetNumber(this.selectedFleetNumber).setAllocatedTour(this.selectedRouteNumber + "/" + this.selectedTourNumber);
      this.router.navigate(['management']);
    } else {
      this.serverService.addAllocation(this.selectedRouteNumber, this.selectedFleetNumber, this.selectedTourNumber).then(() => {
        this.router.navigate(['management']);
      })
    }

  }

  /**
   * When we click the back button, we should return to the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
