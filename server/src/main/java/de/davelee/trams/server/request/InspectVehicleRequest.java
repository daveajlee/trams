package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to inspect a vehicle for the particular
 * company fulfilling the details supplied.
 * @author Dave Lee
 */
public class InspectVehicleRequest {

    /**
     * The fleet number of the vehicle to inspect.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle at the moment.
     */
    private String company;

    public InspectVehicleRequest() {
    }

    public InspectVehicleRequest(String fleetNumber, String company) {
        this.fleetNumber = fleetNumber;
        this.company = company;
    }

    public String getFleetNumber() {
        return fleetNumber;
    }

    public void setFleetNumber(String fleetNumber) {
        this.fleetNumber = fleetNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
