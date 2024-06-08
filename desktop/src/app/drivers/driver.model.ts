/**
 * This class defines a model for Drivers in TraMS
 */
export class Driver {

    private name: string;
    private contractedHours: number;
    private startDate: string;

    /**
     * Construct a new model for Drivers which contains the supplied data.
     * @param name the name of this driver
     * @param contractedHours the number of contracted hours for this driver that they are available to work
     * @param startDate the date that the driver started working for the company in dd-mm-yyyy
     */
    constructor( name: string, contractedHours: number, startDate: string) {
        this.name = name;
        this.contractedHours = contractedHours;
        this.startDate = startDate;
    }

    /**
     * Retrieve the name of the driver.
     * @return the name of the driver as a string.
     */
    getName(): string {
        return this.name;
    }

    /**
     * Retrieve the contracted hours of the driver.
     * @return the contracted hours of the driver as a number.
     */
    getContractedHours(): number {
        return this.contractedHours;
    }

    /**
     * Retrieve the start date of the driver.
     * @return the start date of the driver as a string.
     */
    getStartDate(): string {
        return this.startDate;
    }

}
