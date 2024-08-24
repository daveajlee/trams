/**
 * This class defines a model for requests to change service information in simulation mode in TraMS.
 */
export class ServiceChangeRequest {

    /**
     * Company which services should be reset for
     */
    private company: string;

    /**
     * The id of the service.
     */
    private serviceId: string;

    /**
     * The id of the route and schedule running the service e.g. 3/1
     */
    private scheduleId: string;

    /**
     * A boolean which is true iff the service is out of service i.e. not running.
     */
    private outOfService: boolean;

    /**
     * Allow a service to start after normal stop to reduce delays etc.
     */
    private tempStartStopPos: number;

    /**
     * Allow a service to end before normal stop to reduce delays etc.
     */
    private tempEndStopPos: number;

    /**
     * Construct a new request to change a service.
     * @param company the company that we should change a service for.
     * @param serviceId the id of the service to change / update.
     * @param scheduleId the id of the schedule to change in the format route and schedule number e.g. 3/1
     * @param outOfService a boolean which is true iff the service should not be in service.
     * @param tempStartStopPos the start position of this service when it has been shortened (default: 0).
     * @param tempEndStopPos the end position of this service when it has been shortened (default: 0 or stopList size -1).
     */
    constructor(company: string, serviceId: string, scheduleId: string, outOfService: boolean,
                tempStartStopPos: number, tempEndStopPos: number) {
        this.company = company;
        this.serviceId = serviceId;
        this.scheduleId = scheduleId;
        this.outOfService = outOfService;
        this.tempStartStopPos = tempStartStopPos;
        this.tempEndStopPos = tempEndStopPos;
    }
}