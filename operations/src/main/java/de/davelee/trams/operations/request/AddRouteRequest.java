package de.davelee.trams.operations.request;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a request to add a route.
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

}
