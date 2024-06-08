/**
 * This class defines a model for Real Time Information in TraMS which consist of a departure and arrival time,
 * route number and destination.
 */
export class RealTimeInfo {

  private departureTime: string;
  private arrivalTime: string;
  private routeNumber: string;
  private destination: string;

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

  /**
   * Retrieve the departure time for this entry.
   * @return the departure time as a String in the format HH:mm
   */
  getDepartureTime(): string {
    return this.departureTime;
  }

  /**
   * Retrieve the arrival time for this entry.
   * @return the arrival time as a String in the format HH:mm
   */
  getArrivalTime(): string {
    return this.arrivalTime;
  }

  /**
   * Retrieve the route number for this entry.
   * @return the route number as a String.
   */
  getRouteNumber(): string {
    return this.routeNumber;
  }

  /**
   * Retrieve the destination for this entry.
   * @return the destination as a String.
   */
  getDestination(): string {
    return this.destination;
  }

}
