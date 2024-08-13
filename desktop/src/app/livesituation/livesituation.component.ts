import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {Route} from "../routes/route.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";
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

  getCurrentPosition(routeTour: string): string {
    if ( this.gameService.isOfflineMode() ) {
      return PositionHelper.getCurrentPosition(routeTour, this.routes, this.gameService.getGame()).getStop();
    } else {
      return "";
    }

  }

  setSimulationRunning(value: boolean) {
    this.simulationRunning = value;
    if ( value === true ) {
      this.interval = setInterval(() => {
        this.gameService.getGame().updateSimulationStep();
        }, 10000);
    } else {
      if (this.interval) {
        clearInterval(this.interval);
      }
    }
  }

  moveToScheduleScreen(routeScheduleId: string) {
    this.setSimulationRunning(false);
    this.router.navigate(['scheduleinfo', routeScheduleId.replace("/", ":")]);
  }

  backToManagementScreen(): void {
    this.setSimulationRunning(false);
    this.router.navigate(['management']);
  }

}
