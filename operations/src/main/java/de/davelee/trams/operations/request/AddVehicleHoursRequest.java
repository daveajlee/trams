package de.davelee.trams.operations.request;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a request to add a number of hours to a particular date
 * to a particular vehicle (identified by fleet number and company).
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AddVehicleHoursRequest {

    //company that the vehicle belongs to
    private String company;

    //fleet number of the vehicle
    private String fleetNumber;

    //the date to add the hours to in format dd-MM-yyyy.
    private String date;

    //the number of hours to add
    private int hours;

}
