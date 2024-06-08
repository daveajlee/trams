import {Component, OnDestroy, OnInit} from '@angular/core';
import {DataService} from '../shared/data.service';
import {StopsService} from './stops.service';
import {Stop} from './stop.model';
import {Subscription} from 'rxjs';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";

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

  private stops: Stop[];
  private subscription: Subscription;
  private alphabet: string[];

  /**
   * Create a new stops component which constructs a data service and a stop service to retreive data from the server.
   * @param dataService which contains the HTTP connection to the server
   * @param gameService a service which retrieves game information
   * @param stopsService which formats the HTTP calls into a way which the frontend can read and render.
   * @param router the router object from Angular to move screens.
   */
  constructor(private dataService: DataService, private gameService: GameService, private stopsService: StopsService, public router: Router) { }

  /**
   * Initialise a new stops component which maintains a list of stops that can be updated and set from the server calls.
   */
  ngOnInit(): void {
    this.alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
    this.stops = this.retrieveAllStops();
  }

  /**
   * Destroy the subscription when the component is destroyed.
   */
  ngOnDestroy(): void {
    if ( this.subscription ) {
      this.subscription.unsubscribe();
    }
  }

  /**
   * Retrieve the alphabet as a list.
   * @return the
   */
  getAlphabet(): string[] {
    return this.alphabet;
  }

  /**
   * Retrieve the current list of stops (which may be all or filtered).
   * @return the list of stops as an array of Stops objects.
   */
  getStops(): Stop[] {
    return this.stops;
  }

  /**
   * Helper method to retrieve all stops.
   */
  retrieveAllStops(): Stop[] {
    if ( this.gameService.getGame().getScenario().getStopDistances() ) {
      let stops = [];
      var allStops =  this.gameService.getGame().getScenario().getStopDistances();
      for ( var i = 0; i < allStops.length; i++ ) {
        stops.push(new Stop('' + i, allStops[i].split(":")[0], 0, 0));
      }
      return stops;
    } else {
      this.subscription = this.stopsService.getStopsChanged().subscribe((stops: Stop[]) => {
        this.stops = stops;
      });
      return this.stopsService.getStops();
    }
  }

  /**
   * Filter the stops based on the supplied letter.
   * @param letter the letter to filter the results for.
   */
  filterByLetter(letter: string): void {
    let stops = this.retrieveAllStops();
    this.stops = stops.filter((stop: Stop) =>
      stop.getName().toLowerCase().startsWith(letter));
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
