package de.davelee.trams.operations.model;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;

/**
 * Class to represent a particular entry in the history of a particular vehicle in TraMS Operations.
 * @author Dave Lee
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VehicleHistoryEntry {

    /**
     * A unique id for this history entry.
     */
    private ObjectId id;

    /**
     * The date that this history entry took place.
     */
    private LocalDate date;

    /**
     * The reason for this history entry.
     */
    private VehicleHistoryReason vehicleHistoryReason;

    /**
     * A comment about this history - this could be the reason it was given.
     */
    private String comment;


}
