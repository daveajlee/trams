import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from "rxjs";
import {GameService} from "../shared/game.service";
import {PositionHelper} from "../shared/position.helper";
import {ServiceModel} from "../stops/stop-detail/service.model";
import {RoutesService} from "../routes/routes.service";
import {ServerService} from "../shared/server.service";
import {ServiceTripResponse} from "./servicetrip.response";
import {Scenario} from "../shared/scenario.model";
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";

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
  private serviceTrip: ServiceTripResponse;

  private messages: string[];

  /**
   * Create a new schedule information screen to display the schedule details to the user.
   * @param gameService the game service containing the currently loaded game
   * @param route the current activated route containing the params with the route schedule id e.g. 3/1.
   * @param router the router object to navigate to other screens where necessary.
   * @param routeService the route service containing details of the routes.
   * @param serverService the service managing the http calls.
   */
  constructor(private gameService: GameService, private route: ActivatedRoute, public router: Router, private routeService: RoutesService,
              private serverService: ServerService ) {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.id = params['routeScheduleId'];
      console.log('Param is: ' + this.id);
      this.selectedRoute = this.id.split(":")[0];
      this.scheduleId = this.id.split(":")[1];

      // If offline mode
      if ( this.gameService.isOfflineMode() ) {
        // Get the route and schedule objects.
        let route = this.gameService.getGame().getRoute(this.selectedRoute);
        let schedules = route.getSchedules();
        for ( let i = 0; i < schedules.length; i++ ) {
          if ( schedules[i].getRouteNumberAndScheduleId().split("/")[1] === this.scheduleId ) {
            // Retrieve the stop and the destination.
            let positionModel = PositionHelper.getCurrentPosition(schedules[i].getRouteNumberAndScheduleId(),
                this.gameService.getGame().getRoutes(), this.gameService.getGame());
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
      } else {
        // Get the route and schedule objects.
        this.serverService.getRoute(this.selectedRoute).then((routeResponse) => {
          this.serverService.getCurrentDateTime().then((date) => {
            // Retrieve the schedules or tours which can be done in parallel.
            this.serverService.getStopTimes(this.selectedRoute, date.split(" ")[0], routeResponse.startStop, "").then((stopTimes) => {
               let tours = [];
               for ( let i = 0; i < stopTimes.count; i++ ) {
                if ( !tours.includes(this.selectedRoute + "/" + stopTimes.stopTimeResponses[i].scheduleNumber) ) {
                  tours.push(this.selectedRoute + "/" + stopTimes.stopTimeResponses[i].scheduleNumber);
                }
               }
               for ( let j = 0; j < tours.length; j++ ) {
                 if ( tours[j] === (this.selectedRoute + "/" + this.scheduleId) ) {
                   this.getCurrentPosition(tours[j]);
                 }
               }
            })
          });
        });
        /*let schedules = route.getSchedules();
        for ( let i = 0; i < schedules.length; i++ ) {
            let services = PositionHelper.getCurrentAndNextService(schedules[i], this.gameService.getGame() );
            if ( services.length > 0 ) {
              this.currentService = services[0];
            }
            if ( services.length > 1 ) {
              this.nextService = services[1];
            }

          }
        }*/

        // Retrieve the vehicle assigned to this schedule id.
        this.serverService.getVehicles().then((vehicles) => {
          for ( let i = 0; i < vehicles.count; i++ ) {
            if ( vehicles.vehicleResponses[i].allocatedTour === this.selectedRoute + "/" + this.scheduleId) {
              this.fleetNumber = vehicles.vehicleResponses[i].fleetNumber;
            }
          }
        })
        // Set messages to an empty array.
        this.messages = [];
      }
    });
  }

  /**
   * Initialise anything outside of the constructor.
   */
  ngOnInit(): void {
  }

  /**
   * Get the current position for the supplied route schedule id.
   * @param routeTour the route schedule id that we want to retrieve the position for.
   */
  getCurrentPosition(routeTour: string): void {
    console.log('Attempting to get position for ' + routeTour);
      this.serverService.getCurrentDateTime().then((dateTime) => {
        this.serverService.getPosition(routeTour, dateTime, 'EASY').then((position) => {
          if ( position && position.stop ) {
            console.log('Position for ' + routeTour + ' is ' + position.stop);
            this.stop = position.stop;
            this.destination = position.destination;
            this.delay = position.delay;
            this.serviceTrip = position.service;
            // Check out of service and shorten info and display if appropriate.
            if ( this.serviceTrip.outOfService ) {
              this.destination = "Out Of Service";
            } else if ( this.serviceTrip.tempEndStopPos > 0 ) {
              this.destination = this.serviceTrip.stopList[this.serviceTrip.tempEndStopPos];
            }
          } else {
            console.log('Position for ' + routeTour + ' is not allocated');
            this.stop = "";
            this.destination = "";
            this.delay = 0;
            this.serviceTrip = null;
          }
        })
      })
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
    if ( this.gameService.isOfflineMode() ) {
      // This is where we actually shorten the service.
      this.currentService.setTempEndStop(this.stop);
      // Also shorten the next service if it exists.
      this.nextService.setTempStartStop(this.stop);
      // Decrease the passenger satisfaction by 5%.
      this.gameService.getGame().adjustPassengerSatisfaction(-5);
    } else {
      // This is where we actually shorten the service and shorten the next service if it exists.
      this.serverService.shortenService(this.serviceTrip, this.stop).then(() => {
        // Decrease the passenger satisfaction by 5%.
        this.serverService.adjustPassengerSatisfaction(-5);
      });
    }

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
    if ( this.gameService.isOfflineMode() ) {
      // This is where we actually put the vehicle out of service.
      this.currentService.setServiceToOutOfService();
      // Reduce the delay by 10% of the duration.
      this.gameService.getGame().getVehicleByFleetNumber(this.fleetNumber).adjustDelay(-(0.1 * this.routeService.getDuration(this.gameService.getGame().getScenario(), this.gameService.getGame().getRoute(this.selectedRoute).getStartStop(), this.gameService.getGame().getRoute(this.selectedRoute).getStops(), this.gameService.getGame().getRoute(this.selectedRoute).getEndStop())));
      // Decrease the passenger satisfaction by 3%.
      this.gameService.getGame().adjustPassengerSatisfaction(-3);
    } else {
      // Get the route object so that we can calculate distance.
      this.serverService.getRoute(this.selectedRoute).then((routeResponse) => {
        // Get the scenario we are running.
        this.serverService.getScenarioName().then((scenarioName) => {
          // This is where we actually put the vehicle out of service.
          this.serverService.outOfService(this.serviceTrip).then(() => {
            // Reduce the delay by 10% of the duration.
            this.serverService.adjustDelay(this.fleetNumber, -(0.1 * this.routeService.getDuration(this.loadScenario(scenarioName), routeResponse.startStop, routeResponse.stops, routeResponse.endStop))).then(() => {
              // Decrease the passenger satisfaction by 3%.
              this.serverService.adjustPassengerSatisfaction(-3);
            });
          });
        });
      });
    }
    // Now we print messages saying that the vehicle is being put out of service.
    this.messages.push("Control: Vehicle " + this.fleetNumber + ", please go out of service until " + this.destination + ". Over!")
    this.messages.push("Vehicle " + this.fleetNumber + ": Message acknowledged. Thanks! Over!")
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
