package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add an entry to the vehicle's history.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AddHistoryEntryRequest {

    //company that owns the vehicle
    private String company;

    //fleet number of the vehicle
    private String fleetNumber;

    //date that the history entry took place in format (dd-mm-yyyy)
    private String date;

    //reason for the history entry
    private String reason;

    //comment for the history entry
    private String comment;

}
