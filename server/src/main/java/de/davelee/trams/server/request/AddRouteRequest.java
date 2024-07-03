package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add a route.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AddRouteRequest {

    /**
     * The number of the route which can contain either alphabetical and alphanumeric characters.
     */
    private String routeNumber;

    /**
     * The agency or company who runs the route - currently only one company can run a particular route.
     */
    private String company;

    /**
     * The start stop of this route.
     */
    private String startStop;

    /**
     * The last stop of this route.
     */
    private String endStop;

    /**
     * The list of intermediate stops served by this route.
     */
    private String[] stops;

    /**
     * A boolean which is true iff this is a night route.
     */
    private boolean nightRoute;

}
