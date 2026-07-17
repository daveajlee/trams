package de.davelee.trams.server.request;

import java.util.List;
import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add a stop.
 * @author Dave Lee
 */
public class AddStopRequest {

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
     * A list of the other stop names since some programming languages do not support map/key values.
     */
    private List<String> otherStopNames;

    /**
     * The distances between this stop and the other stops. The distance matches the position in the stop names list.
     */
    private List<Integer> otherStopDistances;

    /**
     * The latitude location of the stop which should be in a valid format for a latitude e.g. 50.0200004
     */
    private double latitude;

    /**
     * The longitude location of the stop which should be in a valid format for a longitude e.g. 50.0200004
     */
    private double longitude;

    public AddStopRequest() {
    }

    public AddStopRequest(String name, String company, int waitingTime, List<String> otherStopNames, List<Integer> otherStopDistances, double latitude, double longitude) {
        this.name = name;
        this.company = company;
        this.waitingTime = waitingTime;
        this.otherStopNames = otherStopNames;
        this.otherStopDistances = otherStopDistances;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

    public List<String> getOtherStopNames() {
        return otherStopNames;
    }

    public void setOtherStopNames(List<String> otherStopNames) {
        this.otherStopNames = otherStopNames;
    }

    public List<Integer> getOtherStopDistances() {
        return otherStopDistances;
    }

    public void setOtherStopDistances(List<Integer> otherStopDistances) {
        this.otherStopDistances = otherStopDistances;
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
}
