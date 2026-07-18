package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add a number of hours to a particular date
 * to a particular vehicle (identified by fleet number and company).
 * @author Dave Lee
 */
public class AddVehicleHoursRequest {

    //company that the vehicle belongs to
    private String company;

    //fleet number of the vehicle
    private String fleetNumber;

    //the date to add the hours to in format dd-MM-yyyy.
    private String date;

    //the number of hours to add
    private int hours;

    public AddVehicleHoursRequest() {
    }

    public AddVehicleHoursRequest(String company, String fleetNumber, String date, int hours) {
        this.company = company;
        this.fleetNumber = fleetNumber;
        this.date = date;
        this.hours = hours;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
