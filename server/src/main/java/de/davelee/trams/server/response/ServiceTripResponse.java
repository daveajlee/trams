package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing
 * the service trip matched to the current position returned from the server.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceTripResponse {

    /**
     * The id of the service.
     */
    private String serviceId;

    /**
     * The id of the route and schedule running the service e.g. 3/1
     */
    private String scheduleId;

    /**
     * The list of stops served by this service.
     */
    private String[] stopList;

    /**
     * A boolean which is true iff the service is out of service i.e. not running.
     */
    private boolean outOfService;

    /**
     * Allow a service to start after normal stop to reduce delays etc.
     */
    private int tempStartStopPos;

    /**
     * Allow a service to end before normal stop to reduce delays etc.
     */
    private int tempEndStopPos;

}
