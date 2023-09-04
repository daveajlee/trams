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

  vehicle: Vehicle;
  id: number;
  idSubscription: Subscription;

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
      if ( this.gameService.getGame().vehicles.length > 0 ) {
        this.vehicle = this.gameService.getGame().vehicles[this.id];
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
    if ( this.vehicle.vehicleType === 'Train') {
      return this.vehicle.vehicleType + '-' + this.vehicle.additionalTypeInformationMap['Power Mode'];
    }
    return this.vehicle.vehicleType;
  }

  sellVehicle(vehicle: Vehicle): void {
    console.log('It is currently not possible to sell vehicles for vehicle ' + vehicle.fleetNumber);
  }
  /*verifyMap(): void {
    this.vehicle.additionalTypeInformationMap.forEach().forEach((key: string, value: string) => {
      console.log(key, value);
    });
  }*/

}
