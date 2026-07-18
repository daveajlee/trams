package de.davelee.trams.server.response;

import java.util.Arrays;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing
 * the service trip matched to the current position returned from the server.
 * @author Dave Lee
 */
public class ServiceTripResponse {

    /**
     * The id of the service.
     */
    private String serviceId;

    /**
     * The id of the route and schedule running the service e.g. 3/1
     */
    private String scheduleId;

    /**
     * The list of stops served by this service.
     */
    private String[] stopList;

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

    public ServiceTripResponse() {
    }

    public ServiceTripResponse(String serviceId, String scheduleId, String[] stopList, boolean outOfService, int tempStartStopPos, int tempEndStopPos) {
        this.serviceId = serviceId;
        this.scheduleId = scheduleId;
        this.stopList = stopList;
        this.outOfService = outOfService;
        this.tempStartStopPos = tempStartStopPos;
        this.tempEndStopPos = tempEndStopPos;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String[] getStopList() {
        return stopList;
    }

    public void setStopList(String[] stopList) {
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

    @Override
    public String toString() {
        return "ServiceTripResponse{" +
                "serviceId='" + serviceId + '\'' +
                ", scheduleId='" + scheduleId + '\'' +
                ", stopList=" + Arrays.toString(stopList) +
                ", outOfService=" + outOfService +
                ", tempStartStopPos=" + tempStartStopPos +
                ", tempEndStopPos=" + tempEndStopPos +
                '}';
    }
}
