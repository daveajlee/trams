class Tour {

    routeNumber: string;
    tourNumber: number;
    fleetNumber: string;

    constructor(routeNumber: string, tourNumber: number, fleetNumber: string) {
        this.routeNumber = routeNumber;
        this.fleetNumber = fleetNumber;
        this.tourNumber = tourNumber;
    }
}

export default Tour;