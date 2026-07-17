package de.davelee.trams.server.model;

import java.util.Arrays;

/**
 * This class represents a route. A route can contain an id, a route number and an agency who runs the route on a regular basis.
 * @author Dave Lee
 */
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

    public boolean isNightRoute() {
        return nightRoute;
    }

    public void setNightRoute(boolean nightRoute) {
        this.nightRoute = nightRoute;
    }

    public String[] getStops() {
        return stops;
    }

    public void setStops(String[] stops) {
        this.stops = stops;
    }

    public String getEndStop() {
        return endStop;
    }

    public void setEndStop(String endStop) {
        this.endStop = endStop;
    }

    public String getStartStop() {
        return startStop;
    }

    public void setStartStop(String startStop) {
        this.startStop = startStop;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", routeNumber='" + routeNumber + '\'' +
                ", company='" + company + '\'' +
                ", startStop='" + startStop + '\'' +
                ", endStop='" + endStop + '\'' +
                ", stops=" + Arrays.toString(stops) +
                ", nightRoute=" + nightRoute +
                '}';
    }
}
