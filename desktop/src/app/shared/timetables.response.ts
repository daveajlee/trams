import {TimetableResponse} from "./timetable.response";

/**
 * This class defines a response for retrieving Timetables in TraMS.
 */
export class TimetablesResponse {

    //a count of the number of timetables which were found by the server.
    public count: number;

    //an array of all timetables found by the server.
    public timetableResponses: TimetableResponse[];

}