package de.davelee.trams.operations.response;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a response to a request to inspect a vehicle
 * and contains the price for which the vehicle was inspected.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class InspectVehicleResponse {

    /**
     * Could the vehicle be inspected successfully?
     */
    private boolean inspected;

    /**
     * The inspection price of the vehicle which may be 0 if the vehicle could not be inspected successfully.
     */
    private double inspectionPrice;

}
