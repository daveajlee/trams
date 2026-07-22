package de.davelee.trams.server.response;

import de.davelee.trams.server.model.ServiceTrip;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing
 * the current position returned from the server.
 * @author Dave Lee
 */
public class PositionResponse {

    /**
     * The company running the tour.
     */
    private String company;

    /**
     * The current stop that the tour is at.
     */
    private String stop;

    /**
     * The destination that the tour is heading to.
     */
    private String destination;

    /**
     * The current delay of the vehicle.
     */
    private int delay;

    /**
     * The service that is running this journey.
     */
    private ServiceTripResponse service;

    public PositionResponse() {
    }

    public PositionResponse(String company, String stop, String destination, int delay, ServiceTripResponse service) {
        this.company = company;
        this.stop = stop;
        this.destination = destination;
        this.delay = delay;
        this.service = service;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public ServiceTripResponse getService() {
        return service;
    }

    public void setService(ServiceTripResponse service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "PositionResponse{" +
                "company='" + company + '\'' +
                ", stop='" + stop + '\'' +
                ", destination='" + destination + '\'' +
                ", delay=" + delay +
                ", service=" + service +
                '}';
    }
}
