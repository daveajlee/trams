import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {GameService} from "../../shared/game.service";
import {Driver} from "../driver.model";
import {Subscription} from "rxjs";

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

  driver: Driver;
  id: number;
  idSubscription: Subscription;

  /**
   * Construct a new driver-detail component based on the supplied information.
   * @param gameService a service which can retrieve game information
   * @param route a variable which contains the current driver that the user clicked on.
   */
  constructor(private route: ActivatedRoute, private gameService: GameService) { }

  /**
   * Initialise the vehicle information during construction and ensure all variables are set to the correct data.
   */
  ngOnInit(): void {
    this.idSubscription = this.route.params.subscribe((params: Params) => {
      this.id = +params['id'];
      this.driver = this.gameService.getGame().drivers[this.id];
    });
  }

  /**
   * When destroying this component we have nothing to do so far.
   */
  ngOnDestroy(): void {
  }

  sackDriver(driver: Driver): void {
    var allDrivers = this.gameService.getGame().drivers;
    for ( var i = 0; i < allDrivers.length; i++ ) {
      if ( this.gameService.getGame().drivers[i].name.valueOf() === driver.name.valueOf() ) {
        this.gameService.getGame().drivers.splice(i, 1);
      }
    }
    console.log('Currently the length of drivers is: ' + this.gameService.getGame().drivers.length )
  }

}
