package de.davelee.trams.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * This class defines a model for a service which runs one entry of a schedule belonging to a route.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class ServiceTrip {

    /**
     * The id of the service.
     */
    private String serviceId;

    /**
     * The route schedule for this service.
     */
    private RouteSchedule routeSchedule;

    /**
     * The list of stops belonging to this service.
     */
    private List<Stop> stopList;

    /**
     * True if and only if this service is out of service and not running.
     */
    private boolean outOfService;

    /**
     * Allow a service to start later to reduce delays etc.
     */
    private int tempStartStopPos;

    /**
     * Allow a service to end before to reduce delays etc.
     */
    private int tempEndStopPos;

}
