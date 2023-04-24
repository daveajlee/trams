import {Component, OnDestroy, OnInit} from '@angular/core';
import {Route} from './route.model';
import {Subscription} from 'rxjs';
import {DataService} from '../shared/data.service';
import {RoutesService} from './routes.service';

@Component({
  selector: 'app-routes',
  templateUrl: './routes.component.html',
  styleUrls: ['./routes.component.css']
})
/**
 * This class implements the functionality for the routes component which retrieves route data from the server and sends it to the
 * frontend component for rendering.
 */
export class RoutesComponent implements OnInit, OnDestroy {

  routes: Route[];
  subscription: Subscription;

  /**
   * Create a new routes component which constructs a data service and a route service to retreive data from the server.
   * @param dataService which contains the HTTP connection to the server
   * @param routesService which formats the HTTP calls into a way which the frontend can read and render.
   */
  constructor(private dataService: DataService, private routesService: RoutesService) { }

  /**
   * Initialise a new routes component which maintains a list of routes that can be updated and set from the server calls.
   */
  ngOnInit(): void {
    this.subscription = this.routesService.routesChanged.subscribe((routes: Route[]) => {
      this.routes = routes;
    });
    this.routes = this.routesService.getRoutes();
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}