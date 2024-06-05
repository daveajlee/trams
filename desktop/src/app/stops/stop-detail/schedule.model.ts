import {ServiceModel} from "./service.model";

/**
 * This class defines a model for a schedule belonging to a route.
 */
export class ScheduleModel {

    private routeNumber: string;
    private scheduleId: string;
    private services: ServiceModel[];

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

    /**
     * Retrieve the route number and schedule id for this schedule.
     * @return the route number and scheudule id with a slash between as a String.
     */
    getRouteNumberAndScheduleId(): string {
        return this.routeNumber + "/" + this.scheduleId;
    }

    /**
     * Retrieve the route number and schedule id for this schedule.
     * @return the route number and scheudule id with a colon between as a String.
     */
    getRouteNumberAndScheduleIdWithColon(): string {
        return this.routeNumber + ":" + this.scheduleId;
    }

    /**
     * Get the list of services that the schedule has.
     * @return the list of services as an array of ServiceModel objects.
     */
    getServices(): ServiceModel[] {
        return this.services;
    }

}