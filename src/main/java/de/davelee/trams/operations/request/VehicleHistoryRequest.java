package de.davelee.trams.operations.request;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a request to the server for a particular vehicle
 * history entry containing date, reason and comment.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleHistoryRequest {

    /**
     * The date that this history entry took place in format dd-MM-yyyy.
     */
    private String date;

    /**
     * The reason for this history entry.
     */
    private String vehicleHistoryReason;

    /**
     * A comment about this history - this could be the reason it was given.
     */
    private String comment;

}
