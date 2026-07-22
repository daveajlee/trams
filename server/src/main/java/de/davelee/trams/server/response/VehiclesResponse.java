package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched vehicles according to specified criteria. As well as containing details about the vehicles in form of
 * an array of <code>VehicleResponse</code> objects, the object also contains a simple count of the vehicles.
 * @author Dave Lee
 */
public class VehiclesResponse {

    //a count of the number of vehicles which were found by the server.
    private Long count;

    //an array of all vehicles found by the server.
    private VehicleResponse[] vehicleResponses;

    public VehiclesResponse() {
    }

    public VehiclesResponse(Long count, VehicleResponse[] vehicleResponses) {
        this.count = count;
        this.vehicleResponses = vehicleResponses;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public VehicleResponse[] getVehicleResponses() {
        return vehicleResponses;
    }

    public void setVehicleResponses(VehicleResponse[] vehicleResponses) {
        this.vehicleResponses = vehicleResponses;
    }
}
