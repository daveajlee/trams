import {StopTimeModel} from "./stoptime.model";

/**
 * This class defines a model for a service belonging to a schedule.
 */
export class ServiceModel {

    public serviceId: string;
    public stopList: StopTimeModel[];

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

}