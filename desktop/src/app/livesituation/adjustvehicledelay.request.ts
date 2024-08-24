/**
 * This class defines a model for requests to adjust vehicle delay in simulation mode in TraMS.
 */
export class AdjustVehicleDelayRequest {

    private company: string;
    private fleetNumber: string;
    private delayInMinutes: number;

    /**
     * Construct a new request to adjust the vehicle delay.
     * @param company the company that we should adjust the vehicle delay for.
     * @param fleetNumber the fleet number of the vehicle that should have its delay adjusted.
     * @param delayInMinutes the delay in minutes that should be adjusted.
     */
    constructor(company: string, fleetNumber: string, delayInMinutes: number) {
        this.company = company;
        this.fleetNumber = fleetNumber;
        this.delayInMinutes = delayInMinutes;
    }
}