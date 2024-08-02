package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched drivers according to specified criteria. As well as containing details about the drivers in form of
 * an array of <code>DriverResponse</code> objects, the object also contains a simple count of the drivers.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DriversResponse {

    //a count of the number of drivers which were found by the server.
    private Long count;

    //an array of all drivers found by the server.
    private DriverResponse[] driverResponses;

}