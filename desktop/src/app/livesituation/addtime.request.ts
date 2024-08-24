/**
 * This class defines a model for requests to add time in minutes in simulation mode in TraMS.
 */
export class AddTimeRequest {

    private company: string;
    private minutes: number;

    /**
     * Construct a new request to add time in minutes.
     * @param company the company that we should add time for.
     * @param minutes the number of minutes that we should add to the time.
     */
    constructor(company: string, minutes: number) {
        this.company = company;
        this.minutes = minutes;
    }
}