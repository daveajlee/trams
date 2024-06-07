import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Subscription} from "rxjs";
import {GameService} from "../shared/game.service";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";
import {TimeHelper} from "../shared/time.helper";
import {PositionModel} from "../livesituation/position.model";

@Component({
  selector: 'app-schedule-information',
  templateUrl: './schedule-information.component.html',
  styleUrls: ['./schedule-information.component.css']
})
export class ScheduleInformationComponent implements OnInit, OnDestroy {

  private idSubscription: Subscription;
  private id: string;

  private selectedRoute: string;
  private scheduleId: string;
  private fleetNumber: string;
  private stop: string;
  private destination: string;
  private delay: number;

  private messages: string[];

  constructor(private gameService: GameService, private route: ActivatedRoute ) {
  }

  /**
   * Initialise the stop information during construction and ensure all variables are set to the correct data.
   */
  ngOnInit(): void {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.id = params['routeScheduleId'];
      console.log('Param is: ' + this.id);
      this.selectedRoute = this.id.split(":")[0];
      this.scheduleId = this.id.split(":")[1];

      // Get the route and schedule objects.
      let route = this.gameService.getGame().getRoute(this.selectedRoute);
      let schedules = route.getSchedules();
      for ( let i = 0; i < schedules.length; i++ ) {
        if ( schedules[i].getRouteNumberAndScheduleId().split("/")[1] === this.scheduleId ) {
          // Retrieve the stop and the destination.
          let positionModel = this.getCurrentPosition(schedules[i]);
          this.stop = positionModel.getStop();
          this.destination = positionModel.getDestination();
          this.delay = positionModel.getDelay();
        }
      }

      // Retrieve the vehicle assigned to this schedule id.
      this.fleetNumber = this.gameService.getGame().getAssignedVehicle(this.selectedRoute + "/" + this.scheduleId);

      // Set messages to an empty array.
      this.messages = [];
    });
  }

  /**
   * Get the current position of the specified schedule.
   * @param schedule the Schedule Model object that we want to retrieve the position for.
   * @return the position as a PositionModel object.
   */
  getCurrentPosition(schedule: ScheduleModel): PositionModel {
    // If a schedule is not assigned then it cannot be shown in the live situation and it's position is depot.
    if ( this.gameService.getGame().retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()) === -1 ) {
      return new PositionModel("Depot", "", 0);
    }
    // Otherwise get position based on time.
    var currentDateTime = this.gameService.getGame().getCurrentDateTime();
    var currentTime = TimeHelper.formatTimeAsString(currentDateTime);
    for ( let i = 0; i < schedule.getServices().length; i++ ) {
      for ( let j = 0; j < schedule.getServices()[i].getStopList().length; j++ ) {
        // If we exactly match the departure time then this is where we are.
        if ( schedule.getServices()[i].getStopList()[j].getDepartureTime() === currentTime ) {
          return new PositionModel(schedule.getServices()[i].getStopList()[j].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), this.gameService.getGame().retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
        }
        // If we are before departure time but after arrival time then we are also here.
        else if ( schedule.getServices()[i].getStopList()[j].getDepartureTime() > currentTime
            && schedule.getServices()[i].getStopList()[j].getArrivalTime() <= currentTime) {
          return new PositionModel(schedule.getServices()[i].getStopList()[j].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), this.gameService.getGame().retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
        }
        // If we are before both departure time and arrival time then we are at the previous stop if there was one.
        else if ( schedule.getServices()[i].getStopList()[j].getDepartureTime() > currentTime
            && schedule.getServices()[i].getStopList()[j].getArrivalTime() > currentTime
            && schedule.getServices()[0].getStopList()[0].getArrivalTime() < currentTime // Ensure service has started
            && j != 0 ) {
          return new PositionModel(schedule.getServices()[i].getStopList()[j-1].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), this.gameService.getGame().retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
        }
        // If there was not a previous stop then it is the previous service last stop where we are at if there was one.
        else if ( schedule.getServices()[i].getStopList()[j].getDepartureTime() > currentTime
            && schedule.getServices()[i].getStopList()[j].getArrivalTime() > currentTime
            && schedule.getServices()[0].getStopList()[0].getArrivalTime() < currentTime // Ensure service has started
            && j === 0
            && i != 0 ) {
          return new PositionModel(schedule.getServices()[i-1].getStopList()[schedule.getServices()[i-1].getStopList().length-1].getStop(), schedule.getServices()[i-1].getStopList()[schedule.getServices()[i-1].getStopList().length-1].getStop(), this.gameService.getGame().retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
        }

      }
    }
    // If we still did not match, then we must be at the depot.
    return new PositionModel("Depot", "", 0);
  }

  /**
   * Get the route that the user has currently selected.
   * @return the selected route as a String.
   */
  getSelectedRoute(): string {
    return this.selectedRoute;
  }

  /**
   * Get the schedule id that the user clicked on.
   * @return the schedule id as a String.
   */
  getScheduleId() {
    return this.scheduleId;
  }

  /**
   * Get the fleet number of the vehicle running this schedule at the moment.
   * @return the fleet number as a String.
   */
  getFleetNumber(): string {
    return this.fleetNumber;
  }

  /**
   * Get the stop that the vehicle is currently at.
   * @return the stop as a String.
   */
  getStop(): string {
    return this.stop;
  }

  /**
   * Get the destination that the vehicle is running towards.
   * @return the destination as a String.
   */
  getDestination(): string {
    return this.destination;
  }

  /**
   * Get the delay in minutes that the vehicle currently has.
   * @return the delay in minutes as a number.
   */
  getDelay(): number {
    return this.delay;
  }

  /**
   * Clicking on contact vehicle should display a text.
   */
  contactVehicle(): void {
    // Print messages where the vehicle currently is.
    this.messages.push("Control: Vehicle " + this.fleetNumber + ", please state your current position. Over!");
    this.messages.push("Response: Vehicle " + this.fleetNumber + " At: " + this.stop + " heading towards " + this.destination + " with delay of " + this.delay + " mins. Over!")
  }

  /**
   * Clicking on shorten schedule should shorten the schedule so that the vehicle terminates
   * at the current stop. It then proceeds back at the appropriate time. Delay should be reduced
   * and the current and next service updated to reflect change.
   */
  shortenService(): void {
    // This is where we actually shorten the service.
    console.log('We still need to shorten the service in the simulation');
    // Decrease the passenger satisfaction by 5%.
    this.gameService.getGame().adjustPassengerSatisfaction(-5);
    // Now we print messages saving that the vehicle schedule is being shortened.
    this.messages.push("Vehicle " + this.fleetNumber + ", please terminate at " + this.stop + " and proceed in service in the reverse direction. Over!");
    this.messages.push("Response: Vehicle " + this.fleetNumber + ": Message acknowledged. Thanks! Over!");
  }

  /**
   * Clicking on out of service should put the vehicle out of service for the rest of this service.
   * With the start of the next service, the vehicle is back in service. Delay should be reduced and the
   * current service updated to reflect change.
   */
  outOfService(): void {
    // This is where we actually put the vehicle out of service.
    console.log('We still need to put the vehicle out of service in the simulation');
    // Reduce the delay by 10% of the duration.
    this.gameService.getGame().getVehicleByFleetNumber(this.fleetNumber).adjustDelay(-(0.1 * this.gameService.getGame().getRoute(this.selectedRoute).getDuration(this.gameService.getGame().getScenario())));
    // Decrease the passenger satisfaction by 3%.
    this.gameService.getGame().adjustPassengerSatisfaction(-3);
    // Now we print messages saying that the vehicle is being put out of service.
    this.messages.push("Control: Vehicle " + this.fleetNumber + ", please go out of service until " + this.destination + ". Over!")
    this.messages.push("Vehicle " + this.fleetNumber + ": Message acknowledged. Thanks! Over!")
  }

  /**
   * Get messages exchanged between control and vehicle.
   * @return an array with the list of messages exchanged as a String array.
   */
  getMessages() {
    return this.messages;
  }

  /**
   * When destroying this component we should ensure that all subscriptions are cancelled.
   */
  ngOnDestroy(): void {
  }

}
