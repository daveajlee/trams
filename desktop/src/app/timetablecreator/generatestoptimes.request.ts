export class GenerateStopTimesRequest {

    private company: string;
    private stopNames: string[];
    private routeNumber: string;
    private startTime: string;
    private endTime: string;
    private frequency: number;
    private validFromDate: string;
    private validToDate: string;
    private operatingDays: string;

    /**
     * Construct a new generate stop times request which contains the supplied data.
     * @param company the company which the stop times should be generated for as a string
     * @param stopNames the list of stop names where the service should stop as a string array
     * @param routeNumber the route number that the stop times should be generated for
     * @param startTime the start time of this frequency pattern in format HH:mm
     * @param endTime the end time of this frequency pattern in format HH:mm
     * @param frequency the frequency in minutes that should be operated
     * @param validFromDate the valid from date of this timetable in format dd-MM-yyyy
     * @param validToDate the valid to date of this timetable in format dd-MM-yyyy
     * @param operatingDays the days when these stop times run as a comma-separated string.
     */

    constructor(company: string, stopNames: string[], routeNumber: string, startTime: string, endTime: string,
                frequency: number, validFromDate: string, validToDate: string, operatingDays: string) {
        this.company = company;
        this.stopNames = stopNames;
        this.routeNumber = routeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequency = frequency;
        this.validFromDate = validFromDate;
        this.validToDate = validToDate
        this.operatingDays = operatingDays;
    }

}