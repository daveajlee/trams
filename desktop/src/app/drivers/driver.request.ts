/**
 * This class defines a model for requests to employ drivers in TraMS.
 */
export class DriverRequest {

    private name: string;
    private contractedHours: number;
    private startDate: string;
    private company: string;

    /**
     * Construct a new driver request which contains the supplied data.
     * @param name the name of the driver
     * @param contractedHours the contracted hours of the driver
     * @param startDate the start date for the driver
     * @param company the company that the driver will work for.
     */
    constructor(name: string, contractedHours: number, startDate: string, company: string) {
        this.name = name;
        this.contractedHours = contractedHours;
        this.startDate = startDate;
        this.company = company;
    }

}