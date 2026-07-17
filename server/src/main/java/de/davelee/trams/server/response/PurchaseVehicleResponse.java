package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to purchase a vehicle
 * and contains the price for which the vehicle was purchased.
 * @author Dave Lee
 */
public class PurchaseVehicleResponse {

    /**
     * Could the vehicle be purchased successfully?
     */
    private boolean purchased;

    /**
     * The price of the vehicle which may be 0 if the vehicle could not be purchased successfully.
     */
    private double purchasePrice;

    public PurchaseVehicleResponse() {
    }

    public PurchaseVehicleResponse(boolean purchased, double purchasePrice) {
        this.purchased = purchased;
        this.purchasePrice = purchasePrice;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
