/**
 * This class defines a model for requests to adjust the balance in TraMS.
 */
export class AdjustBalanceRequest {

    private company: string;
    private value: number;

    /**
     * Construct a new request to adjust the balance.
     * @param company the company that we should adjust the balance for.
     * @param value the amount that we should adjust the balance by.
     */
    constructor(company: string, value: number) {
        this.company = company;
        this.value = value;
    }
}