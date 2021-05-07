import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {Vehicle} from './vehicle.model';
import {VehiclesService} from './vehicles.service';
import {DataService} from '../shared/data.service';
import {HttpClient} from '@angular/common/http';

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
  searchSubscription: Subscription;

  /**
   * Create a new vehicles component which constructs a data service and a vehicle service to retrieve data from the server.
   * @param http an http client which can make http calls
   * @param dataService which contains the HTTP connection to the server
   * @param vehiclesService which formats the HTTP calls into a way which the frontend can read and render.
   */
  constructor(private http: HttpClient, private dataService: DataService, private vehiclesService: VehiclesService ) { }

  /**
   * Initialise a new stops component which maintains a list of stops that can be updated and set from the server calls.
   */
  ngOnInit(): void {
    this.subscription = this.vehiclesService.vehiclesChanged.subscribe((vehicles: Vehicle[]) => {
      this.vehicles = vehicles;
    });
  }

  searchByFleetNumber(searchValue: string): void {
    console.log('Search was: ' + searchValue);
    this.searchSubscription = this.http.get<Vehicle[]>('http://localhost:8080/trams-operations/' +
        'vehiclesCompanyFleetNumber?fleetNumber=' + searchValue).subscribe(vehicleInfos => {
      this.vehicles = vehicleInfos;
    });
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.searchSubscription.unsubscribe();
  }

}
