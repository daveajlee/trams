package de.davelee.trams.operations.request;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a request to inspect a vehicle for the particular
 * company fulfilling the details supplied.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class InspectVehicleRequest {

    /**
     * The fleet number of the vehicle to inspect.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle at the moment.
     */
    private String company;


}
