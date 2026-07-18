package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to calculate the current
 * value of a vehicle and contains the current value of the vehicle as well as the company and fleet number.
 * @author Dave Lee
 */
public class VehicleValueResponse {

    //company that owns the vehicle
    private String company;

    //fleet number of the vehicle
    private String fleetNumber;

    //value of the vehicle
    private double value;

    public VehicleValueResponse() {
    }

    public VehicleValueResponse(String company, String fleetNumber, double value) {
        this.company = company;
        this.fleetNumber = fleetNumber;
        this.value = value;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
