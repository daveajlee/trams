package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to assign a vehicle to a particular
 * tour / timetable.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class RemoveVehicleRequest {

    /**
     * The fleet number of this vehicle.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle.
     */
    private String company;

}
