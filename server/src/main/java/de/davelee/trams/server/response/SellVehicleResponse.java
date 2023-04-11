package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to sell a vehicle
 * and contains the price for which the vehicle was sold.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SellVehicleResponse {

    /**
     * Could the vehicle be sold successfully?
     */
    private boolean sold;

    /**
     * The price of the vehicle which may be 0 if the vehicle could not be sold successfully.
     */
    private double soldPrice;

}
