import {FrequencyPattern} from "./frequencypattern.model";

/**
 * This class defines a response for a single Timetable in TraMS.
 */
export class TimetableResponse {

    /**
     * The name of this timetable.
     */
    public name: string;

    /**
     * The company that this timetable belongs to.
     */
    public company: string;

    /**
     * The route number that this timetable belongs to.
     */
    public routeNumber: string;

    /**
     * The date that this timetable is valid from in format dd-MM-yyyy HH:mm
     */
    public validFromDate: string;

    /**
     * The date that this timetable is valid to in format dd-MM-yyyy HH:mm
     */
    public validToDate: string;

    /**
     * The frequency patterns belonging to this timetable.
     */
    public frequencyPatterns: FrequencyPattern[];

}