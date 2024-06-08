import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Route} from './route.model';
import {RoutesResponse} from './routes-response.model';

@Injectable()
/**
 * This class provides access to the list of routes from the server so that all routes or a single route from the list can be returned
 * to the Frontend component.
 */
export class RoutesService {

  private routesChanged = new Subject<Route[]>();

  private routes: Route[] = [];

  /**
   * Set the list of routes to new routes supplied from the server.
   * @param routesResponse the response sent from the server with matching routes.
   */
  setRoutes(routesResponse: RoutesResponse): void {
    this.routes = routesResponse.getRouteResponses();
    this.routesChanged.next(this.routes.slice());
  }

  /**
   * Return the current list of routes which the server has provided.
   */
  getRoutes( ): Route[] {
    return this.routes.slice();
  }

  /**
   * Return a single route based on the supplied position in the array.
   * @param index a number containing the position in the array to return.
   */
  getRoute( index: number ): Route {
    return this.routes.slice()[index];
  }

  /**
   * Get the routes changed subject.
   * @return the routesChanged subject object.
   */
  getRoutesChanged(): Subject<Route[]> {
    return this.routesChanged;
  }

}
