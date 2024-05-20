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

  gameService: GameService;
  selectedRoute: String;
  simulationRunning: boolean;
  interval: any;

  display = "none";

  constructor(private gameService2: GameService, public router: Router) {
    this.gameService = gameService2;
    this.selectedRoute = this.getRoutes()[0].routeNumber;
    this.simulationRunning = false;
  }

  ngOnInit(): void {
  }

  getCurrentDate(): string {
    return this.gameService.getGame().currentDateTime.toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
  }

  getBalance(): string {
    return '' + this.gameService.getGame().balance;
  }

  getPassengerSatisfaction(): number {
    return this.gameService.getGame().passengerSatisfaction;
  }

  getRoutes(): Route[] {
    return this.gameService.getGame().routes;
  }

  getSelectedRouteStops(): String[] {
    for ( let i = 0; i < this.getRoutes().length; i++ ) {
      if ( this.selectedRoute == this.getRoutes()[i].routeNumber ) {
        var stops = [];
        stops.push(this.getRoutes()[i].startStop);
        stops = stops.concat(this.getRoutes()[i].stops)
        stops.push(this.getRoutes()[i].endStop);
        return stops;
      }
    }
  }

  getRouteScheds(): ScheduleModel[] {
    for ( let i = 0; i < this.getRoutes().length; i++ ) {
      if ( this.selectedRoute == this.getRoutes()[i].routeNumber ) {
        return this.getRoutes()[i].schedules;
      }
    }
  }

  getCurrentPosition(schedule: ScheduleModel): string {
    var currentDateTime = this.gameService.getGame().currentDateTime;
    var currentTime = TimeHelper.formatTimeAsString(currentDateTime);
    for ( let i = 0; i < schedule.services.length; i++ ) {
      for ( let j = 0; j < schedule.services[i].stopList.length; j++ ) {
        // If we exactly match the departure time then this is where we are.
        if ( schedule.services[i].stopList[j].departureTime === currentTime ) {
          return schedule.services[i].stopList[j].stop;
        }
        // If we are before departure time but after arrival time then we are also here.
        else if ( schedule.services[i].stopList[j].departureTime > currentTime
            && schedule.services[i].stopList[j].arrivalTime <= currentTime) {
          return schedule.services[i].stopList[j].stop;
        }
        // If we are before both departure time and arrival time then we are at the previous stop if there was one.
        else if ( schedule.services[i].stopList[j].departureTime > currentTime
            && schedule.services[i].stopList[j].arrivalTime > currentTime
            && schedule.services[0].stopList[0].arrivalTime < currentTime // Ensure service has started
            && j != 0 ) {
          return schedule.services[i].stopList[j-1].stop;
        }
        // If there was not a previous stop then it is the previous service last stop where we are at if there was one.
        else if ( schedule.services[i].stopList[j].departureTime > currentTime
            && schedule.services[i].stopList[j].arrivalTime > currentTime
            && schedule.services[0].stopList[0].arrivalTime < currentTime // Ensure service has started
            && j === 0
            && i != 0 ) {
          return schedule.services[i-1].stopList[schedule.services[i-1].stopList.length-1].stop;
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
        // Increase the time by 15 minutes,
        this.gameService.getGame().currentDateTime = new Date(this.gameService.getGame().currentDateTime.getTime() + 100*60000);
        // Decrease or increase the passenger satisfaction by a maximum of 2 in either plus or minus direction,
        var randomDiff = Math.random() * (4);
        this.gameService.getGame().passengerSatisfaction = Math.round(this.gameService.getGame().passengerSatisfaction + (randomDiff-2));
        // Ensure that passenger satisfaction is between 0 and 100.
        if ( this.gameService.getGame().passengerSatisfaction < 0 ) {
          this.gameService.getGame().passengerSatisfaction = 0;
        } else if ( this.gameService.getGame().passengerSatisfaction > 100 ) {
          this.gameService.getGame().passengerSatisfaction = 100;
        }
        }, 10000);
    } else {
      if (this.interval) {
        clearInterval(this.interval);
      }
    }
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
