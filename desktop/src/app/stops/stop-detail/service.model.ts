import {StopTimeModel} from "./stoptime.model";

/**
 * This class defines a model for a service belonging to a schedule.
 */
export class ServiceModel {

    private serviceId: string;
    private stopList: StopTimeModel[];
    private outOfService: boolean;

    /**
     * Construct a new ServiceModel object based on the supplied information
     * @param serviceId the id of the service
     */
    constructor( serviceId: string ) {
        this.serviceId = serviceId;
        this.stopList = [];
        this.outOfService = false;
    }

    /**
     * Add departure, arrival and stop name for a particular stop to the service.
     * @param departureTime the time that the vehicle will depart
     * @param arrivalTime the time that the vehicle will arrive
     * @param stop the name of the stop.
     */
    addStop(departureTime: string, arrivalTime: string, stop: string) {
        this.stopList.push(new StopTimeModel(departureTime, arrivalTime, stop));
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

}