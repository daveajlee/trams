import {StopTimeModel} from "./stoptime.model";

/**
 * This class defines a model for a service belonging to a schedule.
 */
export class ServiceModel {

    private serviceId: string;
    private stopList: StopTimeModel[];

    /**
     * Construct a new ServiceModel object based on the supplied information
     * @param serviceId the id of the service
     */
    constructor( serviceId: string ) {
        this.serviceId = serviceId;
        this.stopList = [];
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
     * Retrieve the list of stops served by this service.
     * @return the list of stops as an array of StopTimeModel objects.
     */
    getStopList(): StopTimeModel[] {
        return this.stopList;
    }

}