package de.davelee.trams.server.request;

import java.util.Arrays;

/**
 * This class is part of the TraMS Server REST API. It represents a request to generate stop times automatically.
 * @author Dave Lee
 */
public class GenerateStopTimesRequest {

    /**
     * The company that should run these stop times.
     */
    private String company;

    /**
     * The stops that should be served in an array in the order that they should be served.
     */
    private String[] stopNames;

    /**
     * The route number serving these stops.
     */
    private String routeNumber;

    /**
     * The start time from when stop times should be generated in the format HH:mm.
     */
    private String startTime;

    /**
     * The end time until when stop times should be generated in the format HH:mm.
     */
    private String endTime;

    /**
     * The start stop to generate stop times from.
     */
    private String startStop;

    /**
     * The end stop to generate stop times until.
     */
    private String endStop;

    /**
     * The frequency in which stops times should be generated in minutes. Minimum value is 1.
     */
    private int frequency;

    /**
     * The number of tours that should be generated.
     */
    private int numTours;

    /**
     * The valid from date with the date from which stop times are valid. The date is inclusive.
     */
    private String validFromDate;

    /**
     * The valid to date until when stop times are valid. The date is inclusive.
     */
    private String validToDate;

    /**
     * The days when these stop times run.
     */
    private String operatingDays;

    /**
     * The distances between stops in the format stopName:distance,distance,distance per stop as one entry.
     */
    private String[] stopDistances;

    public GenerateStopTimesRequest() {
    }

    public GenerateStopTimesRequest(String company, String[] stopNames, String routeNumber, String startTime, String endTime, String startStop, String endStop, int frequency, int numTours, String validFromDate, String validToDate, String operatingDays, String[] stopDistances) {
        this.company = company;
        this.stopNames = stopNames;
        this.routeNumber = routeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startStop = startStop;
        this.endStop = endStop;
        this.frequency = frequency;
        this.numTours = numTours;
        this.validFromDate = validFromDate;
        this.validToDate = validToDate;
        this.operatingDays = operatingDays;
        this.stopDistances = stopDistances;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String[] getStopNames() {
        return stopNames;
    }

    public void setStopNames(String[] stopNames) {
        this.stopNames = stopNames;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartStop() {
        return startStop;
    }

    public void setStartStop(String startStop) {
        this.startStop = startStop;
    }

    public String getEndStop() {
        return endStop;
    }

    public void setEndStop(String endStop) {
        this.endStop = endStop;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getNumTours() {
        return numTours;
    }

    public void setNumTours(int numTours) {
        this.numTours = numTours;
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

    public String getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(String operatingDays) {
        this.operatingDays = operatingDays;
    }

    public String[] getStopDistances() {
        return stopDistances;
    }

    public void setStopDistances(String[] stopDistances) {
        this.stopDistances = stopDistances;
    }

    @Override
    public String toString() {
        return "GenerateStopTimesRequest{" +
                "company='" + company + '\'' +
                ", stopNames=" + Arrays.toString(stopNames) +
                ", routeNumber='" + routeNumber + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startStop='" + startStop + '\'' +
                ", endStop='" + endStop + '\'' +
                ", frequency=" + frequency +
                ", numTours=" + numTours +
                ", validFromDate='" + validFromDate + '\'' +
                ", validToDate='" + validToDate + '\'' +
                ", operatingDays='" + operatingDays + '\'' +
                ", stopDistances=" + Arrays.toString(stopDistances) +
                '}';
    }
}
