package de.davelee.trams.server.response;

import java.util.Arrays;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of a single route containing route number and company.
 * @author Dave Lee
 */
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

    @Override
    public String toString() {
        return "RouteResponse{" +
                "routeNumber='" + routeNumber + '\'' +
                ", company='" + company + '\'' +
                ", startStop='" + startStop + '\'' +
                ", endStop='" + endStop + '\'' +
                ", stops=" + Arrays.toString(stops) +
                ", nightRoute=" + nightRoute +
                '}';
    }
}
