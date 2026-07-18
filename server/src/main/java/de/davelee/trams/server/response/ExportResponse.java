package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to export all routes
 * and vehicles.
 * @author Dave Lee
 */
public class ExportResponse {

    private RouteResponse[] routeResponses;

    private VehicleResponse[] vehicleResponses;

    public ExportResponse() {
    }

    public ExportResponse(RouteResponse[] routeResponses, VehicleResponse[] vehicleResponses) {
        this.routeResponses = routeResponses;
        this.vehicleResponses = vehicleResponses;
    }

    public RouteResponse[] getRouteResponses() {
        return routeResponses;
    }

    public void setRouteResponses(RouteResponse[] routeResponses) {
        this.routeResponses = routeResponses;
    }

    public VehicleResponse[] getVehicleResponses() {
        return vehicleResponses;
    }

    public void setVehicleResponses(VehicleResponse[] vehicleResponses) {
        this.vehicleResponses = vehicleResponses;
    }
}
