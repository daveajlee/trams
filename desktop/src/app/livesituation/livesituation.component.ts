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

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
