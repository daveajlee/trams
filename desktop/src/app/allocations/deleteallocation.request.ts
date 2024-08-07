/**
 * This class define a model for requesting deletion of a vehicle being allocated to a route.
 */
export class DeleteAllocationRequest {

    private fleetNumber: string;
    private company: string;

    /**
     * Construct a new request to deallocate a vehicle.
     * @param company the company that we should deallocate the vehicle to.
     * @param fleetNumber the fleet number that should be deallocated.
     */
    constructor(company: string, fleetNumber: string) {
        this.company = company;
        this.fleetNumber = fleetNumber;
    }

}