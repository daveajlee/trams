/**
 * This class defines a model for Stops in TraMS which consist of an id, name, latitude and longitude.
 */
export class Stop {

  private id: string;
  private name: string;
  private latitude: number;
  private longitude: number;

  /**
   * Construct a new model for Stops which contains the supplied data.
   * @param id an id for this stop
   * @param name a name for this stop
   * @param latitude a latitude position for this stop
   * @param longitude a longitude position for this stop
   */
  constructor( id: string, name: string, latitude: number, longitude: number ) {
    this.id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Retrieve the name of the stop.
   * @return the name of the stop as a String.
   */
  getName(): string {
    return this.name;
  }

  /**
   * Retrieve the latitude of the stop.
   * @return the latitude of the stop as a number.
   */
  getLatitude(): number {
    return this.latitude;
  }

  /**
   * Retrieve the longitude of the stop.
   * @return the longitude of the stop as a number.
   */
  getLongitude(): number {
    return this.longitude;
  }

}
