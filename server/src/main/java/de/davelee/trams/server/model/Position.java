package de.davelee.trams.server.model;

/**
 * This class represents a position where a vehicle / tour is currently at.
 * @author Dave Lee
 */
public class Position {

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
    private ServiceTrip service;

    public Position() {
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

    public ServiceTrip getService() {
        return service;
    }

    public void setService(ServiceTrip service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Position{" +
                "company='" + company + '\'' +
                ", stop='" + stop + '\'' +
                ", destination='" + destination + '\'' +
                ", delay=" + delay +
                ", service=" + service +
                '}';
    }
}
