import { Component } from '@angular/core';
import {VehicleModel} from "../vehicles/vehiclemodel.model";
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {DatePipe} from "@angular/common";
import {Vehicle} from "../vehicles/vehicle.model";
import {AdditionalTypeInformation} from "../vehicles/additionalTypeInfo.model";
import {ServerService} from "../shared/server.service";
import {TimeHelper} from "../shared/time.helper";
import {VehicleRequest} from "../vehicles/vehicle.request";
import {Scenario} from "../shared/scenario.model";
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";

@Component({
  selector: 'app-vehicleshowroom',
  templateUrl: './vehicleshowroom.component.html',
  styleUrls: ['./vehicleshowroom.component.css']
})
export class VehicleshowroomComponent {

  private models: VehicleModel[];
  private currentDisplay: number;
  private deliveryDate: Date
  quantity: number;

  /**
   * Construct a new Vehicle Showroom component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   * @param datePipe a date pipe object for transforming dates in Angular.
   * @param serverService the server service containing the http connections to the server.
   */
  constructor(private gameService: GameService, public router: Router, private datePipe: DatePipe, private serverService: ServerService) {
    this.models = [new VehicleModel('MyBus Single Decker', 'Single Decker Bus', 44, 36, 85000.0, 'assets/singledecker-bus-pixabay.png'),
        new VehicleModel('MyBus Double Decker', 'Double Decker Bus', 78, 25,160000, 'assets/doubledecker-bus-pixabay.png' ),
        new VehicleModel('MyBus Bendy', 'Bendy Bus', 48, 97, 190000, 'assets/bendybus-albertstoynov-unsplash.jpg'),
        new VehicleModel('MyTram Tram 1', 'Tram',104, 83, 280000, 'assets/tram-pixabay.png')];
    this.currentDisplay = 0;
    this.quantity = 1;
    if ( this.gameService.isOfflineMode() ) {
      this.deliveryDate = this.gameService.getGame().getCurrentDateTime();
      this.deliveryDate.setDate(this.deliveryDate.getDate() + 5);
    } else {
      this.serverService.getCurrentDateTime().then((dateTime) => {
        this.deliveryDate = TimeHelper.formatStringAsDateObject(dateTime);
        this.deliveryDate.setDate(this.deliveryDate.getDate() + 5);
      })
    }
  }

  getVehiclePicture(): string {
    return this.models[this.currentDisplay].getPicture();
  }

  getVehicleType(): string {
    return this.models[this.currentDisplay].getModelName();
  }

  getVehicleSeatingCapacity(): number {
    return this.models[this.currentDisplay].getSeatingCapacity();
  }

  getVehicleStandingCapacity(): number {
    return this.models[this.currentDisplay].getStandingCapacity();
  }

  getVehicleDeliveryDate(): string {
    return this.datePipe.transform(this.deliveryDate, 'yyyy-MM-dd');
  }

  getVehiclePurchasePrice(): number {
    return this.models[this.currentDisplay].getValue();
  }

  getPossibleQuantities(): number[] {
    return [1,2,3,4,5,6,7,8,9,10];
  }

  getTotalPrice(): number {
    return this.models[this.currentDisplay].getValue() * this.quantity;
  }

  onPurchaseVehicle(): void {
    // First we determine the next fleet number.
    if ( this.gameService.isOfflineMode() ) {
      let highestFleetNumberSoFar = this.gameService.getGame().getHighestFleetNumber();
      if ( this.getVehiclePurchasePrice() > this.gameService.getGame().getBalance() ) {
        alert('You need to earn more money before you can buy new vehicles!');
      } else {
        this.gameService.getGame().addVehicle(new Vehicle('' + (highestFleetNumberSoFar + 1), this.models[this.currentDisplay].getModelType(), '',
            '', '', 0, this.generateAdditionalProps(this.models[this.currentDisplay].getModelType(), (highestFleetNumberSoFar + 1), this.gameService.getGame().getScenario().getScenarioName())));
        this.gameService.getGame().withdrawBalance(this.getVehiclePurchasePrice());
      }
      this.router.navigate(['management']);
    } else {
      this.serverService.getBalance().then((balance) => {
        if ( this.getVehiclePurchasePrice() > balance ) {
          alert('You need to earn more money before you can buy new vehicles!');
        } else {
          this.serverService.getHighestFleetNumber().then((fleetNumber) => {
            this.serverService.getScenarioName().then((scenarioName) => {
              let vehicleType = this.models[this.currentDisplay].getModelType().includes('Bus') ? 'BUS' : 'TRAM';
              let vehicleRequest = new VehicleRequest('' + (fleetNumber + 1), this.serverService.getCompanyName(), vehicleType, 'None',
                  this.generateAdditionalProps(vehicleType, (fleetNumber + 1), scenarioName), '' + this.models[this.currentDisplay].getSeatingCapacity(), '' + this.models[this.currentDisplay].getStandingCapacity(),
                  this.models[this.currentDisplay].getModelName());
              console.log(vehicleRequest);
              this.serverService.addVehicle(vehicleRequest).then(() => {
                this.serverService.adjustBalance(-this.getVehiclePurchasePrice()).then(() => {
                  this.router.navigate(['management']);
                });
              })
            })
          })
        }
      })
    }
  }

  generateAdditionalProps(vehicleType: string, fleetNumber: number, scenarioName: string): AdditionalTypeInformation {
    const additionalProps = new AdditionalTypeInformation();
    additionalProps.setModel(this.getVehicleType());
    additionalProps.setAge('0 months');
    additionalProps.setStandingCapacity('' + this.getVehicleStandingCapacity());
    additionalProps.setSeatingCapacity('' + this.getVehicleSeatingCapacity());
    additionalProps.setValue('' + this.getVehiclePurchasePrice());
    if ( vehicleType.toUpperCase() == 'BUS' ) {
      additionalProps.setRegistrationNumber('' + this.loadScenario(scenarioName).getRegistrationShortCode() + "-" + new Date().getFullYear() + "-" +  fleetNumber);
    }
    return additionalProps;
  }

  /**
   * This is a helper method to load the correct scenario based on the supplied scenario name.
   * @param scenario which contains the name of the scenario that the user chose.
   * @returns the scenario object corresponding to the supplied name.
   */
  loadScenario(scenario: string): Scenario {
    if ( scenario === SCENARIO_LANDUFF.getScenarioName() ) {
      return SCENARIO_LANDUFF;
    } else if ( scenario === SCENARIO_LONGTS.getScenarioName()) {
      return SCENARIO_LONGTS;
    } else if ( scenario === SCENARIO_MDORF.getScenarioName() ) {
      return SCENARIO_MDORF;
    } else {
      return null;
    }
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
