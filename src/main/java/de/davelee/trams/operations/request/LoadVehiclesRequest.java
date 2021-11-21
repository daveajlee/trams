package de.davelee.trams.operations.request;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a request to the server containing details
 * of all the vehicles according to be loaded. As well as containing details about the vehicles in form of
 * an array of <code>LoadVehicleRequest</code> objects, the object also contains a simple count of the vehicles.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoadVehiclesRequest {

    //a count of the number of vehicles which were found by the server.
    private Long count;

    //an array of all vehicles found by the server.
    private LoadVehicleRequest[] loadVehicleRequests;

}
