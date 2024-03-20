import {ServiceModel} from "./service.model";

/**
 * This class defines a model for a schedule belonging to a route.
 */
export class ScheduleModel {

    public routeNumber: string;
    public scheduleId: string;
    public services: ServiceModel[];

    /**
     * Construct a new ScheduleModel object based on the supplied information
     * @param routeNumber the route number that the schedule is for
     * @param scheduleId the id of the schedule
     */
    constructor( routeNumber: string, scheduleId: string ) {
        this.routeNumber = routeNumber;
        this.scheduleId = scheduleId;
        this.services = [];
    }

    /**
     * Add a service to the list of services.
     *
     */
    addService( service: ServiceModel) {
        this.services.push(service);
    }

}