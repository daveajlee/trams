import {Component, OnDestroy, OnInit} from '@angular/core';
import {Vehicle} from './vehicle.model';
import {VehiclesService} from './vehicles.service';
import {HttpClient} from '@angular/common/http';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {ServerService} from "../shared/server.service";
import {VehicleResponse} from "./vehicle.response";

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
/**
 * This class implements the functionality for the vehicles component which retrieves vehicle data from the server and sends it to the
 * frontend component for rendering.
 */
export class VehiclesComponent implements OnInit, OnDestroy {

  private vehicles: Vehicle[];
  private doVehiclesExist: boolean;

  /**
   * Create a new vehicles component which constructs a data service and a vehicle service to retrieve data from the server.
   * @param http an http client which can make http calls
   * @param serverService which contains the HTTP connection to the server
   * @param vehiclesService which formats the HTTP calls into a way which the frontend can read and render.
   * @param gameService a service which retrieves game information
   * @param router a router service provided by Angular
   */
  constructor(private http: HttpClient, private serverService: ServerService, private vehiclesService: VehiclesService,
              private gameService: GameService, private router:Router) {
    if ( !this.gameService.isOfflineMode() ) {
      this.serverService.getVehicles().then((vehicles) => {
        if (vehicles) {
          this.doVehiclesExist = vehicles.vehicleResponses.length > 0;
          this.vehicles = [];
          for ( let i = 0; i < vehicles.vehicleResponses.length; i++ ) {
            if ( vehicles.vehicleResponses[i].vehicleStatus && vehicles.vehicleResponses[i].vehicleStatus != "SOLD") {
              this.vehicles.push(this.convertResponseToVehicle(vehicles.vehicleResponses[i]));
            } else if ( !vehicles.vehicleResponses[i].vehicleStatus ) {
              this.vehicles.push(this.convertResponseToVehicle(vehicles.vehicleResponses[i]));
            }
          }
        }
      });
    }
  }

  /**
   * Convert a response to a vehicle object.
   * @param vehicleResponse the response to be converted to a vehicle object.
   * @return the converted vehicle object.
   */
  convertResponseToVehicle( vehicleResponse: VehicleResponse ): Vehicle {
    return new Vehicle(vehicleResponse.fleetNumber, vehicleResponse.vehicleType, vehicleResponse.livery,
        vehicleResponse.allocatedTour, vehicleResponse.inspectionStatus, vehicleResponse.nextInspectionDueInDays,
        vehicleResponse.additionalTypeInformationMap);
  }

  /**
   * Initialise a new vehicles component which maintains a list of vehicles that can be updated and set from the server calls.
   */
  ngOnInit(): void {
    if ( this.gameService.isOfflineMode() ) {
      if ( this.gameService.getGame().doVehiclesExist() ) {
        this.vehicles = this.gameService.getGame().getVehicles();
      }
    }
  }

  /**
   * Retrieve the vehicles currently loaded.
   * @return the list of vehicles as an array of Vehicle objects.
   */
  getVehicles(): Vehicle[] {
    return this.vehicles;
  }

  /**
   * Search for the specified value as a fleet number.
   * @param searchValue the value to search for.
   */
  searchByFleetNumber(searchValue: string): void {
    if ( this.gameService.isOfflineMode() && this.gameService.getGame().doVehiclesExist() ) {
      var foundVehicle = this.gameService.getGame().getVehicleByFleetNumber(searchValue);
      if ( foundVehicle ) {
        this.vehicles = new Array(foundVehicle);
      } else {
        this.vehicles = this.gameService.getGame().getVehicles();
      }
    } else if ( this.doVehiclesExist ) {
      this.serverService.getVehicle(searchValue).then((foundVehicles) => {
        if ( foundVehicles.count > 0 ) {
          this.vehicles = [];
          for ( let i = 0; i < foundVehicles.vehicleResponses.length; i++ ) {
            if ( foundVehicles.vehicleResponses[i].vehicleStatus && foundVehicles.vehicleResponses[i].vehicleStatus != "SOLD") {
              this.vehicles.push(this.convertResponseToVehicle(foundVehicles.vehicleResponses[i]));
            } else if ( !foundVehicles.vehicleResponses[i].vehicleStatus ) {
              this.vehicles.push(this.convertResponseToVehicle(foundVehicles.vehicleResponses[i]));
            }
          }
        }
      });

    }
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
  }

  /**
   * When the user clicks on the back button, return to management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
