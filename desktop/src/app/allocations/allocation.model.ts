/**
 * This class defines allocations in TraMS.
 */
export class Allocation {

    public routeNumber: string;
    public fleetNumber: string;
    public tourNumber: string;

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

}