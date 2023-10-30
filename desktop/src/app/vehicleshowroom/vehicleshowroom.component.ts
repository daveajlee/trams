import { Component } from '@angular/core';
import {VehicleModel} from "../vehicles/vehiclemodel.model";
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-vehicleshowroom',
  templateUrl: './vehicleshowroom.component.html',
  styleUrls: ['./vehicleshowroom.component.css']
})
export class VehicleshowroomComponent {

  models: VehicleModel[];
  currentDisplay: number;
  gameService: GameService
  currentDate: Date
  quantity: number;

  /**
   * Construct a new Vehicle Showroom component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   * @param datePipe a date pipe object for transforming dates in Angular.
   */
  constructor(private gameService2: GameService, public router: Router, private datePipe: DatePipe) {
    this.gameService = gameService2;
    this.models = [new VehicleModel('MyBus Single Decker', 44, 36, 85000.0, 'assets/Bus.jpg')];
    this.currentDisplay = 0;
    this.quantity = 1;
    this.currentDate = new Date();
    this.currentDate.setDate(this.currentDate.getDate() + 5);
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
    return this.datePipe.transform(this.currentDate, 'yyyy-MM-dd');
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
    alert('Coming Soon!');
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

}
