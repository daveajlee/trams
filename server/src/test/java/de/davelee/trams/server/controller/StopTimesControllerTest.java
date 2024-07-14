package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.OperatingDays;
import de.davelee.trams.server.model.Stop;
import de.davelee.trams.server.model.StopTime;
import de.davelee.trams.server.request.GenerateStopTimesRequest;
import de.davelee.trams.server.response.StopTimesResponse;
import de.davelee.trams.server.service.StopService;
import de.davelee.trams.server.service.StopTimeService;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;
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

    @Mock
    private StopService stopService;

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
                .operatingDays(OperatingDays.builder()
                        .operatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                        .specialOperatingDays(Arrays.asList(LocalDateTime.of(2020,12,25,0,0), LocalDateTime.of(2021,1,1,0,0)))
                        .build())
                .routeNumber("405A")
                .stopName("Lakeside")
                .validFromDate(LocalDateTime.of(2020,12,12,0,0))
                .validToDate(LocalDateTime.of(2021,12,11,0,0))
                .build()));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.of("22:00"), "15-03-2020", null,true, false);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getCount());
        assertEquals("101", responseEntity.getBody().getStopTimeResponses()[0].getJourneyNumber());
        ResponseEntity<StopTimesResponse> responseEntity2 = stopTimesController.getStopTimes("Lakeside", "", Optional.of("22:00"), "15-03-2020", null, true, false);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        ResponseEntity<StopTimesResponse> responseEntity3 = stopTimesController.getStopTimes("Lakeside", "Mustermann Buses GmbH", Optional.of("22:00"), "15-03-2020", null,true, false);
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
                .operatingDays(OperatingDays.builder()
                        .operatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                        .specialOperatingDays(Arrays.asList(LocalDateTime.of(2020,12,25,0,0), LocalDateTime.of(2021,1,1,0,0)))
                        .build())
                .routeNumber("405A")
                .stopName("Lakeside")
                .validFromDate(LocalDateTime.of(2020,12,12,0,0))
                .validToDate(LocalDateTime.of(2021,12,11,0,0))
                .build()));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.of("22:00"), "15-03-2020", null, false, true);
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
                .operatingDays(OperatingDays.builder()
                        .operatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                        .specialOperatingDays(Arrays.asList(LocalDateTime.of(2020,12,25,0,0), LocalDateTime.of(2021,1,1,0,0)))
                        .build())
                .routeNumber("405A")
                .stopName("Lakeside")
                .validFromDate(LocalDateTime.of(2020,12,12,0,0))
                .validToDate(LocalDateTime.of(2021,12,11,0,0))
                .build()));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.empty(), "10-04-2021", null, true, false);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getCount());
        assertEquals("101", responseEntity.getBody().getStopTimeResponses()[0].getJourneyNumber());
    }

    /**
     * Test the generate endpoint of this controller.
     */
    @Test
    public void testGenerateEndpoint() {
        //Mock data.
        Mockito.when(stopService.getStop("Lee Transport", "Ferry Terminal")).thenReturn(
                Stop.builder()
                        .distances(Map.of("Arena", 7))
                        .waitingTime(0)
                        .company("Lee Transport")
                        .name("Ferry Terminal")
                        .build()
        );
        Mockito.when(stopService.getStop("Lee Transport", "Arena")).thenReturn(
                Stop.builder()
                        .distances(Map.of("Cathedral", 3, "Ferry Terminal", 7))
                        .waitingTime(0)
                        .company("Lee Transport")
                        .name("Arena")
                        .build()
        );
        Mockito.when(stopService.getStop("Lee Transport", "Cathedral")).thenReturn(
                Stop.builder()
                        .distances(Map.of("Bus Station", 1, "Arena", 3))
                        .waitingTime(1)
                        .company("Lee Transport")
                        .name("Cathedral")
                        .build()
        );
        Mockito.when(stopService.getStop("Lee Transport", "Bus Station")).thenReturn(
                Stop.builder()
                        .distances(Map.of("Airport", 5, "Cathedral", 1))
                        .waitingTime(0)
                        .company("Lee Transport")
                        .name("Bus Station")
                        .build()
        );
        Mockito.when(stopService.getStop("Lee Transport", "Airport")).thenReturn(
                Stop.builder()
                        .distances(Map.of("Bus Station", 5))
                        .waitingTime(0)
                        .company("Lee Transport")
                        .name("Airport")
                        .build()
        );
        //1st test
        GenerateStopTimesRequest generateStopTimesRequest = GenerateStopTimesRequest.builder()
                .company("Lee Transport")
                .stopNames(new String[]{ "Ferry Terminal", "Arena", "Cathedral", "Bus Station", "Airport"})
                .routeNumber("TravelExpress")
                .startTime("05:00")
                .endTime("23:00")
                .frequency(90)
                .validFromDate("11-12-2021")
                .validToDate("10-12-2022")
                .operatingDays("Monday,Tuesday,Wednesday,Thursday,Friday,25-12-2021,01-01-2022")
                .build();
        assertEquals("GenerateStopTimesRequest(company=Lee Transport, stopNames=[Ferry Terminal, Arena, Cathedral, Bus Station, Airport], routeNumber=TravelExpress, startTime=05:00, endTime=23:00, frequency=90, validFromDate=11-12-2021, validToDate=10-12-2022, operatingDays=Monday,Tuesday,Wednesday,Thursday,Friday,25-12-2021,01-01-2022)", generateStopTimesRequest.toString());
        stopTimesController.generateStopTimes(generateStopTimesRequest);
        //Mocks for 2nd test
        Mockito.when(stopService.getStop("Lee Transport", "Bus Station")).thenReturn(
                Stop.builder()
                        .distances(Map.of("Country Park", 40))
                        .waitingTime(2)
                        .company("Lee Transport")
                        .name("Bus Station")
                        .build()
        );
        Mockito.when(stopService.getStop("Lee Transport", "Country Park")).thenReturn(
                Stop.builder()
                        .distances(Map.of("Bus Station", 40))
                        .waitingTime(2)
                        .company("Lee Transport")
                        .name("Bus Station")
                        .build()
        );
        //2nd test
        GenerateStopTimesRequest generateStopTimesRequest2 = new GenerateStopTimesRequest();
        generateStopTimesRequest2.setCompany("Lee Transport");
        generateStopTimesRequest2.setStopNames(new String[] { "Bus Station", "Country Park"});
        generateStopTimesRequest2.setRouteNumber("ParkExpress");
        generateStopTimesRequest2.setStartTime("10:00");
        generateStopTimesRequest2.setEndTime("15:00");
        generateStopTimesRequest2.setFrequency(120);
        generateStopTimesRequest2.setValidFromDate("11-12-2021");
        generateStopTimesRequest2.setValidToDate("25-12-2021");
        generateStopTimesRequest2.setOperatingDays("Saturday,Sunday");
        stopTimesController.generateStopTimes(generateStopTimesRequest2);
    }

}
