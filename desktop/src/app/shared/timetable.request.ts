import {FrequencyPattern} from "./frequencypattern.model";

/**
 * This class defines a model for Timetables in TraMS.
 */
export class TimetableRequest {

    private name: string;
    private validFromDate: Date;
    private validToDate: Date;
    private frequencyPatterns: FrequencyPattern[];
    private company: string;
    private routeNumber: string;

    /**
     * Construct a new timetable which contains the supplied data.
     * @param name the name of the timetable
     * @param validFromDate the valid from date of this timetable in format dd-MM-yyyy
     * @param validToDate the valid to date of this timetable in format dd-MM-yyyy
     * @param frequencyPatterns the frequency patterns belonging to this timetable.
     * @param company the company that the timetable belongs to.
     * @param routeNumber the route number that the timetable belongs to.
     */

    constructor(name: string, validFromDate: string, validToDate: string, frequencyPatterns: FrequencyPattern[], company: string,
                routeNumber: string) {
        this.name = name;
        this.validFromDate = new Date(validFromDate);
        this.validToDate = new Date(validToDate);
        this.frequencyPatterns = frequencyPatterns;
        this.company = company;
        this.routeNumber = routeNumber;
    }
}