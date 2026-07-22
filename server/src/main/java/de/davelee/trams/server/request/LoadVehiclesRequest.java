package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to the server containing details
 * of all the vehicles according to be loaded. As well as containing details about the vehicles in form of
 * an array of <code>LoadVehicleRequest</code> objects, the object also contains a simple count of the vehicles.
 * @author Dave Lee
 */
public class LoadVehiclesRequest {

    //a count of the number of vehicles which were found by the server.
    private Long count;

    //an array of all vehicles found by the server.
    private LoadVehicleRequest[] loadVehicleRequests;

    public LoadVehiclesRequest() {
    }

    public LoadVehiclesRequest(Long count, LoadVehicleRequest[] loadVehicleRequests) {
        this.count = count;
        this.loadVehicleRequests = loadVehicleRequests;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public LoadVehicleRequest[] getLoadVehicleRequests() {
        return loadVehicleRequests;
    }

    public void setLoadVehicleRequests(LoadVehicleRequest[] loadVehicleRequests) {
        this.loadVehicleRequests = loadVehicleRequests;
    }

}
