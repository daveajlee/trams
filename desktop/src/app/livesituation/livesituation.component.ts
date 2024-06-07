import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {Route} from "../routes/route.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";
import {TimeHelper} from "../shared/time.helper";

@Component({
  selector: 'app-livesituation',
  templateUrl: './livesituation.component.html',
  styleUrls: ['./livesituation.component.css'],
})
export class LivesituationComponent implements OnInit {

  selectedRoute: String;
  private simulationRunning: boolean;
  private interval: any;

  constructor(private gameService: GameService, public router: Router) {
    this.selectedRoute = this.getRoutes()[0].getRouteNumber();
    this.simulationRunning = false;
  }

  ngOnInit(): void {
  }

  getCurrentDate(): string {
    return this.gameService.getGame().getCurrentDateTime().toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
  }

  getBalance(): string {
    return '' + this.gameService.getGame().getBalance();
  }

  getPassengerSatisfaction(): number {
    return this.gameService.getGame().getPassengerSatisfaction();
  }

  getRoutes(): Route[] {
    return this.gameService.getGame().getRoutes();
  }

  getSelectedRouteStops(): String[] {
    for ( let i = 0; i < this.getRoutes().length; i++ ) {
      if ( this.selectedRoute == this.getRoutes()[i].getRouteNumber() ) {
        var stops = [];
        stops.push(this.getRoutes()[i].getStartStop());
        stops = stops.concat(this.getRoutes()[i].getStops())
        stops.push(this.getRoutes()[i].getEndStop());
        return stops;
      }
    }
  }

  getRouteScheds(): ScheduleModel[] {
    for ( let i = 0; i < this.getRoutes().length; i++ ) {
      if ( this.selectedRoute == this.getRoutes()[i].getRouteNumber() ) {
        return this.getRoutes()[i].getSchedules();
      }
    }
  }

  getCurrentPosition(schedule: ScheduleModel): string {
    // If a schedule is not assigned then it cannot be shown in the live situation and it's position is depot.
    if ( this.gameService.getGame().retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()) === -1 ) {
      return "Depot";
    }
    var currentDateTime = this.gameService.getGame().getCurrentDateTime();
    let currentTime = TimeHelper.formatTimeAsString(currentDateTime);
    let assignedFleetId = this.gameService.getGame().getAssignedVehicle(schedule.getRouteNumberAndScheduleId());
    let delay = this.gameService.getGame().getVehicleByFleetNumber(assignedFleetId).getDelay();
    // Decrease time.
    currentTime = TimeHelper.subtractTime(currentTime, delay);
    // Now find relevant service and position.
    let services = schedule.getServices();
    for ( let i = 0; i < services.length; i++ ) {
      for ( let j = 0; j < services[i].getStopList().length; j++ ) {
        // If we exactly match the departure time then this is where we are.
        if ( services[i].getStopList()[j].getDepartureTime() === currentTime ) {
          return services[i].getStopList()[j].getStop();
        }
        // If we are before departure time but after arrival time then we are also here.
        else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
            && services[i].getStopList()[j].getArrivalTime() <= currentTime) {
          return services[i].getStopList()[j].getStop();
        }
        // If we are before both departure time and arrival time then we are at the previous stop if there was one.
        else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
            && services[i].getStopList()[j].getArrivalTime() > currentTime
            && services[0].getStopList()[0].getArrivalTime() < currentTime // Ensure service has started
            && j != 0 ) {
          return services[i].getStopList()[j-1].getStop();
        }
        // If there was not a previous stop then it is the previous service last stop where we are at if there was one.
        else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
            && services[i].getStopList()[j].getArrivalTime() > currentTime
            && services[0].getStopList()[0].getArrivalTime() < currentTime // Ensure service has started
            && j === 0
            && i != 0 ) {
          return services[i-1].getStopList()[services[i-1].getStopList().length-1].getStop();
        }

      }
    }
    // If we still did not match, then we must be at the depot.
    return "Depot";
  }

  isSimulationRunning() {
    return this.simulationRunning;
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
    this.router.navigate(['scheduleinfo', routeScheduleId]);
  }

  backToManagementScreen(): void {
    this.setSimulationRunning(false);
    this.router.navigate(['management']);
  }

}
