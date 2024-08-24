/**
 * This class defines a response for a single Driver in TraMS.
 */
export class DriverResponse {

    /**
     * The name of this driver.
     */
    public name: string;

    /**
     * The company that this timetable belongs to.
     */
    public company: string;

    /**
     * The contracted hours that the driver works.
     */
    public contractedHours: number;

    /**
     * The date that the driver starts from in format dd-MM-yyyy HH:mm
     */
    public startDate: string;

}