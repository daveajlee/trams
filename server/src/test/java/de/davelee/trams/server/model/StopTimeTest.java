package de.davelee.trams.server.model;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the StopTime class and ensures that its works correctly.
 * @author Dave Lee
 */
public class StopTimeTest {

    /**
     * Ensure that a StopTime class can be correctly instantiated.
     */
    @Test
    public void testBuilderGetterSetterToString ( ) {
        OperatingDays operatingDays = new OperatingDays();
        operatingDays.setOperatingDays(Arrays.asList(DayOfWeek.MONDAY));
        operatingDays.setSpecialOperatingDays(Arrays.asList(LocalDateTime.of(2020,12,25,0,0)));
        //Test stop time constructor.
        StopTime stopTime = new StopTime();
        stopTime.setArrivalTime(LocalTime.of(19, 46));
        stopTime.setCompany("Mustermann Bus GmbH");
        stopTime.setDepartureTime(LocalTime.of(19,48));
        stopTime.setDestination("Greenfield");
        stopTime.setFootnote("Continues as 405B to Brownfield");
        stopTime.setJourneyNumber("123");
        stopTime.setOperatingDays(operatingDays);
        stopTime.setRouteNumber("405A");
        stopTime.setStopName("Lakeside");
        stopTime.setValidFromDate(LocalDateTime.of(2020,12,12,0,0));
        stopTime.setValidToDate(LocalDateTime.of(2021,12,11,0,0));
        //Verify the builder functionality through getter methods
        assertEquals(LocalTime.of(19,46), stopTime.getArrivalTime());
        assertEquals(LocalTime.of(19,48), stopTime.getDepartureTime());
        assertEquals("Greenfield", stopTime.getDestination());
        assertEquals("123", stopTime.getJourneyNumber());
        assertEquals(Collections.singletonList(DayOfWeek.MONDAY), stopTime.getOperatingDays().getOperatingDays());
        assertEquals(Arrays.asList(LocalDateTime.of(2020,12,25,0,0)), stopTime.getOperatingDays().getSpecialOperatingDays());
        assertEquals("405A", stopTime.getRouteNumber());
        assertEquals(LocalDateTime.of(2020,12,12,0,0), stopTime.getValidFromDate());
        assertEquals(LocalDateTime.of(2021,12,11,0,0), stopTime.getValidToDate());
        assertEquals("Continues as 405B to Brownfield", stopTime.getFootnote());
        //Verify the toString method
        assertEquals("StopTime{id=null, stopName='Lakeside', company='Mustermann Bus GmbH', arrivalTime=19:46, departureTime=19:48, destination='Greenfield', routeNumber='405A', service=null, validFromDate=2020-12-12T00:00, validToDate=2021-12-11T00:00, operatingDays=OperatingDays{operatingDays=[MONDAY], specialOperatingDays=[2020-12-25T00:00], disruptedOperatingDays=null}, journeyNumber='123', footnote='Continues as 405B to Brownfield'}", stopTime.toString());
        //Now use the setter methods
        stopTime.setArrivalTime(LocalTime.of(20, 46));
        stopTime.setCompany("Mustermann Bus GmbH");
        stopTime.setDepartureTime(LocalTime.of(20,48));
        stopTime.setDestination("Lake Way");
        stopTime.setFootnote(null);
        stopTime.setJourneyNumber("1234");
        stopTime.setOperatingDays(operatingDays);
        stopTime.setRouteNumber("405B");
        stopTime.setStopName("Old Town");
        stopTime.setValidFromDate(LocalDateTime.of(2020,11,12,0,0));
        stopTime.setValidToDate(LocalDateTime.of(2021,11,11,0,0));
        //And verify again through the toString methods
        //assertEquals("", stopTime.getOperatingDays().toString());
        assertEquals("StopTime{id=null, stopName='Old Town', company='Mustermann Bus GmbH', arrivalTime=20:46, departureTime=20:48, destination='Lake Way', routeNumber='405B', service=null, validFromDate=2020-11-12T00:00, validToDate=2021-11-11T00:00, operatingDays=OperatingDays{operatingDays=[MONDAY], specialOperatingDays=[2020-12-25T00:00], disruptedOperatingDays=null}, journeyNumber='1234', footnote='null'}", stopTime.toString());
    }

    /**
     * Test the get time method to ensure that depending on departure or arrival parameter it returns the correct code.
     */
    @Test
    public void testGetTime () {
        OperatingDays operatingDays = new OperatingDays();
        operatingDays.setOperatingDays(Arrays.asList(DayOfWeek.MONDAY));
        //Create Test Date
        StopTime stopTime = new StopTime();
        stopTime.setArrivalTime(LocalTime.of(19, 46));
        stopTime.setDepartureTime(LocalTime.of(19,48));
        stopTime.setDestination("Greenfield");
        stopTime.setJourneyNumber("123");
        stopTime.setOperatingDays(operatingDays);
        stopTime.setRouteNumber("405A");
        stopTime.setValidFromDate(LocalDateTime.of(2020,12,12,0,0));
        stopTime.setValidToDate(LocalDateTime.of(2021,12,11,0,0));
        // Test retrieval of departure time.
        assertEquals(stopTime.getTime("Departure"), LocalTime.of(19, 48));
        // Test retrieval of arrival time.
        assertEquals(stopTime.getTime("Arrival"), LocalTime.of(19, 46));
    }

}
