import {Component, OnDestroy, OnInit} from '@angular/core';
import {Route} from './route.model';
import {RoutesService} from './routes.service';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {ServerService} from "../shared/server.service";

@Component({
  selector: 'app-routes',
  templateUrl: './routes.component.html',
  styleUrls: ['./routes.component.css','./icofont.min.css']
})
/**
 * This class implements the functionality for the routes component which retrieves route data from the server and sends it to the
 * frontend component for rendering.
 */
export class RoutesComponent implements OnInit, OnDestroy {

  private allRoutes: Route[];
  private routes: Route[];
  filteredRouteNumber: string;

  /**
   * Create a new routes component which constructs a data service and a route service to retreive data from the server.
   * @param serverService which contains the HTTP connection to the server
   * @param routesService which formats the HTTP calls into a way which the frontend can read and render.
   * @param gameService a service which retrieves game information
   * @param router a router service provided by Angular
   */
  constructor(private serverService: ServerService, private routesService: RoutesService, private gameService: GameService,
              private router:Router) {
    if ( this.gameService.isOfflineMode() ) {
      this.allRoutes = this.gameService.getGame().getRoutes();
      this.routes = this.allRoutes;
    } else {
      this.serverService.getRoutes().then((routes) => {
        // Set the routes to an empty array.
        this.allRoutes = [];
        if ( routes && routes.count > 0 ) {
          // Convert the route responses to route objects and add to array.
          for ( let i = 0; i < routes.routeResponses.length; i++ ) {
            let route = new Route(routes.routeResponses[i].routeNumber, routes.routeResponses[i].startStop,
                routes.routeResponses[i].endStop, routes.routeResponses[i].stops, routes.routeResponses[i].company);
            route.setNightRoute(routes.routeResponses[i].nightRoute);
            this.allRoutes.push(route);
          }
        }
        this.routes = this.allRoutes;
      })
    }
  }

  /**
   * Initialise a new routes component which maintains a list of routes that can be updated and set from the server calls.
   */
  ngOnInit(): void {
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
  }

  /**
   * Filter the routes based on day or night.
   * @param dayRoutes true iff day routes should be shown or false if only night routes should be shown.
   */
  filterRoutes(dayRoutes: boolean): void {
    let routes = this.allRoutes;
    if ( dayRoutes ) {
      this.routes = routes.filter((route: Route) =>
          !route.isNightRoute())
    } else {
      this.routes = routes.filter((route: Route) =>
          route.isNightRoute() === true)
    }
  }

  /**
   * Filter the route number.
   */
  filterRouteNumber(): void {
    let routes = this.allRoutes;
    if ( this.filteredRouteNumber != "" ) {
      this.routes = routes.filter((route: Route) =>
          route.getRouteNumber().startsWith(this.filteredRouteNumber));
    } else {
      this.routes = routes;
    }
  }

  /**
   * This method deletes the route with the supplied route number.
   * @param routeNumber the route number to delete.
   */
  deleteRoute(routeNumber: string) {
    if(confirm("Are you sure you want to delete route " + routeNumber + "?") == true) {
      if ( this.gameService.isOfflineMode() ) {
        this.gameService.getGame().deleteRoute(routeNumber);
        this.router.navigate(['management']);
      } else {
        this.serverService.deleteRoute(routeNumber).then(() => {
          this.router.navigate(['management']);
        })
      }
    }
  }

  /**
   * This method opens the route editor with the supplied route number.
   * @param routeNumber the route number to edit.
   */
  editRoute(routeNumber: string) {
    this.router.navigate(['routeeditor', routeNumber]);
  }

  /**
   * This method opens the timetable viewer with the supplied route number.
   * @param routeNumber the route number to view the timetable of.
   */
  viewTimetable(routeNumber: string) {
    this.router.navigate(['timetableviewer', routeNumber]);
  }

  /**
   * Retrieve the routes to be displayed to the user.
   * @return the list of routes that should currently be displayed to the user.
   */
  getRoutes(): Route[] {
    return this.routes;
  }

  /**
   * This method returns to the management screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
