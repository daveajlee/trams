import {Component, OnDestroy, OnInit} from '@angular/core';

import {Subscription} from 'rxjs';
import {VehiclesService} from '../vehicles.service';
import {ActivatedRoute, Params} from '@angular/router';
import {Vehicle} from '../vehicle.model';
import {GameService} from "../../shared/game.service";

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
  private id: number;
  private idSubscription: Subscription;

  /**
   * Construct a new vehicle-detail component based on the supplied information.
   * @param vehiclesService a service which can retrieve and format vehicle information
   * @param gameService a service which can retrieve game information
   * @param route a variable which contains the current vehicle that the user clicked on.
   */
  constructor(private vehiclesService: VehiclesService, private route: ActivatedRoute, private gameService: GameService) { }

  /**
   * Initialise the vehicle information during construction and ensure all variables are set to the correct data.
   */
  ngOnInit(): void {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.id = +params['id'];
      if ( this.gameService.getGame().doVehiclesExist() ) {
        this.vehicle = this.gameService.getGame().getVehicleByPosition(this.id);
      } else {
        this.vehicle = this.vehiclesService.getVehicle(this.id);
      }
    });
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
    this.gameService.getGame().deleteVehicleByFleetNumber(vehicle.getFleetNumber());
  }

  /**
   * Get the vehicle that we are currently displaying.
   * @return the vehicle information as a Vehicle object.
   */
  getVehicle(): Vehicle {
    return this.vehicle;
  }

}
