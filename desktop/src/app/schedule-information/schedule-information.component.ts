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

  idSubscription: Subscription;
  id: string;

  selectedRoute: string;
  scheduleId: string;
  vehicleId: string;
  stop: string;
  destination: string;
  delay: number;

  gameService: GameService;

  constructor(private gameService2: GameService, private route: ActivatedRoute ) {
    this.gameService = gameService2;
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
        if ( schedules[i].scheduleId === this.scheduleId ) {
          // Retrieve the stop and the destination.
          let positionModel = this.getCurrentPosition(schedules[i]);
          this.stop = positionModel.getStop();
          this.destination = positionModel.getDestination();
          this.delay = positionModel.getDelay();
        }
      }

      // Retrieve the vehicle assigned to this schedule id.
      this.vehicleId = this.gameService.getGame().getAssignedVehicle(this.selectedRoute + "/" + this.scheduleId);

    });
  }

  getCurrentPosition(schedule: ScheduleModel): PositionModel {
    // If a schedule is not assigned then it cannot be shown in the live situation and it's position is depot.
    if ( this.gameService.getGame().retrieveDelayForAssignedTour(schedule.routeNumber + "/" + schedule.scheduleId) === -1 ) {
      return new PositionModel("Depot", "", 0);
    }
    // Otherwise get position based on time.
    var currentDateTime = this.gameService.getGame().getCurrentDateTime();
    var currentTime = TimeHelper.formatTimeAsString(currentDateTime);
    for ( let i = 0; i < schedule.services.length; i++ ) {
      for ( let j = 0; j < schedule.services[i].stopList.length; j++ ) {
        // If we exactly match the departure time then this is where we are.
        if ( schedule.services[i].stopList[j].departureTime === currentTime ) {
          return new PositionModel(schedule.services[i].stopList[j].stop, schedule.services[i].stopList[schedule.services[i].stopList.length-1].stop, this.gameService.getGame().retrieveDelayForAssignedTour(schedule.routeNumber + "/" + schedule.scheduleId));
        }
        // If we are before departure time but after arrival time then we are also here.
        else if ( schedule.services[i].stopList[j].departureTime > currentTime
            && schedule.services[i].stopList[j].arrivalTime <= currentTime) {
          return new PositionModel(schedule.services[i].stopList[j].stop, schedule.services[i].stopList[schedule.services[i].stopList.length-1].stop, this.gameService.getGame().retrieveDelayForAssignedTour(schedule.routeNumber + "/" + schedule.scheduleId));
        }
        // If we are before both departure time and arrival time then we are at the previous stop if there was one.
        else if ( schedule.services[i].stopList[j].departureTime > currentTime
            && schedule.services[i].stopList[j].arrivalTime > currentTime
            && schedule.services[0].stopList[0].arrivalTime < currentTime // Ensure service has started
            && j != 0 ) {
          return new PositionModel(schedule.services[i].stopList[j-1].stop, schedule.services[i].stopList[schedule.services[i].stopList.length-1].stop, this.gameService.getGame().retrieveDelayForAssignedTour(schedule.routeNumber + "/" + schedule.scheduleId));
        }
        // If there was not a previous stop then it is the previous service last stop where we are at if there was one.
        else if ( schedule.services[i].stopList[j].departureTime > currentTime
            && schedule.services[i].stopList[j].arrivalTime > currentTime
            && schedule.services[0].stopList[0].arrivalTime < currentTime // Ensure service has started
            && j === 0
            && i != 0 ) {
          return new PositionModel(schedule.services[i-1].stopList[schedule.services[i-1].stopList.length-1].stop, schedule.services[i-1].stopList[schedule.services[i-1].stopList.length-1].stop, this.gameService.getGame().retrieveDelayForAssignedTour(schedule.routeNumber + "/" + schedule.scheduleId));
        }

      }
    }
    // If we still did not match, then we must be at the depot.
    return new PositionModel("Depot", "", 0);
  }

  /**
   * When destroying this component we should ensure that all subscriptions are cancelled.
   */
  ngOnDestroy(): void {
  }

}
