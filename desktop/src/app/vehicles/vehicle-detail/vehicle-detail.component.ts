import {Component, OnDestroy, OnInit} from '@angular/core';

import {Subscription} from 'rxjs';
import {VehiclesService} from '../vehicles.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Vehicle} from '../vehicle.model';
import {GameService} from "../../shared/game.service";
import {VehicleResponse} from "../vehicle.response";
import {ServerService} from "../../shared/server.service";

@Component({
  selector: 'app-vehicle-detail',
  templateUrl: './vehicle-detail.component.html',
  styleUrls: ['./vehicle-detail.component.css']
})
/**
 * This class implements the functionality for the vehicle-detail component which retrieves detailed vehicle information and
 * sends it to the frontend component for rendering.
 */
export class VehicleDetailComponent implements OnInit, OnDestroy {

  private vehicle: Vehicle;
  private fleetNumber: string;
  private idSubscription: Subscription;
  public vehiclePictureLink: string;

  /**
   * Construct a new vehicle-detail component based on the supplied information.
   * @param vehiclesService a service which can retrieve and format vehicle information
   * @param gameService a service which can retrieve game information
   * @param route a variable which contains the current vehicle that the user clicked on.
   * @param serverService which contains the HTTP connection to the server
   * @param router a router service provided by Angular
   */
  constructor(private vehiclesService: VehiclesService, private route: ActivatedRoute, private gameService: GameService,
              private serverService: ServerService, private router:Router) { }

  /**
   * Initialise the vehicle information during construction and ensure all variables are set to the correct data.
   */
  ngOnInit(): void {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.fleetNumber = params['id'];
      if ( this.gameService.isOfflineMode() && this.gameService.getGame().doVehiclesExist() ) {
        this.vehicle = this.gameService.getGame().getVehicleByFleetNumber(this.fleetNumber);
        this.vehiclePictureLink = this.getVehiclePictureLink();
      } else {
        this.serverService.getVehicle(this.fleetNumber).then((foundVehicles) => {
          if ( foundVehicles.count === 1 ) {
            this.vehicle = this.convertResponseToVehicle(foundVehicles.vehicleResponses[0]);
            this.vehiclePictureLink = this.getVehiclePictureLink();
          }
        });
      }
    });
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
   * When destroying this component we should ensure that all subscriptions are cancelled.
   */
  ngOnDestroy(): void {
    this.idSubscription.unsubscribe();
  }

  getVehiclePictureLink(): string {
    if ( this.vehicle.getVehicleType() === 'Single Decker Bus') {
      return 'assets/singledecker-bus-pixabay.png';
    } else if ( this.vehicle.getVehicleType() === 'Double Decker Bus') {
      return 'assets/doubledecker-bus-pixabay.png';
    } else if ( this.vehicle.getVehicleType() === 'Bendy Bus') {
      return 'assets/bendybus-albertstoynov-unsplash.jpg';
    } else if ( this.vehicle.getVehicleType() === 'Tram') {
      return 'assets/tram-pixabay.png';
    }
    else if ( this.vehicle.getVehicleType() === 'Train') {
      return 'assets/' + this.vehicle.getVehicleType()+ '-' + this.vehicle.getPowerMode() + '.jpg';
    } else {
      return 'assets/' + this.vehicle.getVehicleType() + '.jpg';
    }
  }

  sellVehicle(vehicle: Vehicle): void {
    if ( this.gameService.isOfflineMode() ) {
      this.gameService.getGame().deleteVehicleByFleetNumber(vehicle.getFleetNumber());
    } else {
      this.serverService.sellVehicle(vehicle.getFleetNumber()).then((sellVehicleResponse) => {
        if (sellVehicleResponse ) {
          this.serverService.adjustBalance(sellVehicleResponse.soldPrice);
          this.router.navigate(['management']);
        }
      })
    }
  }

  /**
   * Get the vehicle that we are currently displaying.
   * @return the vehicle information as a Vehicle object.
   */
  getVehicle(): Vehicle {
    return this.vehicle;
  }

}
