package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server for a particular vehicle
 * history entry containing date, reason and comment.
 * @author Dave Lee
 */
public class VehicleHistoryResponse {

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

    public VehicleHistoryResponse() {
    }

    public VehicleHistoryResponse(String date, String vehicleHistoryReason, String comment) {
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

    @Override
    public String toString() {
        return "VehicleHistoryResponse{" +
                "date='" + date + '\'' +
                ", vehicleHistoryReason='" + vehicleHistoryReason + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
