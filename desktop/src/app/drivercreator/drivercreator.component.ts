import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Driver} from "../drivers/driver.model";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-drivercreator',
  templateUrl: './drivercreator.component.html',
  styleUrls: ['./drivercreator.component.css']
})
export class DrivercreatorComponent {

  name: string;
  contractedHours: number;
  startingDate: string;
  gameService: GameService

  /**
   * Construct a new Driver Creator component
   * @param gameService2 the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   * @param datePipe a date pipe object for transforming dates in Angular.
   */
  constructor(private gameService2: GameService, public router: Router, private datePipe: DatePipe) {
    this.gameService = gameService2;
    this.contractedHours = 40;
    this.startingDate = this.datePipe.transform(this.gameService.getGame().currentDateTime, 'yyyy-MM-dd');
  }

  /**
   * Retrieve the list of possible contracted hours that are available.
   */
  getPossibleContractedHours(): number[] {
    return [20,25,30,35,40];
  }

  onSubmitDriver(): void {
    var driver = new Driver(this.name, this.contractedHours, this.startingDate);
    this.gameService.getGame().addDriver(driver);
    this.router.navigate(['management']);
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }


}
