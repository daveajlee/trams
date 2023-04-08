/**
 * This class defines a response for Stop Times in TraMS which consist of a count and a Stop Time array.
 */
import {RealTimeInfo} from './realtimeinfos.model';

export class StopTimesResponse {

    public count: number;
    public stopTimeResponses: RealTimeInfo[];

}
