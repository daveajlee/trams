/**
 * This class defines a model for Real Time Information in TraMS which consist of a departure and arrival time,
 * route number and destination.
 */
export class RealTimeInfo {

  public departureTime: string;
  public arrivalTime: string;
  public routeNumber: string;
  public destination: string;

  /**
   * Construct a new Real Time Info object based on the supplied information
   * @param departureTime the time that the vehicle will depart
   * @param arrivalTime the time that the vehicle will arrive
   * @param routeNumber the route number of this trip
   * @param destination the destination of this trip
   */
  constructor( departureTime: string, arrivalTime: string, routeNumber: string, destination: string ) {
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.routeNumber = routeNumber;
    this.destination = destination;
  }

}
