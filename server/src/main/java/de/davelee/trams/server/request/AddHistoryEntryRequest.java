package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add an entry to the vehicle's history.
 * @author Dave Lee
 */
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

    public AddHistoryEntryRequest() {
    }

    public AddHistoryEntryRequest(String company, String fleetNumber, String date, String reason, String comment) {
        this.company = company;
        this.fleetNumber = fleetNumber;
        this.date = date;
        this.reason = reason;
        this.comment = comment;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFleetNumber() {
        return fleetNumber;
    }

    public void setFleetNumber(String fleetNumber) {
        this.fleetNumber = fleetNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
