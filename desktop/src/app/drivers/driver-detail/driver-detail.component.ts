import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {GameService} from "../../shared/game.service";
import {Driver} from "../driver.model";
import {Subscription} from "rxjs";
import {DriverResponse} from "../driver.response";
import {ServerService} from "../../shared/server.service";

@Component({
  selector: 'app-driver-detail',
  templateUrl: './driver-detail.component.html',
  styleUrls: ['./driver-detail.component.css']
})
/**
 * This class implements the functionality for the driver-detail component which retrieves detailed driver information and
 * sends it to the frontend component for rendering.
 */
export class DriverDetailComponent implements OnInit, OnDestroy {

  private driver: Driver;
  private name: string;
  private idSubscription: Subscription;

  /**
   * Construct a new driver-detail component based on the supplied information.
   * @param gameService a service which can retrieve game information
   * @param route a variable which contains the current driver that the user clicked on.
   * @param serverService a service managing the http calls
   * @param router the router for navigating to other pages.
   */
  constructor(private route: ActivatedRoute, private gameService: GameService, private serverService: ServerService, public router: Router, ) { }

  /**
   * Initialise the vehicle information during construction and ensure all variables are set to the correct data.
   */
  ngOnInit(): void {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.name = params['id'];
      if ( this.gameService.isOfflineMode() ) {
        this.driver = this.gameService.getGame().getDriverByName(this.name);
      } else {
        console.log('Retrieve driver: ' + this.name);
        this.serverService.getDriver(this.name).then((drivers) => {
          console.log(drivers);
          if ( drivers.count > 0 ) {
            this.driver = this.convertResponseToDriver(drivers.driverResponses[0]);
          }
        })
      }
    });
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
   * When destroying this component we have nothing to do so far.
   */
  ngOnDestroy(): void {
  }

  /**
   * Retrieve the name of this driver.
   * @return the name of the driver as a String.
   */
  getName(): string {
    return this.driver ? this.driver.getName() : "";
  }

  /**
   * Retrieve the contracted hours of this driver.
   * @return the contracted hours of this driver as a number,
   */
  getContractedHours(): number {
    return this.driver ? this.driver.getContractedHours() : 0;
  }

  /**
   * Retrieve the start date of this driver.
   * @return the start date of this driver as a string.
   */
  getStartDate(): string {
    return this.driver ? this.driver.getStartDate() : "";
  }

  /**
   * Sack the specified driver by removing the driver from the driver array.
   * This does not cost any money currently.
   */
  sackDriver(): void {
    if ( this.gameService.isOfflineMode() ) {
      this.gameService.getGame().deleteDriverByName(this.driver.getName());
    } else {
      this.serverService.sackDriver(this.driver.getName()).then(() => {
        this.router.navigate(['management']);
      })
    }
  }

}
