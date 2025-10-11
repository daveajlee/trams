/**
 * This class defines a response for Stop Times in TraMS which consist of a count and a StopTimeResponse array.
 */
import {StopTimeResponse} from "./stoptime.response";

export class StopTimesResponse {

    public count: number = 0;
    public stopTimeResponses: StopTimeResponse[] = [];

}
