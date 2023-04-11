package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to purchase a vehicle
 * and contains the price for which the vehicle was purchased.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PurchaseVehicleResponse {

    /**
     * Could the vehicle be purchased successfully?
     */
    private boolean purchased;

    /**
     * The price of the vehicle which may be 0 if the vehicle could not be purchased successfully.
     */
    private double purchasePrice;

}
