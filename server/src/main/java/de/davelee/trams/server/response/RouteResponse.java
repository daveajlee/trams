package de.davelee.trams.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of a single route containing route number and company.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class RouteResponse {

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
