class AdditionalTour {

  routeNumber: string;
  tourNumber: number;
  scenarioName: string;
  company: string;

    constructor(routeNumber: string, tourNumber: number, scenarioName: string, company: string) {
        this.routeNumber = routeNumber;
        this.tourNumber = tourNumber;
        this.scenarioName = scenarioName;
        this.company = company;
    }
}

export default AdditionalTour;