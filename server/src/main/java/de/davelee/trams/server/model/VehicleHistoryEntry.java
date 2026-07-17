package de.davelee.trams.server.model;

import de.davelee.trams.server.constant.VehicleHistoryReason;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class to represent a particular entry in the history of a particular vehicle in TraMS Server.
 * @author Dave Lee
 */
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

    public VehicleHistoryEntry() {
    }

    public VehicleHistoryEntry(ObjectId id, LocalDateTime date, VehicleHistoryReason vehicleHistoryReason, String comment) {
        this.id = id;
        this.date = date;
        this.vehicleHistoryReason = vehicleHistoryReason;
        this.comment = comment;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public VehicleHistoryReason getVehicleHistoryReason() {
        return vehicleHistoryReason;
    }

    public void setVehicleHistoryReason(VehicleHistoryReason vehicleHistoryReason) {
        this.vehicleHistoryReason = vehicleHistoryReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
