/**
 * This class defines a response for a single Stop Time in TraMS.
 */

export class StopTimeResponse {

    /**
     * The name of the stop where the journey will arrive or depart.
     */
    public stopName: string;

    /**
     * The name of the company operating this journey.
     */
    public company: string;

    /**
     * The arrival time when the journey will arrive which may be null if journey starts here.
     */
    public arrivalTime: string;

    /**
     * The departure time when the journey will depart which may be null if journey ends here.
     */
    public departureTime: string;

    /**
     * The destination of this journey which may be equal to the stop name if the journey ends here.
     */
    public destination: string;

    /**
     * The number of the route which this journey is a part of.
     */
    public routeNumber: string;

    /**
     * The schedule number for this journey.
     */
    public scheduleNumber: number;

    /**
     * The date from which this stop occurs (inclusive).
     */
    public validFromDate: string;

    /**
     * The date until which this stop occurs (inclusive).
     */
    public validToDate: string;

    /**
     * The days on which this stop takes place.
     */
    public operatingDays: string[];

    /**
     * The number of the journey which can contain both alphanumeric and alphabetical characters.
     */
    public journeyNumber: string;

}
