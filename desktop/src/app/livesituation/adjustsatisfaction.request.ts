/**
 * This class defines a model for requests to adjust passenger satisfaction in simulation mode in TraMS.
 */
export class AdjustSatisfactionRequest {

    private company: string;
    private satisfactionRate: number;

    /**
     * Construct a new request to adjust the passenger satisfaction.
     * @param company the company that we should adjust the passenger satisfaction for.
     * @param satisfactionRate the satisfaction rate amount that should be adjusted.
     */
    constructor(company: string, satisfactionRate: number) {
        this.company = company;
        this.satisfactionRate = satisfactionRate;
    }
}