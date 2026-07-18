package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to inspect a vehicle
 * and contains the price for which the vehicle was inspected.
 * @author Dave Lee
 */
public class InspectVehicleResponse {

    /**
     * Could the vehicle be inspected successfully?
     */
    private boolean inspected;

    /**
     * The inspection price of the vehicle which may be 0 if the vehicle could not be inspected successfully.
     */
    private double inspectionPrice;

    public InspectVehicleResponse() {
    }

    public InspectVehicleResponse(boolean inspected, double inspectionPrice) {
        this.inspected = inspected;
        this.inspectionPrice = inspectionPrice;
    }

    public boolean isInspected() {
        return inspected;
    }

    public void setInspected(boolean inspected) {
        this.inspected = inspected;
    }

    public double getInspectionPrice() {
        return inspectionPrice;
    }

    public void setInspectionPrice(double inspectionPrice) {
        this.inspectionPrice = inspectionPrice;
    }
}
