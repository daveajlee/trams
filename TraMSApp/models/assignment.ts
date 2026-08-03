class Assignment {

    routeNumber: string;
    tourNumber: number;
    fleetNumber: number;
    scenarioName: string;
    company: string;

    constructor(routeNumber: string, tourNumber: number, fleetNumber: number, scenarioName: string, company: string) {
        this.routeNumber = routeNumber;
        this.tourNumber = tourNumber;
        this.fleetNumber = fleetNumber;
        this.scenarioName = scenarioName;
        this.company = company;
    }
}

export default Assignment;