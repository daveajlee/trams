import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StopsService} from '../stops/stops.service';
import {RoutesService} from '../routes/routes.service';
import {VehiclesService} from '../vehicles/vehicles.service';
import {RoutesResponse} from '../routes/routes-response.model';
import {StopsResponse} from '../stops/stops-response.model';
import {VehiclesResponse} from '../vehicles/vehicles-response.model';
import {GameService} from "./game.service";

@Injectable({providedIn: 'root'})
/**
 * This class provides access via http calls to the server to collect data about stops and routes as necessary.
 */
export class DataService {

  /**
   * Construct a new DataService object which calls the http calls and displays the data provided.
   * @param http an http client which can make http calls
   * @param stopsService a service which can store stops
   * @param routesService a service which can store routes
   * @param vehiclesService a service which can store vehicles
   * @param gameService a service which stores the current game and whether offline version is currently enabled.
   */
  constructor(private http: HttpClient, private stopsService: StopsService, private routesService: RoutesService,
              private vehiclesService: VehiclesService, private gameService: GameService) {
    if ( !this.gameService.isOfflineVersion() ) {
      this.http.get<StopsResponse>('http://localhost:8084/api/stops/?company=Company').subscribe(stops => {
        this.stopsService.setStops(stops);
      });
      this.http.get<RoutesResponse>('http://localhost:8084/api/routes/?company=Company').subscribe(routes => {
        this.routesService.setRoutes(routes);
      });
      this.http.get<VehiclesResponse>('http://localhost:8084/api/vehicles/?company=Company').subscribe(vehicles => {
        this.vehiclesService.setVehicles(vehicles);
      });
    }
  }

}
