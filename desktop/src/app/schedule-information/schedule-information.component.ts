import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from "rxjs";
import {GameService} from "../shared/game.service";
import {PositionHelper} from "../shared/position.helper";
import {ServiceModel} from "../stops/stop-detail/service.model";
import {RoutesService} from "../routes/routes.service";

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
  private currentService: ServiceModel;
  private nextService: ServiceModel;

  private messages: string[];

  constructor(private gameService: GameService, private route: ActivatedRoute, public router: Router, private routeService: RoutesService ) {
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
          let positionModel = PositionHelper.getCurrentPosition(schedules[i], this.gameService.getGame());
          this.stop = positionModel.getStop();
          this.destination = positionModel.getDestination();
          this.delay = positionModel.getDelay();
          let services = PositionHelper.getCurrentAndNextService(schedules[i], this.gameService.getGame() );
          if ( services.length > 0 ) {
            this.currentService = services[0];
          }
          if ( services.length > 1 ) {
            this.nextService = services[1];
          }

        }
      }

      // Retrieve the vehicle assigned to this schedule id.
      this.fleetNumber = this.gameService.getGame().getAssignedVehicle(this.selectedRoute + "/" + this.scheduleId);

      // Set messages to an empty array.
      this.messages = [];
    });
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
    this.currentService.setTempEndStop(this.stop);
    // Also shorten the next service if it exists.
    this.nextService.setTempStartStop(this.stop);
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
    this.currentService.setServiceToOutOfService();
    // Reduce the delay by 10% of the duration.
    this.gameService.getGame().getVehicleByFleetNumber(this.fleetNumber).adjustDelay(-(0.1 * this.routeService.getDuration(this.gameService.getGame().getScenario(), this.gameService.getGame().getRoute(this.selectedRoute).getStartStop(), this.gameService.getGame().getRoute(this.selectedRoute).getStops(), this.gameService.getGame().getRoute(this.selectedRoute).getEndStop())));
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

  backToSimulationScreen(): void {
    this.router.navigate(['livesituation']);
  }

}
