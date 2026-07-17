package de.davelee.trams.server.response;

import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of a single stop containing name, company and location information.
 * @author Dave Lee
 */
public class StopResponse {

    /**
     * The name of the stop.
     */
    private String name;

    /**
     * The name of the company serving this stop.
     */
    private String company;

    /**
     * The waiting time at the stop for a vehicle.
     */
    private int waitingTime;

    /**
     * The distances between this stop and other stops as key/value pair with stop name and distance in minutes.
     */
    private Map<String, Integer> distances;

    /**
     * The latitude location of the stop which should be in a valid format for a latitude e.g. 50.0200004
     */
    private double latitude;

    /**
     * The longitude location of the stop which should be in a valid format for a longitude e.g. 50.0200004
     */
    private double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Map<String, Integer> getDistances() {
        return distances;
    }

    public void setDistances(Map<String, Integer> distances) {
        this.distances = distances;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "StopResponse{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", waitingTime=" + waitingTime +
                ", distances=" + distances +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
