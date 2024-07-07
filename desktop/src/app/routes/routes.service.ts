import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Route} from './route.model';
import {RoutesResponse} from './routes-response.model';
import {Scenario} from "../shared/scenario.model";

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
    this.routes = routesResponse.routeResponses;
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

  /**
   * Get the duration of this route,
   * @param scenario the scenario object containing the stop distances.
   * @param startStop the name of the start stop as a string.
   * @param stops an array of all intermediate stops as a string array.
   * @param endStop the name of the end stop as a string.
   * @return the duration in minutes as a number.
   */
  getDuration(scenario: Scenario, startStop: string, stops: string[], endStop: string): number {
    // Calculate the duration.
    let duration = 0;
    if ( stops.length > 0 ) {
      duration += scenario.getDistanceBetweenStop(startStop, stops[0]);
      if ( stops.length > 1 ) {
        for ( var i = 0; i < stops.length-1; i++ ) {
          duration += scenario.getDistanceBetweenStop(stops[i], stops[i+1]);
        }
        duration += scenario.getDistanceBetweenStop(stops[stops.length-1], endStop);
      } else {
        duration += scenario.getDistanceBetweenStop(stops[0], endStop);
      }
    } else {
      duration += scenario.getDistanceBetweenStop(startStop, endStop);
    }
    // Check that duration is greater than 0.
    if ( duration > 0 ) {
      // The duration is one-way so double it for both directions.
      duration *= 2;
    }
    return duration;
  }

}
