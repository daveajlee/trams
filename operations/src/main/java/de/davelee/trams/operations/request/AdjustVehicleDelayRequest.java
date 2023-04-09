package de.davelee.trams.operations.request;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a request to adjust the delay of a vehicle in minutes.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AdjustVehicleDelayRequest {

    //company that owns the vehicle
    private String company;

    //fleet number of the vehicle
    private String fleetNumber;

    //delay of the vehicle in minutes (a negative number indicates the delay should be reduced)
    private int delayInMinutes;

}
