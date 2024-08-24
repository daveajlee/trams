/**
 * This class defines a model for Route Responses in TraMS from the server.
 */
export class RouteResponse {

  public routeNumber: string;
  public company: string;
  public startStop: string;
  public endStop: string;
  public stops: string[];
  public nightRoute: boolean;

  constructor( routeNumber: string, startStop: string, endStop: string, stops: string[], company: string) {
    this.routeNumber = routeNumber;
    this.startStop = startStop;
    this.endStop = endStop;
    this.stops = stops;
    this.company = company;
  }

}
