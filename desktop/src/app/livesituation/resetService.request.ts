/**
 * This class defines a model for requests to reset services in simulation mode in TraMS.
 */
export class ResetServiceRequest {

    private company: string;

    /**
     * Construct a new request to reset services.
     * @param company the company that we should reset services for.
     */
    constructor(company: string) {
        this.company = company;
    }
}