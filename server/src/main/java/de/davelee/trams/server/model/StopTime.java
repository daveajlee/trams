package de.davelee.trams.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class represents a departure and/or arrival at a particular stop. A stop time can contain an id, a name, an arrival and/or departure time, a destination,
 * the number of the route, the date from which this stop occurs (inclusive), the date until which this stop occurs (inclusive), the days on which this stop
 * takes place and the journey number.
 * @author Dave Lee
 */
@Document
public class StopTime {

    /**
     * The id of the stop time.
     */
    @Id
    private BigInteger id;

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
    private LocalTime arrivalTime;

    /**
     * The departure time when the journey will depart which may be null if journey ends here.
     */
    private LocalTime departureTime;

    /**
     * The destination of this journey which may be equal to the stop name if the journey ends here.
     */
    private String destination;

    /**
     * The number of the route which this journey is a part of.
     */
    private String routeNumber;

    /**
     * The service for this journey,
     */
    private ServiceTrip service;

    /**
     * The date from which this stop occurs (inclusive).
     */
    private LocalDateTime validFromDate;

    /**
     * The date until which this stop occurs (inclusive).
     */
    private LocalDateTime validToDate;

    /**
     * The operating days on which this stop takes place.
     */
    private OperatingDays operatingDays;

    /**
     * The number of the journey which can contain both alphanumeric and alphabetical characters.
     */
    private String journeyNumber;

    /**
     * A footnote which should be displayed as part of the stop time e.g. that the services continues further.
     */
    private String footnote;

    /**
     * Return the stop time based on the desired type which can either be Departure to return departure time or Arrival to return arrival time.
     * @param type a <code>String</code> with the type of stop times which can be either Departure or Arrival.
     * @return a <code>LocalTime</code> object containing the stop time.
     */
    public LocalTime getTime ( final String type ) {
        if ( type.contentEquals("Departure") ) {
            return getDepartureTime();
        } else {
            return getArrivalTime();
        }
    }

    public StopTime() {
    }

    public StopTime(String stopName, String company, LocalTime arrivalTime, LocalTime departureTime, String destination, String routeNumber, LocalDateTime validFromDate, LocalDateTime validToDate, OperatingDays operatingDays, String journeyNumber, String footnote) {
        this.stopName = stopName;
        this.company = company;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.destination = destination;
        this.routeNumber = routeNumber;
        this.validFromDate = validFromDate;
        this.validToDate = validToDate;
        this.operatingDays = operatingDays;
        this.journeyNumber = journeyNumber;
        this.footnote = footnote;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

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

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
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

    public ServiceTrip getService() {
        return service;
    }

    public void setService(ServiceTrip service) {
        this.service = service;
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

    public OperatingDays getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(OperatingDays operatingDays) {
        this.operatingDays = operatingDays;
    }

    public String getJourneyNumber() {
        return journeyNumber;
    }

    public void setJourneyNumber(String journeyNumber) {
        this.journeyNumber = journeyNumber;
    }

    public String getFootnote() {
        return footnote;
    }

    public void setFootnote(String footnote) {
        this.footnote = footnote;
    }

    @Override
    public String toString() {
        return "StopTime{" +
                "id=" + id +
                ", stopName='" + stopName + '\'' +
                ", company='" + company + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                ", destination='" + destination + '\'' +
                ", routeNumber='" + routeNumber + '\'' +
                ", service=" + service +
                ", validFromDate=" + validFromDate +
                ", validToDate=" + validToDate +
                ", operatingDays=" + operatingDays +
                ", journeyNumber='" + journeyNumber + '\'' +
                ", footnote='" + footnote + '\'' +
                '}';
    }
}
