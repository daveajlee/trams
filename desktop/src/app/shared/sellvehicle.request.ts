/**
 * This class defines a model for requests to sell vehicles in TraMS.
 */
export class SellVehicleRequest {

    private company: string;
    private fleetNumber: string;

    /**
     * Construct a new request to sell a vehicle.
     * @param company the company that the vehicle belonged to.
     * @param fleetNumber the fleet number of the vehicle to sell.
     */

    constructor(company: string, fleetNumber: string) {
        this.company = company;
        this.fleetNumber = fleetNumber;
    }
}