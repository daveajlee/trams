package de.davelee.trams.server.model;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * This class represents a timetable.
 * A timetable contains a set of frequency patterns.
 * @author Dave Lee
 */
public class Timetable {

    /**
     * The id of the timetable in the database.
     */
    private String id;

    /**
     * The name of this timetable.
     */
    private String name;

    /**
     * The company that this timetable belongs to.
     */
    private String company;

    /**
     * The route number that this timetable belongs to.
     */
    private String routeNumber;

    /**
     * The date that this timetable is valid from.
     */
    private LocalDateTime validFromDate;

    /**
     * The date that this timetable is valid to.
     */
    private LocalDateTime validToDate;

    /**
     * The frequency patterns belonging to this timetable.
     */
    private FrequencyPattern[] frequencyPatterns;

    public Timetable() {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public LocalDateTime getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(LocalDateTime validFromDate) {
        this.validFromDate = validFromDate;
    }

    public LocalDateTime getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(LocalDateTime validToDate) {
        this.validToDate = validToDate;
    }

    public FrequencyPattern[] getFrequencyPatterns() {
        return frequencyPatterns;
    }

    public void setFrequencyPatterns(FrequencyPattern[] frequencyPatterns) {
        this.frequencyPatterns = frequencyPatterns;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", routeNumber='" + routeNumber + '\'' +
                ", validFromDate=" + validFromDate +
                ", validToDate=" + validToDate +
                ", frequencyPatterns=" + Arrays.toString(frequencyPatterns) +
                '}';
    }
}
