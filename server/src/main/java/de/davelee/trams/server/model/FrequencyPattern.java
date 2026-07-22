package de.davelee.trams.server.model;

import java.time.LocalTime;

/**
 * This class represents a frequency pattern.
 * A frequency pattern contains information about the frequency between stops on particular days.
 * @author Dave Lee
 */
public class FrequencyPattern {

    /**
     * The id of the frequency pattern in the database.
     */
    private String id;

    /**
     * The name of this frequency pattern.
     */
    private String name;

    /**
     * The days that this frequency pattern operates.
     */
    private OperatingDays daysOfOperation;

    /**
     * The start stop of this frequency pattern.
     */
    private String startStop;

    /**
     * The end stop of this frequency pattern.
     */
    private String endStop;

    /**
     * The start time of this frequency pattern.
     */
    private LocalTime startTime;

    /**
     * The end time of this frequency pattern.
     */
    private LocalTime endTime;

    /**
     * The frequency in minutes.
     */
    private int frequencyInMinutes;

    /**
     * The number of tours required to run this frequency pattern.
     */
    private int numTours;

    public FrequencyPattern() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OperatingDays getDaysOfOperation() {
        return daysOfOperation;
    }

    public void setDaysOfOperation(OperatingDays daysOfOperation) {
        this.daysOfOperation = daysOfOperation;
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getFrequencyInMinutes() {
        return frequencyInMinutes;
    }

    public void setFrequencyInMinutes(int frequencyInMinutes) {
        this.frequencyInMinutes = frequencyInMinutes;
    }

    public int getNumTours() {
        return numTours;
    }

    public void setNumTours(int numTours) {
        this.numTours = numTours;
    }

    @Override
    public String toString() {
        return "FrequencyPattern{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", daysOfOperation=" + daysOfOperation +
                ", startStop='" + startStop + '\'' +
                ", endStop='" + endStop + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", frequencyInMinutes=" + frequencyInMinutes +
                ", numTours=" + numTours +
                '}';
    }
}
