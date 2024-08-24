package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to change the service information
 * because service should be shortened etc.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ServiceChangeRequest {

    /**
     * Company which services should be reset for
     */
    private String company;

    /**
     * The id of the service.
     */
    private String serviceId;

    /**
     * The id of the route and schedule running the service e.g. 3/1
     */
    private String scheduleId;

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
