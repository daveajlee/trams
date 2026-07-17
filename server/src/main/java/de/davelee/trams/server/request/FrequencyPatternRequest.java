package de.davelee.trams.server.request;

import java.util.Arrays;

/**
 * This class is part of the TraMS Server REST API. It represents a request to create
 * a frequency pattern for a particular timetable.
 * @author Dave Lee
 */
public class FrequencyPatternRequest {

    /**
     * The name of this frequency pattern.
     */
    private String name;

    /**
     * The days that this frequency pattern operates.
     */
    private String[] daysOfOperation;

    /**
     * The start stop of this frequency pattern.
     */
    private String startStop;

    /**
     * The end stop of this frequency pattern.
     */
    private String endStop;

    /**
     * The start time of this frequency pattern in the format HH:mm
     */
    private String startTime;

    /**
     * The end time of this frequency pattern in the format HH:mm
     */
    private String endTime;

    /**
     * The frequency in minutes.
     */
    private int frequencyInMinutes;

    /**
     * The number of tours required to run this frequency pattern.
     */
    private int numTours;

    public FrequencyPatternRequest() {
    }

    public FrequencyPatternRequest(String name, String[] daysOfOperation, String startStop, String endStop, String startTime, String endTime, int frequencyInMinutes, int numTours) {
        this.name = name;
        this.daysOfOperation = daysOfOperation;
        this.startStop = startStop;
        this.endStop = endStop;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequencyInMinutes = frequencyInMinutes;
        this.numTours = numTours;
    }

    public int getNumTours() {
        return numTours;
    }

    public void setNumTours(int numTours) {
        this.numTours = numTours;
    }

    public int getFrequencyInMinutes() {
        return frequencyInMinutes;
    }

    public void setFrequencyInMinutes(int frequencyInMinutes) {
        this.frequencyInMinutes = frequencyInMinutes;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndStop() {
        return endStop;
    }

    public void setEndStop(String endStop) {
        this.endStop = endStop;
    }

    public String getStartStop() {
        return startStop;
    }

    public void setStartStop(String startStop) {
        this.startStop = startStop;
    }

    public String[] getDaysOfOperation() {
        return daysOfOperation;
    }

    public void setDaysOfOperation(String[] daysOfOperation) {
        this.daysOfOperation = daysOfOperation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FrequencyPatternRequest{" +
                "name='" + name + '\'' +
                ", daysOfOperation=" + Arrays.toString(daysOfOperation) +
                ", startStop='" + startStop + '\'' +
                ", endStop='" + endStop + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", frequencyInMinutes=" + frequencyInMinutes +
                ", numTours=" + numTours +
                '}';
    }
}
