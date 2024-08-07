/**
 * This class define a model for requesting a vehicle to be allocated to a route.
 */
export class AllocationRequest {

    private fleetNumber: string;
    private company: string;
    private allocatedRoute: string;
    private allocatedTour: string;

    /**
     * Construct a new request to allocate a vehicle.
     * @param company the company that we should allocate the vehicle to.
     * @param fleetNumber the fleet number that should be allocated.
     * @param allocatedRoute the route number that should be allocated.
     * @param allocatedTour the tour or schedule number that should be allocated.
     */
    constructor(company: string, fleetNumber: string, allocatedRoute: string, allocatedTour: string) {
        this.company = company;
        this.fleetNumber = fleetNumber;
        this.allocatedRoute = allocatedRoute;
        this.allocatedTour = allocatedTour;
    }

}