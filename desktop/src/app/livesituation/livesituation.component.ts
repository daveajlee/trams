import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {Route} from "../routes/route.model";
import {PositionHelper} from "../shared/position.helper";
import {ServerService} from "../shared/server.service";
import {TimeHelper} from "../shared/time.helper";

@Component({
  selector: 'app-livesituation',
  templateUrl: './livesituation.component.html',
  styleUrls: ['./livesituation.component.css'],
})
export class LivesituationComponent implements OnInit {

  selectedRoute: string;
  currentDate: string;
  balance: string;
  passengerSatisfaction: number;
  routes: Route[];
  tours: string[];
  private simulationRunning: boolean;
  private interval: any;
  positions: Map<string, string>;

  /**
   * Create a new live situation component to display the current live situation to the user.
   * @param gameService the game service containing the currently loaded game.
   * @param serverService the service managing the http calls.
   * @param router the router object to navigate to other screens where necessary.
   */
  constructor(private gameService: GameService, private serverService: ServerService, public router: Router) {
    if ( this.gameService.isOfflineMode() ) {
      this.selectedRoute = this.gameService.getGame().getRoutes()[0].getRouteNumber();
      this.currentDate = this.gameService.getGame().getCurrentDateTime().toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
      this.balance = '' + this.gameService.getGame().getBalance();
      this.passengerSatisfaction = this.gameService.getGame().getPassengerSatisfaction();
      this.routes = this.gameService.getGame().getRoutes();
      for ( let i = 0; i < this.routes.length; i++ ) {
        if ( this.selectedRoute == this.routes[i].getRouteNumber() ) {
          this.tours = [];
          for ( let j = 0; j < this.routes[i].getSchedules().length; j++ ) {
            this.tours.push(this.routes[i].getSchedules()[j].getRouteNumberAndScheduleId())
          }
        }
      }
      this.positions = new Map<string, string>();
      for ( let j = 0; j < this.tours.length; j++ ) {
        this.getCurrentPosition(this.tours[j]);
      }
    } else {
      this.serverService.getRoutes().then((routes) => {
        this.routes = [];
        for ( let i = 0; i < routes.count; i++ ) {
          this.routes.push(new Route(routes.routeResponses[i].routeNumber, routes.routeResponses[i].startStop,
              routes.routeResponses[i].endStop, routes.routeResponses[i].stops, routes.routeResponses[i].company))
        }
        this.selectedRoute = this.routes[0].getRouteNumber();
        // Retrieve time, balance and passenger satisfaction.
        this.serverService.getCurrentDateTime().then((date) => {
          this.currentDate = TimeHelper.formatStringAsDateObject(date).toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
          // Retrieve balance and passenger satisfaction.
          this.serverService.getBalance().then((balance) => {
            this.balance = '' + balance;
            this.serverService.getPassengerSatisfaction().then((passengerSatisfaction) => {
              this.passengerSatisfaction = passengerSatisfaction;
            })
          })
          // Retrieve the schedules or tours which can be done in parallel.
          this.serverService.getStopTimes(this.selectedRoute, date.split(" ")[0], this.routes[0].getStartStop(), "").then((stopTimes) => {
            this.tours = [];
            for ( let i = 0; i < stopTimes.count; i++ ) {
              if ( !this.tours.includes(this.selectedRoute + "/" + stopTimes.stopTimeResponses[i].scheduleNumber) ) {
                this.tours.push(this.selectedRoute + "/" + stopTimes.stopTimeResponses[i].scheduleNumber);
              }
            }
            this.positions = new Map<string, string>();
            for ( let j = 0; j < this.tours.length; j++ ) {
              this.getCurrentPosition(this.tours[j]);
            }
          })
        })

      })
    }
    this.simulationRunning = false;
  }

  /**
   * Initialise a new allocations component which maintains a list of allocations.
   */
  ngOnInit(): void {
  }

  /**
   * Retrieve the stops that are served by the currently selected route.
   * @return the list of stop names as a String array.
   */
  getSelectedRouteStops(): String[] {
    if ( this.routes ) {
      for ( let i = 0; i < this.routes.length; i++ ) {
        if ( this.selectedRoute == this.routes[i].getRouteNumber() ) {
          let stops = [];
          stops.push(this.routes[i].getStartStop());
          stops = stops.concat(this.routes[i].getStops())
          stops.push(this.routes[i].getEndStop());
          return stops;
        }
      }
    }
  }

  /**
   * Return a boolean which is true if and only if the simulation is currently running.
   * @return a boolean which is true if and only if the simulation is running.
   */
  isSimulationRunning(): boolean {
    return this.simulationRunning;
  }

