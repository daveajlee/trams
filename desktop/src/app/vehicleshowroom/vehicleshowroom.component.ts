import { Component } from '@angular/core';
import {VehicleModel} from "../vehicles/vehiclemodel.model";
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {DatePipe} from "@angular/common";
import {Vehicle} from "../vehicles/vehicle.model";

@Component({
  selector: 'app-vehicleshowroom',
  templateUrl: './vehicleshowroom.component.html',
  styleUrls: ['./vehicleshowroom.component.css']
})
export class VehicleshowroomComponent {

  models: VehicleModel[];
  currentDisplay: number;
  gameService: GameService
  deliveryDate: Date
  quantity: number;

  /**
   * Construct a new Vehicle Showroom component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   * @param datePipe a date pipe object for transforming dates in Angular.
   */
  constructor(private gameService2: GameService, public router: Router, private datePipe: DatePipe) {
    this.gameService = gameService2;
    this.models = [new VehicleModel('MyBus Single Decker', 'Single Decker Bus', 44, 36, 85000.0, 'assets/singledecker-bus-pixabay.png'),
        new VehicleModel('MyBus Double Decker', 'Double Decker Bus', 78, 25,160000, 'assets/doubledecker-bus-pixabay.png' ),
        new VehicleModel('MyBus Bendy', 'Bendy Bus', 48, 97, 190000, 'assets/bendybus-albertstoynov-unsplash.jpg'),
        new VehicleModel('MyTram Tram 1', 'Tram',104, 83, 280000, 'assets/tram-pixabay.png')];
    this.currentDisplay = 0;
    this.quantity = 1;
    this.deliveryDate = this.gameService.getGame().currentDateTime;
    this.deliveryDate.setDate(this.deliveryDate.getDate() + 5);
  }

  getVehiclePicture(): string {
    return this.models[this.currentDisplay].picture;
  }

  getVehicleType(): string {
    return this.models[this.currentDisplay].modelName;
  }

  getVehicleSeatingCapacity(): number {
    return this.models[this.currentDisplay].seatingCapacity;
  }

  getVehicleStandingCapacity(): number {
    return this.models[this.currentDisplay].standingCapacity;
  }

  getVehicleDeliveryDate(): string {
    return this.datePipe.transform(this.deliveryDate, 'yyyy-MM-dd');
  }

  getVehiclePurchasePrice(): number {
    return this.models[this.currentDisplay].value;
  }

  getPossibleQuantities(): number[] {
    return [1,2,3,4,5,6,7,8,9,10];
  }

  getTotalPrice(): number {
    return this.models[this.currentDisplay].value * this.quantity;
  }

  onPurchaseVehicle(): void {
    // First we determine the next fleet number,
    var vehicles = this.gameService.getGame().vehicles;
    var highestFleetNumberSoFar = 0;
    for ( var i = 0; i < vehicles.length; i++ ) {
      if ( parseInt(vehicles[i].fleetNumber) > highestFleetNumberSoFar ) {
        highestFleetNumberSoFar = parseInt(vehicles[i].fleetNumber);
      }
    }
    const additionalProps = new Map<string, string>();
    additionalProps.set('Model', this.getVehicleType());
    additionalProps.set('Age', '0 months');
    additionalProps.set('Standing Capacity', '' + this.getVehicleStandingCapacity());
    additionalProps.set('Seating Capacity', '' + this.getVehicleSeatingCapacity());
    additionalProps.set('Value', '' + this.getVehiclePurchasePrice());
    if ( this.getVehiclePurchasePrice() > this.gameService.getGame().balance ) {
      alert('You need to earn more money before you can buy new vehicles!');
    } else {
      this.gameService.getGame().addVehicle(new Vehicle('' + (highestFleetNumberSoFar + 1), this.models[this.currentDisplay].modelType, '',
          '', '', 0, additionalProps));
      this.gameService.getGame().balance -= this.getVehiclePurchasePrice();
    }
    this.router.navigate(['management']);
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

  checkDisablePrevious():boolean {
    return this.currentDisplay === 0;
  }

  checkDisableNext():boolean {
    return (this.currentDisplay + 1) >= this.models.length;
  }

  moveToPreviousVehicleType(): void {
    this.currentDisplay -= 1;
  }
  moveToNextVehicleType(): void {
    this.currentDisplay += 1;
  }

}
