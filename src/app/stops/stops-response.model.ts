/**
 * This class defines a response for Stops in TraMS which consist of a count and a Stops array.
 */
import {Stop} from './stop.model';

export class StopsResponse {

    public count: number;
    public stopResponses: Stop[];

}
