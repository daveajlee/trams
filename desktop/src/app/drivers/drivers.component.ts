import {Component, OnDestroy, OnInit} from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Driver} from "./driver.model";
import {ServerService} from "../shared/server.service";
import {DriverResponse} from "./driver.response";

@Component({
  selector: 'app-drivers',
  templateUrl: './drivers.component.html',
  styleUrls: ['./drivers.component.css']
})
export class DriversComponent implements OnInit, OnDestroy {

  private drivers: Driver[];

  /**
   * Create a new drivers component which currently uses game service since the server does not yet has this functionality.
   * @param gameService a service which retrieves game information
   * @param router a router service provided by Angular
   * @param serverService a service which manages http calls
   */
  constructor(private gameService: GameService, private router:Router, private serverService: ServerService) { }

  /**
   * Initialise a new drivers component which maintains a list of drivers.
   */
  ngOnInit(): void {
    if ( this.gameService.isOfflineMode() ) {
      this.drivers = this.gameService.getGame().getDrivers();
    } else {
      this.serverService.getDrivers().then((drivers) => {
        if ( drivers.count > 0 ) {
          this.drivers = [];
          for ( let i = 0; i < drivers.count; i++ ) {
            this.drivers[i] = this.convertResponseToDriver(drivers.driverResponses[i]);
          }
        }
      })
    }
  }

  /**
   * Convert a response to a driver object.
   * @param driverResponse the response to be converted to a driver object.
   * @return the converted driver object.
   */
  convertResponseToDriver( driverResponse: DriverResponse ): Driver {
    return new Driver(driverResponse.name, driverResponse.contractedHours, driverResponse.startDate);
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
  }

  /**
   * Clicking the back to management screen so take us back to the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

  /**
   * Retrieve the array of drivers that are stored.
   * @return the array of drivers.
   */
  getDrivers(): Driver[] {
    return this.drivers;
  }

}
