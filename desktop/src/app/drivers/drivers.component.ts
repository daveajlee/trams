import {Component, OnDestroy, OnInit} from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {Driver} from "./driver.model";

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
   */
  constructor(private gameService: GameService, private router:Router) { }

  /**
   * Initialise a new drivers component which maintains a list of drivers.
   */
  ngOnInit(): void {
    this.drivers = this.gameService.getGame().getDrivers();
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
