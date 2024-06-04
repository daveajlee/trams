/**
 * This class defines a response for Stops in TraMS which consist of a count and a Stops array.
 */
import {Stop} from './stop.model';

export class StopsResponse {

    private count: number;
    private stopResponses: Stop[];

    /**
     * Get the stops that were included in the response.
     * @return the stops as an array of Stoo objects.
     */
    getStopResponses(): Stop[] {
        return this.stopResponses;
    }

}
