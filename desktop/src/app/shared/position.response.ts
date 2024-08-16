/**
 * This class defines a model for responses to finding the current position of a vehicle in TraMS.
 */
export class PositionResponse {

    /**
     * The company running the tour.
     */
    public company: string;

    /**
     * The current stop that the tour is at.
     */
    public stop: string;

    /**
     * The destination that the tour is heading to.
     */
    public destination: string;

    /**
     * The current delay of the vehicle.
     */
    public delay: number;

}