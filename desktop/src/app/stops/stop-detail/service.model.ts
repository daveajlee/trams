import {StopTimeModel} from "./stoptime.model";

/**
 * This class defines a model for a service belonging to a schedule.
 */
export class ServiceModel {

    private serviceId: string;
    private scheduleId: string;
    private stopList: StopTimeModel[];
    private outOfService: boolean;

    // These variables allow a service to either start or stop before normal to reduce delays etc.
    private tempStartStopPos: number;
    private tempEndStopPos: number;

    /**
     * Construct a new ServiceModel object based on the supplied information
     * @param serviceId the id of the service
     */
    constructor( serviceId: string ) {
        this.serviceId = serviceId;
        this.stopList = [];
        this.outOfService = false;
        this.tempStartStopPos = -1;
        this.tempEndStopPos = -1;
    }

    /**
     * Add departure, arrival and stop name for a particular stop to the service.
     * @param departureTime the time that the vehicle will depart
     * @param arrivalTime the time that the vehicle will arrive
     * @param stop the name of the stop.
     * @param scheduleNumber the schedule number for this service.
     */
    addStop(departureTime: string, arrivalTime: string, stop: string, scheduleNumber: string) {
        this.stopList.push(new StopTimeModel(departureTime, arrivalTime, stop, scheduleNumber));
    }

    /**
     * Set the service to out of service if the user desires to reduce delays.
     */
    setServiceToOutOfService() {
        this.outOfService = true;
    }

    /**
     * Check if the service is out of service.
     * @return a boolean which is true iff service is out of service.
     */
    isOutOfService(): boolean {
        return this.outOfService;
    }

    /**
     * Retrieve the list of stops served by this service.
     * @return the list of stops as an array of StopTimeModel objects.
     */
    getStopList(): StopTimeModel[] {
        return this.stopList;
    }

    /**
     * Set the temporary start stop for this service by setting the position.
     * @param stop the name of the stop as a string.
     */
    setTempStartStop(stop: string) {
        // Go through the stop list and set the position of the matching stop.
        for (let i = 0; i < this.stopList.length; i++) {
            if ( this.stopList[i].getStop() == stop ) {
                this.tempStartStopPos = i;
            }
        }
    }

    /**
     * Set the temporary end stop for this service by setting the position.
     * @param stop the name of the stop as a string.
     */
    setTempEndStop(stop: string) {
        // Go through the stop list and set the position of the matching stop.
        for ( let i = 0; i < this.stopList.length; i++) {
            if ( this.stopList[i].getStop() == stop ) {
                this.tempEndStopPos = i;
            }
        }
    }

    /**
     * Return the temporary start stop position which may be -1 if it is not set.
     * @return the temporary start stop position as a number.
     */
    getTempStartStop(): number {
        return this.tempStartStopPos;
    }

    /**
     * Return the temporary end stop position which may be -1 if it is not set.
     * @return the temporary end stop position as a number.
     */
    getTempEndStop(): number {
        return this.tempEndStopPos;
    }

}