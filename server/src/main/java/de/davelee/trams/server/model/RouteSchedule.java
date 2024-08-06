package de.davelee.trams.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class defines a model for a schedule belonging to a route.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class RouteSchedule {

    /**
     * The route number that the schedule belongs to.
     */
    private String routeNumber;

    /**
     * The schedule id that the route belongs to.
     */
    private String scheduleId;

    /**
     * Construct a new ScheduleModel object based on the supplied information
     * @param routeNumber the route number that the schedule is for
     * @param scheduleId the id of the schedule
     */
    public RouteSchedule ( final String routeNumber, final String scheduleId ) {
        this.routeNumber = routeNumber;
        this.scheduleId = scheduleId;
    }

    /**
     * Retrieve the route number and schedule id for this schedule.
     * @return the route number and schedule id with a slash between as a String.
     */
    public String getRouteNumberAndScheduleId() {
        return this.routeNumber + "/" + this.scheduleId;
    }

}