  /**
   * Get the current position for the supplied route schedule id.
   * @param routeTour the route schedule id that we want to retrieve the position for.
   */
  getCurrentPosition(routeTour: string): void {
    if ( this.gameService.isOfflineMode() ) {
      this.positions.set(routeTour, PositionHelper.getCurrentPosition(routeTour, this.routes, this.gameService.getGame()).getStop());
    } else {
      this.serverService.getCurrentDateTime().then((dateTime) => {
        this.serverService.getPosition(routeTour, dateTime, 'EASY').then((position) => {
          if ( position && position.stop ) {
            console.log('Position for ' + routeTour + ' is ' + position.stop);
            this.positions.set(routeTour, position.stop);
          } else {
            console.log('Position for ' + routeTour + ' is not allocated');
            this.positions.set(routeTour, "");
          }
        })
      })
    }
  }

  /**
   * Set whether or not the simulation is running. If so, create a javascript interval to run as the simulation.
   * If not, then delete the javascript interval.
   * @param value a boolean which is true iff the simulation should be running.
   */
  setSimulationRunning(value: boolean) {
    this.simulationRunning = value;
    if ( value === true ) {
      this.interval = setInterval(() => {
        if ( this.gameService.isOfflineMode() ) {
          this.gameService.getGame().updateSimulationStep();
        } else {
          this.serverService.getSimulationInterval().then((simulationInterval) => {
            this.serverService.increaseTimeInMinutes().then(() => {
              this.serverService.getCurrentDateTime().then((date) => {
                this.currentDate = TimeHelper.formatStringAsDateObject(date).toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
                // Check if we went past midnight. If so, then we need to pay drivers again.
                let hours = parseInt(date.split(" ")[1].split(":")[0]);
                let minutes = parseInt(date.split(" ")[1].split(":")[1]);
                if ( ((hours * 60) + minutes) <= simulationInterval ) {
                  // Reset all service information.
                  this.serverService.resetServices().then(() => {
                    // Get the number of drivers we have.
                    this.serverService.getDrivers().then((drivers) => {
                      // Now we need to pay drivers.
                      this.serverService.adjustBalance(-(drivers.count * 90)).then(() => {
                        this.serverService.getBalance().then((balance) => {
                          this.balance = '' + balance;
                        })
                      });
                    })
                  })
                }
                // Generate a random delay for each vehicle that is running a schedule.
                this.serverService.getVehicles().then((vehicles) => {
                  for ( let i = 0; i < vehicles.vehicleResponses.length; i++ ) {
                    if ( vehicles.vehicleResponses[i].allocatedTour && vehicles.vehicleResponses[i].allocatedTour != "") {
                      // With probability 40% decrease delay, 40% increase delay and 20% no change to delay.
                      let randomVal = Math.random() * (100);
                      if ( randomVal < 40 ) {
                        //Decrease delay.
                        this.serverService.adjustDelay(vehicles.vehicleResponses[i].fleetNumber, Math.round(-Math.random() * 5));
                      } else if ( randomVal < 80 ) {
                        // Increase delay.
                        this.serverService.adjustDelay(vehicles.vehicleResponses[i].fleetNumber, Math.round(Math.random() * 5));
                      } else {
                        // Do nothing as delay stays the same.
                      }
                    }
                  }
                })
              })
            });
          })
          // Decrease or increase the passenger satisfaction by a maximum of 2 in either plus or minus direction,
          let randomDiff = Math.random() * (4);
          this.serverService.adjustPassengerSatisfaction(Math.round((randomDiff-2))).then((satisfactionRateResponse) => {
            this.passengerSatisfaction = satisfactionRateResponse.satisfactionRate;
          });
          // Get the next positions.
          this.positions = new Map<string, string>();
          for ( let j = 0; j < this.tours.length; j++ ) {
            this.getCurrentPosition(this.tours[j]);
          }
        }
        }, 10000);
    } else {
      if (this.interval) {
        clearInterval(this.interval);
      }
    }
  }

  /**
   * Clicking on the button with the route schedule id causes the schedule information screen to be opened.
   * @param routeScheduleId the route schedule id to open the schedule information screen with.
   */
  moveToScheduleScreen(routeScheduleId: string) {
    this.setSimulationRunning(false);
    this.router.navigate(['scheduleinfo', routeScheduleId.replace("/", ":")]);
  }

  /**
   * Return to the management screen by clicking the back button.
   */
  backToManagementScreen(): void {
    this.setSimulationRunning(false);
    this.router.navigate(['management']);
  }

}
