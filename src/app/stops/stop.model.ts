/**
 * This class defines a model for Stops in TraMS which consist of an id, name, latitude and longitude.
 */
export class Stop {

  public id: string;
  public name: string;
  public latitude: number;
  public longitude: number;

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

}
