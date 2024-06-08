/**
 * This class defines a response for Stop Times in TraMS which consist of a count and a Stop Time array.
 */
import {RealTimeInfo} from './realtimeinfos.model';

export class StopTimesResponse {

    private count: number;
    private stopTimeResponses: RealTimeInfo[];

    /**
     * Retrieve the real time information from the response.
     * @return the real time information as an array of RealTimeInfo objects.
     */
    getStopTimeResponses(): RealTimeInfo[] {
        return this.stopTimeResponses;
    }

}
