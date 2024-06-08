import {FrequencyPattern} from "./frequencypattern.model";

/**
 * This class defines a model for Timetables in TraMS.
 */
export class Timetable {

    private name: string;
    private validFromDate: Date;
    private validToDate: Date;
    private frequencyPatterns: FrequencyPattern[];

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

    /**
     * Get the name of the timetable.
     * @return the name as a String.
     */
    getName(): string {
        return this.name;
    }

    /**
     * Retrieve the frequency patterns of the timetable.
     * @return the frequency patterns as an array.
     */
    getFrequencyPatterns(): FrequencyPattern[] {
        return this.frequencyPatterns;
    }

    /**
     * Retrieve the valid from date of the timetable.
     * @return the valid from date as Date object.
     */
    getValidFromDate(): Date {
        return this.validFromDate;
    }

    /**
     * Retrieve the valid to date of the timetable.
     * @return the valid to date as Date object.
     */
    getValidToDate(): Date {
        return this.validToDate;
    }

}