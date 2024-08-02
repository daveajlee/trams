import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Driver} from "../drivers/driver.model";
import {DatePipe} from "@angular/common";
import {TimeHelper} from "../shared/time.helper";
import {ServerService} from "../shared/server.service";
import {DriverRequest} from "../drivers/driver.request";

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
   * @param serverService the server service managing http calls.
   */
  constructor(private gameService: GameService, public router: Router, private datePipe: DatePipe, private serverService: ServerService) {
    this.contractedHours = 40;
    if ( this.gameService.isOfflineMode() ) {
      this.startingDate = this.datePipe.transform(this.gameService.getGame().getCurrentDateTime(), 'yyyy-MM-dd');
    } else {
      this.serverService.getCurrentDateTime().then((dateTime) => {
        this.startingDate = this.datePipe.transform(TimeHelper.formatStringAsDateObject(dateTime), 'yyyy-MM-dd');
      })
    }
  }

  /**
   * Retrieve the list of possible contracted hours that are available.
   */
  getPossibleContractedHours(): number[] {
    return [20,25,30,35,40];
  }

  onSubmitDriver(): void {
    var driver = new Driver(this.name, this.contractedHours, this.startingDate);
    if ( this.gameService.isOfflineMode()) {
      // Add the driver.
      this.gameService.getGame().addDriver(driver);
      // Deduct the hiring costs of 500.
      this.gameService.getGame().withdrawBalance(500);
      // Redirect to management.
      this.router.navigate(['management']);
    } else {
      let dateParts = driver.getStartDate().split("-");
      let startDateServerFormat = dateParts[2] + '-' + dateParts[1] + '-' + dateParts[0];
      console.log(startDateServerFormat);
      // Add the driver.
      this.serverService.addDriver(new DriverRequest(driver.getName(), driver.getContractedHours(), startDateServerFormat, this.serverService.getCompanyName())).then(() => {
        // Deduct the hiring costs of 500.
        this.serverService.adjustBalance(-500).then(() => {
          // Redirect to management.
          this.router.navigate(['management']);
        })
      })
    }

  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }


}
