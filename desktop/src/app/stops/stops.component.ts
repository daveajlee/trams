import {Component, OnDestroy, OnInit} from '@angular/core';
import {DataService} from '../shared/data.service';
import {StopsService} from './stops.service';
import {Stop} from './stop.model';
import {Subscription} from 'rxjs';
import {GameService} from "../shared/game.service";

@Component({
  selector: 'app-stops',
  templateUrl: './stops.component.html',
  styleUrls: ['./stops.component.css']
})
/**
 * This class implements the functionality for the stops component which retrieves stop data from the server and sends it to the
 * frontend component for rendering.
 */
export class StopsComponent implements OnInit, OnDestroy {

  stops: Stop[];
  subscription: Subscription;

  /**
   * Create a new stops component which constructs a data service and a stop service to retreive data from the server.
   * @param dataService which contains the HTTP connection to the server
   * @param gameService a service which retrieves game information
   * @param stopsService which formats the HTTP calls into a way which the frontend can read and render.
   */
  constructor(private dataService: DataService, private gameService: GameService, private stopsService: StopsService) { }

  /**
   * Initialise a new stops component which maintains a list of stops that can be updated and set from the server calls.
   */
  ngOnInit(): void {
    if ( this.gameService.getGame().scenario.stopDistances ) {
      this.stops = [];
      var allStops =  this.gameService.getGame().scenario.stopDistances;
      for ( var i = 0; i < allStops.length; i++ ) {
        this.stops.push(new Stop('' + i, allStops[i].split(":")[0], 0, 0));
      }
    } else {
      this.subscription = this.stopsService.stopsChanged.subscribe((stops: Stop[]) => {
        this.stops = stops;
      });
      this.stops = this.stopsService.getStops();
    }
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
