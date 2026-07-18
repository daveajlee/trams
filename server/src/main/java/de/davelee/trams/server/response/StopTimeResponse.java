package de.davelee.trams.server.response;

import java.util.List;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of a single stop time with departure and arrival information for a stop in a particular direction.
 * @author Dave Lee
 */
public class StopTimeResponse {

    /**
     * The name of the stop where the journey will arrive or depart.
     */
    private String stopName;

    /**
     * The name of the company operating this journey.
     */
    private String company;

    /**
     * The arrival time when the journey will arrive which may be null if journey starts here.
     */
    private String arrivalTime;

    /**
     * The departure time when the journey will depart which may be null if journey ends here.
     */
    private String departureTime;

    /**
     * The destination of this journey which may be equal to the stop name if the journey ends here.
     */
    private String destination;

    /**
     * The number of the route which this journey is a part of.
     */
    private String routeNumber;

    /**
     * The schedule number for this journey.
     */
    private int scheduleNumber;

    /**
     * The date from which this stop occurs (inclusive).
     */
    private String validFromDate;

    /**
     * The date until which this stop occurs (inclusive).
     */
    private String validToDate;

    /**
     * The days on which this stop takes place.
     */
    private List<String> operatingDays;

    /**
     * The number of the journey which can contain both alphanumeric and alphabetical characters.
     */
    private String journeyNumber;

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public int getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(int scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public String getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(String validFromDate) {
        this.validFromDate = validFromDate;
    }

    public String getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(String validToDate) {
        this.validToDate = validToDate;
    }

    public List<String> getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(List<String> operatingDays) {
        this.operatingDays = operatingDays;
    }

    public String getJourneyNumber() {
        return journeyNumber;
    }

    public void setJourneyNumber(String journeyNumber) {
        this.journeyNumber = journeyNumber;
    }

    @Override
    public String toString() {
        return "StopTimeResponse{" +
                "stopName='" + stopName + '\'' +
                ", company='" + company + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", destination='" + destination + '\'' +
                ", routeNumber='" + routeNumber + '\'' +
                ", scheduleNumber=" + scheduleNumber +
                ", validFromDate='" + validFromDate + '\'' +
                ", validToDate='" + validToDate + '\'' +
                ", operatingDays=" + operatingDays +
                ", journeyNumber='" + journeyNumber + '\'' +
                '}';
    }
}
