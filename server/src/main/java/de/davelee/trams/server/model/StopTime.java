package de.davelee.trams.server.model;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
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

}
