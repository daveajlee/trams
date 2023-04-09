package de.davelee.trams.operations.response;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a response to a request to calculate the current
 * value of a vehicle and contains the current value of the vehicle as well as the company and fleet number.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class VehicleValueResponse {

    //company that owns the vehicle
    private String company;

    //fleet number of the vehicle
    private String fleetNumber;

    //value of the vehicle
    private double value;

}
