/**
 * This class defines a model for Stop Times in TraMS which consist of a departure and arrival time
 * for a particular stop.
 */
export class StopTimeModel {

    public departureTime: string;
    public arrivalTime: string;
    public stop: string;

    /**
     * Construct a new StopTimeModel object based on the supplied information
     * @param departureTime the time that the vehicle will depart
     * @param arrivalTime the time that the vehicle will arrive
     * @param stop the name of the stop.
     */
    constructor(departureTime: string, arrivalTime: string, stop: string ) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.stop = stop;
    }

}