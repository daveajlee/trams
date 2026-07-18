package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server for the number of hours
 * of a particular vehicle.
 * @author Dave Lee
 */
public class VehicleHoursResponse {

    /**
     * The number of hours that the vehicle has been in service for the specified date.
     */
    private int numberOfHoursSoFar;

    /**
     * The number of hours that the vehicle may still serve for the specified date.
     */
    private int numberOfHoursAvailable;

    /**
     * Whether or not the maximum number of hours has already been reached.
     */
    private boolean maximumHoursReached;

    public VehicleHoursResponse() {
    }

    public VehicleHoursResponse(int numberOfHoursSoFar, int numberOfHoursAvailable, boolean maximumHoursReached) {
        this.numberOfHoursSoFar = numberOfHoursSoFar;
        this.numberOfHoursAvailable = numberOfHoursAvailable;
        this.maximumHoursReached = maximumHoursReached;
    }

    public int getNumberOfHoursSoFar() {
        return numberOfHoursSoFar;
    }

    public void setNumberOfHoursSoFar(int numberOfHoursSoFar) {
        this.numberOfHoursSoFar = numberOfHoursSoFar;
    }

    public int getNumberOfHoursAvailable() {
        return numberOfHoursAvailable;
    }

    public void setNumberOfHoursAvailable(int numberOfHoursAvailable) {
        this.numberOfHoursAvailable = numberOfHoursAvailable;
    }

    public boolean isMaximumHoursReached() {
        return maximumHoursReached;
    }

    public void setMaximumHoursReached(boolean maximumHoursReached) {
        this.maximumHoursReached = maximumHoursReached;
    }

    @Override
    public String toString() {
        return "VehicleHoursResponse{" +
                "numberOfHoursSoFar=" + numberOfHoursSoFar +
                ", numberOfHoursAvailable=" + numberOfHoursAvailable +
                ", maximumHoursReached=" + maximumHoursReached +
                '}';
    }
}
