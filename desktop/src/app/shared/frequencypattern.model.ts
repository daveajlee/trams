/**
 * This class defines a model for Frequency Patterns in TraMS.
 */
export class FrequencyPattern {

    private name: string;
    private daysOfOperation: string[];
    private startStop: string;
    private endStop: string;
    private startTime: string;
    private endTime: string;
    private frequencyInMinutes: number;
    private numTours: number;

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

    /**
     * Get the name of this frequency pattern.
     * @return the name as a String.
     */
    getName(): string {
        return this.name;
    }

    /**
     * Get the start time of this frequency pattern.
     * @return the start time in the format HH:mm as a string.
     */
    getStartTime(): string {
        return this.startTime;
    }

    /**
     * Get the end time of this frequency pattern.
     * @return the end time in the format HH:mm as a string.
     */
    getEndTime(): string {
        return this.endTime;
    }

    /**
     * Get the start stop of this frequency pattern.
     * @return the start stop name as a String.
     */
    getStartStop(): string {
        return this.startStop;
    }

    /**
     * Get the end stop of this frequency pattern.
     * @return the end stop name as a String.
     */
    getEndStop(): string {
        return this.endStop;
    }

    /**
     * Get the frequency of this frequency pattern in minutes.
     * @return the frequency in minutes as a number.
     */
    getFrequencyInMinutes(): number {
        return this.frequencyInMinutes;
    }

    /**
     * Get the number of tours that are required to fulfil this frequency pattern.
     * @return the number of tours as a number.
     */
    getNumTours(): number {
        return this.numTours;
    }

}