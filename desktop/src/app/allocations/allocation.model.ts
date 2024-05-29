/**
 * This class defines allocations in TraMS.
 */
export class Allocation {

    private routeNumber: string;
    private fleetNumber: string;
    private tourNumber: string;

    /**
     * Construct a new Allocation object which contains the supplied data.
     * @param routeNumber the route number of the assignment.
     * @param fleetNumber the fleet number of the assignment.
     * @param tourNumber the tour number of the assignment.
     */
    constructor(routeNumber: string, fleetNumber: string, tourNumber: string) {
        this.routeNumber = routeNumber;
        this.fleetNumber = fleetNumber;
        this.tourNumber = tourNumber;
    }

    /**
     * Retrieve the route number,
     * @return the route number for this allocation.
     */
    getRouteNumber(): string {
        return this.routeNumber;
    }

    /**
     * Retrieve the fleet number.
     * @return the fleet number for this allocation.
     */
    getFleetNumber(): string {
        return this.fleetNumber;
    }

    /**
     * Retrieve the tour number.
     * @return the tour number for this allocation.
     */
    getTourNumber(): string {
        return this.tourNumber;
    }

}