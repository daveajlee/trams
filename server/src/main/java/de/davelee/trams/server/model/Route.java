package de.davelee.trams.server.model;

import lombok.*;

/**
 * This class represents a route. A route can contain an id, a route number and an agency who runs the route on a regular basis.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class Route {

    /**
     * The id of the route.
     */
    private String id;

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
