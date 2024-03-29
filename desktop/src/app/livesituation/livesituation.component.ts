import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {Route} from "../routes/route.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";

@Component({
  selector: 'app-livesituation',
  templateUrl: './livesituation.component.html',
  styleUrls: ['./livesituation.component.css']
})
export class LivesituationComponent implements OnInit {

  currentDate: Date;
  gameService: GameService;
  selectedRoute: String;

  constructor(private gameService2: GameService, public router: Router) {
    this.currentDate = new Date();
    this.gameService = gameService2;
    this.selectedRoute = this.getRoutes()[0].routeNumber;
  }

  ngOnInit(): void {
  }

  getCurrentDate(): string {
    return this.currentDate.toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
  }

  getBalance(): string {
    return '' + this.gameService.getGame().balance;
  }

  getRoutes(): Route[] {
    return this.gameService.getGame().routes;
  }

  getSelectedRouteStops(): String[] {
    for ( let i = 0; i < this.getRoutes().length; i++ ) {
      if ( this.selectedRoute == this.getRoutes()[i].routeNumber ) {
        return this.getRoutes()[i].stops;
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
    var currentTime = this.currentDate.getHours() + ":" + this.currentDate.getMinutes();
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
            && j != 0 ) {
          return schedule.services[i].stopList[j-1].stop;
        }
        // If there was not a previous stop then it is the previous service last stop where we are at if there was one.
        else if ( schedule.services[i].stopList[j].departureTime > currentTime
            && schedule.services[i].stopList[j].arrivalTime > currentTime
            && j === 0
            && i != 0 ) {
          return schedule.services[i-1].stopList[schedule.services[i-1].stopList.length-1].stop;
        }

      }
    }
    // If we still did not match, then we must be at the depot.
    return "Depot";
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
