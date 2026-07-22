package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add a route.
 * @author Dave Lee
 */
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

    public AddRouteRequest() {
    }

    public AddRouteRequest(String routeNumber, String company, String startStop, String endStop, String[] stops, boolean nightRoute) {
        this.routeNumber = routeNumber;
        this.company = company;
        this.startStop = startStop;
        this.endStop = endStop;
        this.stops = stops;
        this.nightRoute = nightRoute;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartStop() {
        return startStop;
    }

    public void setStartStop(String startStop) {
        this.startStop = startStop;
    }

    public String getEndStop() {
        return endStop;
    }

    public void setEndStop(String endStop) {
        this.endStop = endStop;
    }

    public String[] getStops() {
        return stops;
    }

    public void setStops(String[] stops) {
        this.stops = stops;
    }

    public boolean isNightRoute() {
        return nightRoute;
    }

    public void setNightRoute(boolean nightRoute) {
        this.nightRoute = nightRoute;
    }
}
