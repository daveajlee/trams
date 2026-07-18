package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to change the service information
 * because service should be shortened etc.
 * @author Dave Lee
 */
public class ServiceChangeRequest {

    /**
     * Company which services should be reset for
     */
    private String company;

    /**
     * The id of the service.
     */
    private String serviceId;

    /**
     * The id of the route and schedule running the service e.g. 3/1
     */
    private String scheduleId;

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

    public ServiceChangeRequest() {
    }

    public ServiceChangeRequest(String company, String serviceId, String scheduleId, boolean outOfService, int tempStartStopPos, int tempEndStopPos) {
        this.company = company;
        this.serviceId = serviceId;
        this.scheduleId = scheduleId;
        this.outOfService = outOfService;
        this.tempStartStopPos = tempStartStopPos;
        this.tempEndStopPos = tempEndStopPos;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
