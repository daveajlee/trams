package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched routes according to specified criteria. As well as containing details about the routes in form of
 * an array of <code>RouteResponse</code> objects, the object also contains a simple count of the routes.
 * @author Dave Lee
 */
public class RoutesResponse {

    //a count of the number of routes which were found by the server.
    private Long count;

    //an array of all routes found by the server.
    private RouteResponse[] routeResponses;

    public RoutesResponse() {
    }

    public RoutesResponse(Long count, RouteResponse[] routeResponses) {
        this.count = count;
        this.routeResponses = routeResponses;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public RouteResponse[] getRouteResponses() {
        return routeResponses;
    }

    public void setRouteResponses(RouteResponse[] routeResponses) {
        this.routeResponses = routeResponses;
    }
}
