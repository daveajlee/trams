/**
 * This class defines a model for Drivers in TraMS
 */
export class Driver {

    public name: string;
    public contractedHours: number;
    public startDate: string;

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

}
