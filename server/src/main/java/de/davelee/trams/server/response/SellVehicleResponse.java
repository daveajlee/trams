package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to sell a vehicle
 * and contains the price for which the vehicle was sold.
 * @author Dave Lee
 */
public class SellVehicleResponse {

    /**
     * Could the vehicle be sold successfully?
     */
    private boolean sold;

    /**
     * The price of the vehicle which may be 0 if the vehicle could not be sold successfully.
     */
    private double soldPrice;

    public SellVehicleResponse() {
    }

    public SellVehicleResponse(boolean sold, double soldPrice) {
        this.sold = sold;
        this.soldPrice = soldPrice;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }
}
