package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to the server for a particular vehicle
 * history entry containing date, reason and comment.
 * @author Dave Lee
 */
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

    public VehicleHistoryRequest() {
    }

    public VehicleHistoryRequest(String date, String vehicleHistoryReason, String comment) {
        this.date = date;
        this.vehicleHistoryReason = vehicleHistoryReason;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVehicleHistoryReason() {
        return vehicleHistoryReason;
    }

    public void setVehicleHistoryReason(String vehicleHistoryReason) {
        this.vehicleHistoryReason = vehicleHistoryReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
