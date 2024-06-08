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

  public name: string;
  public contractedHours: number;
  public startingDate: string;

  /**
   * Construct a new Driver Creator component
   * @param gameService the game service containing the currently loaded game.
   * @param router the router for navigating to other pages.
   * @param datePipe a date pipe object for transforming dates in Angular.
   */
  constructor(private gameService: GameService, public router: Router, private datePipe: DatePipe) {
    this.contractedHours = 40;
    this.startingDate = this.datePipe.transform(this.gameService.getGame().getCurrentDateTime(), 'yyyy-MM-dd');
  }

  /**
   * Retrieve the list of possible contracted hours that are available.
   */
  getPossibleContractedHours(): number[] {
    return [20,25,30,35,40];
  }

  onSubmitDriver(): void {
    var driver = new Driver(this.name, this.contractedHours, this.startingDate);
    // Add the driver.
    this.gameService.getGame().addDriver(driver);
    // Deduct the hiring costs of 500.
    this.gameService.getGame().withdrawBalance(500);
    // Redirect to management.
    this.router.navigate(['management']);
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }


}
