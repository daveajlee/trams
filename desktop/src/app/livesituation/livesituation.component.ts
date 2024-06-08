import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../shared/game.service";
import {Route} from "../routes/route.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";
import {PositionHelper} from "../shared/position.helper";

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

  isSimulationRunning() {
    return this.simulationRunning;
  }

  getCurrentPosition(sched: ScheduleModel): string {
    return PositionHelper.getCurrentPosition(sched, this.gameService.getGame()).getStop();
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
