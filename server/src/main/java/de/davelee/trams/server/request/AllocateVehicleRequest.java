package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to allocate a vehicle to a particular
 * tour / timetable.
 * @author Dave Lee
 */
public class AllocateVehicleRequest {

    /**
     * The fleet number of this vehicle.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle.
     */
    private String company;

    /**
     * The allocated route for this vehicle.
     */
    private String allocatedRoute;

    /**
     * The allocated tour for this vehicle.
     */
    private String allocatedTour;

    public AllocateVehicleRequest() {
    }

    public AllocateVehicleRequest(String fleetNumber, String company, String allocatedRoute, String allocatedTour) {
        this.fleetNumber = fleetNumber;
        this.company = company;
        this.allocatedRoute = allocatedRoute;
        this.allocatedTour = allocatedTour;
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

    public String getAllocatedRoute() {
        return allocatedRoute;
    }

    public void setAllocatedRoute(String allocatedRoute) {
        this.allocatedRoute = allocatedRoute;
    }

    public String getAllocatedTour() {
        return allocatedTour;
    }

    public void setAllocatedTour(String allocatedTour) {
        this.allocatedTour = allocatedTour;
    }
}
