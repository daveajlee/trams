/**
 * This class defines a model for requests to add stops in TraMS.
 */
export class AddStopRequest {

    /**
     * The name of the stop.
     */
    private name: string;

    /**
     * The name of the company serving this stop.
     */
    private company: string;

    /**
     * The waiting time at the stop for a vehicle.
     */
    private waitingTime: number;

    /**
     * A list of the other stop names since some programming languages do not support map/key values.
     */
    private otherStopNames: string[];

    /**
     * The distances between this stop and the other stops. The distance matches the position in the stop names list.
     */
    private otherStopDistances: number[];

    /**
     * The latitude location of the stop which should be in a valid format for a latitude e.g. 50.0200004
     */
    private latitude: number;

    /**
     * The longitude location of the stop which should be in a valid format for a longitude e.g. 50.0200004
     */
    private longitude: number;

    /**
     * Construct a new add stop request which contains the supplied data.
     * @param name the name of the stop
     * @param company the name of the company
     * @param waitingTime the waiting time at this stop
     * @param otherStopNames the names of the other stops that are included in the distances
     * @param otherStopDistances the distances between the other stops
     * @param latitude the latitude location of this stop
     * @param longitude the longitude location of this stop
     */
    constructor( name: string, company: string, waitingTime: number, otherStopNames: string[], otherStopDistances: number[],
                 latitude: number, longitude: number ) {
        this.name = name;
        this.company = company;
        this.waitingTime = waitingTime;
        this.otherStopNames = otherStopNames;
        this.otherStopDistances = otherStopDistances;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}