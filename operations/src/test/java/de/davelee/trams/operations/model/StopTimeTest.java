package de.davelee.trams.operations.model;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        //Test builder
        StopTime stopTime = StopTime.builder()
                .arrivalTime(LocalTime.of(19, 46))
                .company("Mustermann Bus GmbH")
                .departureTime(LocalTime.of(19,48))
                .destination("Greenfield")
                .footnote("Continues as 405B to Brownfield")
                .id(1234)
                .journeyNumber("123")
                .operatingDays(OperatingDays.builder()
                        .operatingDays(Arrays.asList(DayOfWeek.MONDAY))
                        .specialOperatingDays(Arrays.asList(LocalDate.of(2020,12,25)))
                        .build())
                .routeNumber("405A")
                .stopName("Lakeside")
                .validFromDate(LocalDate.of(2020,12,12))
                .validToDate(LocalDate.of(2021,12,11))
                .build();
        //Verify the builder functionality through getter methods
        assertEquals(LocalTime.of(19,46), stopTime.getArrivalTime());
        assertEquals(LocalTime.of(19,48), stopTime.getDepartureTime());
        assertEquals("Greenfield", stopTime.getDestination());
        assertEquals(1234, stopTime.getId());
        assertEquals("123", stopTime.getJourneyNumber());
        assertEquals(Collections.singletonList(DayOfWeek.MONDAY), stopTime.getOperatingDays().getOperatingDays());
        assertEquals(Arrays.asList(LocalDate.of(2020,12,25)), stopTime.getOperatingDays().getSpecialOperatingDays());
        assertEquals("405A", stopTime.getRouteNumber());
        assertEquals(LocalDate.of(2020,12,12), stopTime.getValidFromDate());
        assertEquals(LocalDate.of(2021,12,11), stopTime.getValidToDate());
        assertEquals("Continues as 405B to Brownfield", stopTime.getFootnote());
        //Verify the toString method
        assertEquals("StopTime(id=1234, stopName=Lakeside, company=Mustermann Bus GmbH, arrivalTime=19:46, departureTime=19:48, destination=Greenfield, routeNumber=405A, validFromDate=2020-12-12, validToDate=2021-12-11, operatingDays=OperatingDays(operatingDays=[MONDAY], specialOperatingDays=[2020-12-25], disruptedOperatingDays=null), journeyNumber=123, footnote=Continues as 405B to Brownfield)", stopTime.toString());
        //Now use the setter methods
        stopTime.setArrivalTime(LocalTime.of(20, 46));
        stopTime.setCompany("Mustermann Bus GmbH");
        stopTime.setDepartureTime(LocalTime.of(20,48));
        stopTime.setDestination("Lake Way");
        stopTime.setFootnote(null);
        stopTime.setId(12345);
        stopTime.setJourneyNumber("1234");
        stopTime.setOperatingDays(OperatingDays.builder()
                .operatingDays(Arrays.asList(DayOfWeek.MONDAY))
                .specialOperatingDays(Arrays.asList(LocalDate.of(2020,12,25)))
                .build());
        stopTime.setRouteNumber("405B");
        stopTime.setStopName("Old Town");
        stopTime.setValidFromDate(LocalDate.of(2020,11,12));
        stopTime.setValidToDate(LocalDate.of(2021,11,11));
        //And verify again through the toString methods
        //assertEquals("", stopTime.getOperatingDays().toString());
        assertEquals("StopTime(id=12345, stopName=Old Town, company=Mustermann Bus GmbH, arrivalTime=20:46, departureTime=20:48, destination=Lake Way, routeNumber=405B, validFromDate=2020-11-12, validToDate=2021-11-11, operatingDays=OperatingDays(operatingDays=[MONDAY], specialOperatingDays=[2020-12-25], disruptedOperatingDays=null), journeyNumber=1234, footnote=null)", stopTime.toString());
    }

    /**
     * Test the get time method to ensure that depending on departure or arrival parameter it returns the correct code.
     */
    @Test
    public void testGetTime () {
        //Create Test Date
        StopTime stopTime = StopTime.builder()
                .arrivalTime(LocalTime.of(19, 46))
                .departureTime(LocalTime.of(19,48))
                .destination("Greenfield")
                .id(1234)
                .journeyNumber("123")
                .operatingDays(OperatingDays.builder()
                        .operatingDays(Arrays.asList(DayOfWeek.MONDAY))
                        .build())
                .routeNumber("405A")
                .validFromDate(LocalDate.of(2020,12,12))
                .validToDate(LocalDate.of(2021,12,11))
                .build();
        // Test retrieval of departure time.
        assertEquals(stopTime.getTime("Departure"), LocalTime.of(19, 48));
        // Test retrieval of arrival time.
        assertEquals(stopTime.getTime("Arrival"), LocalTime.of(19, 46));
    }

}
