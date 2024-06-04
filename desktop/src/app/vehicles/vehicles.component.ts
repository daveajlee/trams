import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {Vehicle} from './vehicle.model';
import {VehiclesService} from './vehicles.service';
import {DataService} from '../shared/data.service';
import {HttpClient} from '@angular/common/http';
import {VehiclesResponse} from './vehicles-response.model';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";

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

  private vehicles: Vehicle[];
  private subscription: Subscription;
  private searchSubscription: Subscription;

  /**
   * Create a new vehicles component which constructs a data service and a vehicle service to retrieve data from the server.
   * @param http an http client which can make http calls
   * @param dataService which contains the HTTP connection to the server
   * @param vehiclesService which formats the HTTP calls into a way which the frontend can read and render.
   * @param gameService a service which retrieves game information
   * @param router a router service provided by Angular
   */
  constructor(private http: HttpClient, private dataService: DataService, private vehiclesService: VehiclesService,
              private gameService: GameService, private router:Router) { }

  /**
   * Initialise a new vehicles component which maintains a list of vehicles that can be updated and set from the server calls.
   */
  ngOnInit(): void {
    if ( this.gameService.getGame().doVehiclesExist() ) {
      this.vehicles = this.gameService.getGame().getVehicles();
    } else {
      this.subscription = this.vehiclesService.getVehiclesChanged().subscribe((vehicles: Vehicle[]) => {
        this.vehicles = vehicles;
      });
    }
  }

  /**
   * Retrieve the vehicles currently loaded.
   * @return the list of vehicles as an array of Vehicle objects.
   */
  getVehicles(): Vehicle[] {
    return this.vehicles;
  }

  /**
   * Search for the specified value as a fleet number.
   * @param searchValue the value to search for.
   */
  searchByFleetNumber(searchValue: string): void {
    if ( this.gameService.getGame().doVehiclesExist() ) {
      var foundVehicle = this.gameService.getGame().getVehicleByFleetNumber(searchValue);
      if ( foundVehicle ) {
        this.vehicles = new Array(foundVehicle);
      } else {
        this.vehicles = this.gameService.getGame().getVehicles();
      }
    } else {
      this.searchSubscription = this.http.get<VehiclesResponse>(this.gameService.getServerUrl() + '/' +
          'vehicles/?company=Company&fleetNumber=' + searchValue).subscribe(vehicleInfos => {
        this.vehicles = vehicleInfos.getVehicleResponses();
      });
    }
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
    if ( !this.gameService.getGame().doVehiclesExist() ) {
      this.subscription.unsubscribe();
      this.searchSubscription.unsubscribe();
    }
  }

  /**
   * When the user clicks on the back button, return to management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
