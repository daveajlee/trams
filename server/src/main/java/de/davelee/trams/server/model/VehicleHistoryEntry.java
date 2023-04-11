package de.davelee.trams.server.model;

import de.davelee.trams.server.constant.VehicleHistoryReason;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class to represent a particular entry in the history of a particular vehicle in TraMS Server.
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
    private LocalDateTime date;

    /**
     * The reason for this history entry.
     */
    private VehicleHistoryReason vehicleHistoryReason;

    /**
     * A comment about this history - this could be the reason it was given.
     */
    private String comment;


}
