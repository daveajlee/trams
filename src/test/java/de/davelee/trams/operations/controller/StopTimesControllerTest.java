package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.StopTime;
import de.davelee.trams.operations.response.StopTimesResponse;
import de.davelee.trams.operations.service.StopTimeService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * This class tests the StopTimesController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class StopTimesControllerTest {

    @InjectMocks
    private StopTimesController stopTimesController;

    @Mock
    private StopTimeService stopTimeService;

    /**
     * Test the departure endpoint of this controller.
     */
    @Test
    public void testDeparturesEndpoints() {
        Mockito.when(stopTimeService.getDepartures("Lakeside", "Mustermann Bus GmbH", "22:00")).thenReturn(Lists.newArrayList(StopTime.builder()
                .arrivalTime(LocalTime.of(22,11))
                .company("Mustermann Bus GmbH")
                .departureTime(LocalTime.of(22,13))
                .destination("Greenfield")
                .id(1)
                .journeyNumber("101")
                .operatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                .routeNumber("405A")
                .stopName("Lakeside")
                .validFromDate(LocalDate.of(2020,12,12))
                .validToDate(LocalDate.of(2021,12,11))
                .build()));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.of("22:00"), "2020-03-15", true, false);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getCount());
        assertEquals("101", responseEntity.getBody().getStopTimeResponses()[0].getJourneyNumber());
        ResponseEntity<StopTimesResponse> responseEntity2 = stopTimesController.getStopTimes("Lakeside", "", Optional.of("22:00"), "2020-03-15", true, false);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        ResponseEntity<StopTimesResponse> responseEntity3 = stopTimesController.getStopTimes("Lakeside", "Mustermann Buses GmbH", Optional.of("22:00"), "2020-03-15", true, false);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
    }

    /**
     * Test the arrival endpoint of this controller.
     */
    @Test
    public void testArrivalsEndpoints() {
        Mockito.when(stopTimeService.getArrivals(anyString(), anyString(), anyString())).thenReturn(Lists.newArrayList(StopTime.builder()
                .arrivalTime(LocalTime.of(22,11))
                .company("Mustermann Bus GmbH")
                .departureTime(LocalTime.of(22,13))
                .destination("Greenfield")
                .id(1)
                .journeyNumber("101")
                .operatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                .routeNumber("405A")
                .stopName("Lakeside")
                .validFromDate(LocalDate.of(2020,12,12))
                .validToDate(LocalDate.of(2021,12,11))
                .build()));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.of("22:00"), "2020-03-15", false, true);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getCount());
        assertEquals("22:11", responseEntity.getBody().getStopTimeResponses()[0].getArrivalTime());
    }

    /**
     * Test the departure date endpoint of this controller.
     */
    @Test
    public void testDeparturesDateEndpoints() {
        Mockito.when(stopTimeService.getDeparturesByDate(anyString(), anyString(), anyString())).thenReturn(Lists.newArrayList(StopTime.builder()
                .arrivalTime(LocalTime.of(22,11))
                .departureTime(LocalTime.of(22,13))
                .destination("Greenfield")
                .id(1)
                .journeyNumber("101")
                .operatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                .routeNumber("405A")
                .stopName("Lakeside")
                .validFromDate(LocalDate.of(2020,12,12))
                .validToDate(LocalDate.of(2021,12,11))
                .build()));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.empty(), "2021-04-10", true, false);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getCount());
        assertEquals("101", responseEntity.getBody().getStopTimeResponses()[0].getJourneyNumber());
    }

}
