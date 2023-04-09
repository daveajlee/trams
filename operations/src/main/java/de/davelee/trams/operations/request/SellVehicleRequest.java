package de.davelee.trams.operations.request;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a request to sell a vehicle for the particular
 * company fulfilling the details supplied.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SellVehicleRequest {

    /**
     * The fleet number of the vehicle to sell.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle at the moment.
     */
    private String company;

}
