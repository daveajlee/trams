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

  private driver: Driver;
  private id: number;
  private idSubscription: Subscription;

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
      this.driver = this.gameService.getGame().getDriverByPosition(this.id);
    });
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
    return this.driver.getName();
  }

  /**
   * Retrieve the contracted hours of this driver.
   * @return the contracted hours of this driver as a number,
   */
  getContractedHours(): number {
    return this.driver.getContractedHours();
  }

  /**
   * Retrieve the start date of this driver.
   * @return the start date of this driver as a string.
   */
  getStartDate(): string {
    return this.driver.getStartDate();
  }

  /**
   * Sack the specified driver by removing the driver from the driver array.
   * This does not cost any money currently.
   */
  sackDriver(): void {
    this.gameService.getGame().deleteDriverByName(this.driver.getName());
  }

}
