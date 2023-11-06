/**
 * This class defines a model for Frequency Patterns in TraMS.
 */
export class FrequencyPattern {

    public name: string;
    public daysOfOperation: string[];
    public startStop: string;
    public endStop: string;
    public startTime: string;
    public endTime: string;
    public frequencyInMinutes: number;
    public numTours: number;

    /**
     * Construct a new frequency pattern which contains the supplied data.
     * @param name the name of the frequency pattern
     * @param daysOfOperation a string array of the days that the frequency pattern runs
     * @param startStop the start terminus of this frequency pattern.
     * @param endStop the end terminus of this frequency pattern
     * @param startTime the start time when the frequency begins
     * @param endTime the start time when the frequency ends
     * @param frequencyInMinutes the frequency that will be run in minutes.
     * @param numTours the number of tours that is required for this frequency pattern.
     */

    constructor( name: string, daysOfOperation: string[], startStop: string, endStop: string,
                 startTime: string, endTime: string, frequencyInMinutes: number, numTours: number ) {
        this.name = name;
        this.daysOfOperation = daysOfOperation;
        this.startStop = startStop;
        this.endStop = endStop;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequencyInMinutes = frequencyInMinutes;
        this.numTours = numTours;
    }

}