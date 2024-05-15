import {FrequencyPattern} from "./frequencypattern.model";

/**
 * This class defines a model for Timetables in TraMS.
 */
export class Timetable {

    public name: string;
    public validFromDate: Date;
    public validToDate: Date;
    public frequencyPatterns: FrequencyPattern[];

    /**
     * Construct a new timetable which contains the supplied data.
     * @param name the name of the timetable
     * @param validFromDate the valid from date of this timetable in format dd-MM-yyyy
     * @param validToDate the valid to date of this timetable in format dd-MM-yyyy
     * @param frequencyPatterns the frequency patterns belonging to this timetable.
     */

    constructor( name: string, validFromDate: string, validToDate: string, frequencyPatterns: FrequencyPattern[] ) {
        this.name = name;
        this.validFromDate = new Date(validFromDate);
        this.validToDate = new Date(validToDate);
        this.frequencyPatterns = frequencyPatterns;
    }

}