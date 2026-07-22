package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to adjust the delay of a vehicle
 * and contains the current delay of the vehicle in minutes.
 * @author Dave Lee
 */
public class VehicleDelayResponse {

    //company that owns the vehicle
    private String company;

    //fleet number of the vehicle
    private String fleetNumber;

    //delay of the vehicle in minutes (must be 0 or greater)
    private int delayInMinutes;

    public VehicleDelayResponse() {
    }

    public VehicleDelayResponse(String company, String fleetNumber, int delayInMinutes) {
        this.company = company;
        this.fleetNumber = fleetNumber;
        this.delayInMinutes = delayInMinutes;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFleetNumber() {
        return fleetNumber;
    }

    public void setFleetNumber(String fleetNumber) {
        this.fleetNumber = fleetNumber;
    }

    public int getDelayInMinutes() {
        return delayInMinutes;
    }

    public void setDelayInMinutes(int delayInMinutes) {
        this.delayInMinutes = delayInMinutes;
    }
}
