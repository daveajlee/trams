package de.davelee.trams.server.response;

import java.util.Arrays;

/**
 * This class is part of the TraMS Server REST API. It represents a response with
 * a timetable for a particular route and company.
 * @author Dave Lee
 */
public class TimetableResponse {

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
     * The date that this timetable is valid from in format dd-MM-yyyy HH:mm
     */
    private String validFromDate;

    /**
     * The date that this timetable is valid to in format dd-MM-yyyy HH:mm
     */
    private String validToDate;

    /**
     * The frequency patterns belonging to this timetable.
     */
    private FrequencyPatternResponse[] frequencyPatterns;

    public TimetableResponse() {
    }

    public TimetableResponse(String name, String company, String routeNumber, String validFromDate, String validToDate, FrequencyPatternResponse[] frequencyPatterns) {
        this.name = name;
        this.company = company;
        this.routeNumber = routeNumber;
        this.validFromDate = validFromDate;
        this.validToDate = validToDate;
        this.frequencyPatterns = frequencyPatterns;
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

    public FrequencyPatternResponse[] getFrequencyPatterns() {
        return frequencyPatterns;
    }

    public void setFrequencyPatterns(FrequencyPatternResponse[] frequencyPatterns) {
        this.frequencyPatterns = frequencyPatterns;
    }

    @Override
    public String toString() {
        return "TimetableResponse{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", routeNumber='" + routeNumber + '\'' +
                ", validFromDate='" + validFromDate + '\'' +
                ", validToDate='" + validToDate + '\'' +
                ", frequencyPatterns=" + Arrays.toString(frequencyPatterns) +
                '}';
    }
}
