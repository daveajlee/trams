package de.davelee.trams.server.model;

import java.util.List;

/**
 * This class defines a model for a service which runs one entry of a schedule belonging to a route.
 * @author Dave Lee
 */
public class ServiceTrip {

    /**
     * The id of the service.
     */
    private String serviceId;

    /**
     * The route schedule for this service.
     */
    private RouteSchedule routeSchedule;

    /**
     * The list of stops belonging to this service.
     */
    private List<Stop> stopList;

    /**
     * True if and only if this service is out of service and not running.
     */
    private boolean outOfService;

    /**
     * Allow a service to start later to reduce delays etc.
     */
    private int tempStartStopPos;

    /**
     * Allow a service to end before to reduce delays etc.
     */
    private int tempEndStopPos;

    public ServiceTrip() {
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public RouteSchedule getRouteSchedule() {
        return routeSchedule;
    }

    public void setRouteSchedule(RouteSchedule routeSchedule) {
        this.routeSchedule = routeSchedule;
    }

    public List<Stop> getStopList() {
        return stopList;
    }

    public void setStopList(List<Stop> stopList) {
        this.stopList = stopList;
    }

    public boolean isOutOfService() {
        return outOfService;
    }

    public void setOutOfService(boolean outOfService) {
        this.outOfService = outOfService;
    }

    public int getTempStartStopPos() {
        return tempStartStopPos;
    }

    public void setTempStartStopPos(int tempStartStopPos) {
        this.tempStartStopPos = tempStartStopPos;
    }

    public int getTempEndStopPos() {
        return tempEndStopPos;
    }

    public void setTempEndStopPos(int tempEndStopPos) {
        this.tempEndStopPos = tempEndStopPos;
    }
}
