import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {Vehicle} from './vehicle.model';
import {VehiclesService} from './vehicles.service';
import {DataService} from '../shared/data.service';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
/**
 * This class implements the functionality for the vehicles component which retrieves vehicle data from the server and sends it to the
 * frontend component for rendering.
 */
export class VehiclesComponent implements OnInit, OnDestroy {

  vehicles: Vehicle[];
  subscription: Subscription;

  /**
   * Create a new vehicles component which constructs a data service and a vehicle service to retrieve data from the server.
   * @param dataService which contains the HTTP connection to the server
   * @param vehiclesService which formats the HTTP calls into a way which the frontend can read and render.
   */
  constructor(private dataService: DataService, private vehiclesService: VehiclesService ) { }

  /**
   * Initialise a new stops component which maintains a list of stops that can be updated and set from the server calls.
   */
  ngOnInit(): void {
    this.subscription = this.vehiclesService.vehiclesChanged.subscribe((vehicles: Vehicle[]) => {
      this.vehicles = vehicles;
    });
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
