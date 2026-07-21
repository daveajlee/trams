package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.*;
import de.davelee.trams.server.request.GenerateStopTimesRequest;
import de.davelee.trams.server.response.StopTimesResponse;
import de.davelee.trams.server.service.CompanyService;
import de.davelee.trams.server.service.StopService;
import de.davelee.trams.server.service.StopTimeService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Provider;
import java.time.DayOfWeek;
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
@ExtendWith(MockitoExtension.class)
public class StopTimesControllerTest {

    @InjectMocks
    private StopTimesController stopTimesController;

    @Mock
    private StopTimeService stopTimeService;

    @Mock
    private StopService stopService;

    @Mock
    private CompanyService companyService;

    /**
     * Test the departure endpoint of this controller.
     */
    @Test
    public void testDeparturesEndpoints() {
        StopTime stopTime = new StopTime();
        stopTime.setArrivalTime(LocalTime.of(22,11));
        stopTime.setCompany("Mustermann Bus GmbH");
        stopTime.setDepartureTime(LocalTime.of(22,13));
        stopTime.setDestination("Greenfield");
        stopTime.setJourneyNumber("101");
        stopTime.setOperatingDays(new OperatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                        Arrays.asList(LocalDateTime.of(2020,12,25,0,0), LocalDateTime.of(2021,1,1,0,0)), null));
        stopTime.setRouteNumber("405A");
        stopTime.setStopName("Lakeside");
        ServiceTrip serviceTrip = new ServiceTrip();
        serviceTrip.setRouteSchedule(new RouteSchedule("405A", "1"));
        stopTime.setService(serviceTrip);
        stopTime.setValidFromDate(LocalDateTime.of(2020,12,12,0,0));
        stopTime.setValidToDate(LocalDateTime.of(2021,12,11,0,0));
        Mockito.when(stopTimeService.getDepartures("Lakeside", "Mustermann Bus GmbH", "22:00", "")).thenReturn(Lists.newArrayList(stopTime));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.of("22:00"), "15-03-2020", null,true, false, Optional.empty());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getCount());
        assertEquals("101", responseEntity.getBody().getStopTimeResponses()[0].getJourneyNumber());
        ResponseEntity<StopTimesResponse> responseEntity2 = stopTimesController.getStopTimes("Lakeside", "", Optional.of("22:00"), "15-03-2020", null, true, false, Optional.empty());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        ResponseEntity<StopTimesResponse> responseEntity3 = stopTimesController.getStopTimes("Lakeside", "Mustermann Buses GmbH", Optional.of("22:00"), "15-03-2020", null,true, false, Optional.empty());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
    }

    /**
     * Test the arrival endpoint of this controller.
     */
    @Test
    public void testArrivalsEndpoints() {
        StopTime stopTime = new StopTime();
        stopTime.setArrivalTime(LocalTime.of(22,11));
        stopTime.setCompany("Mustermann Bus GmbH");
        stopTime.setDepartureTime(LocalTime.of(22,13));
        stopTime.setDestination("Greenfield");
        stopTime.setJourneyNumber("101");
        stopTime.setOperatingDays(new OperatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                Arrays.asList(LocalDateTime.of(2020,12,25,0,0), LocalDateTime.of(2021,1,1,0,0)), null));
        stopTime.setRouteNumber("405A");
        stopTime.setStopName("Lakeside");
        ServiceTrip serviceTrip = new ServiceTrip();
        serviceTrip.setRouteSchedule(new RouteSchedule("405A", "1"));
        stopTime.setService(serviceTrip);
        stopTime.setValidFromDate(LocalDateTime.of(2020,12,12,0,0));
        stopTime.setValidToDate(LocalDateTime.of(2021,12,11,0,0));
        Mockito.when(stopTimeService.getArrivals(anyString(), anyString(), anyString(), anyString())).thenReturn(Lists.newArrayList(stopTime));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.of("22:00"), "15-03-2020", null, false, true, Optional.empty());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getCount());
        assertEquals("22:11", responseEntity.getBody().getStopTimeResponses()[0].getArrivalTime());
    }

    /**
     * Test the departure date endpoint of this controller.
     */
    @Test
    public void testDeparturesDateEndpoints() {
        StopTime stopTime = new StopTime();
        stopTime.setArrivalTime(LocalTime.of(22,11));
        stopTime.setCompany("Mustermann Bus GmbH");
        stopTime.setDepartureTime(LocalTime.of(22,13));
        stopTime.setDestination("Greenfield");
        stopTime.setJourneyNumber("101");
        stopTime.setOperatingDays(new OperatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                Arrays.asList(LocalDateTime.of(2020,12,25,0,0), LocalDateTime.of(2021,1,1,0,0)), null));
        stopTime.setRouteNumber("405A");
        stopTime.setStopName("Lakeside");
        ServiceTrip serviceTrip = new ServiceTrip();
        serviceTrip.setRouteSchedule(new RouteSchedule("405A", "1"));
        stopTime.setService(serviceTrip);
        stopTime.setValidFromDate(LocalDateTime.of(2020,12,12,0,0));
        stopTime.setValidToDate(LocalDateTime.of(2021,12,11,0,0));
        Mockito.when(stopTimeService.getDeparturesByDate(anyString(), anyString(), anyString(), anyString())).thenReturn(Lists.newArrayList(stopTime));
        ResponseEntity<StopTimesResponse> responseEntity = stopTimesController.getStopTimes("Lakeside", "Mustermann Bus GmbH", Optional.empty(), "10-04-2021", null, true, false, Optional.empty());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getCount());
        assertEquals("101", responseEntity.getBody().getStopTimeResponses()[0].getJourneyNumber());
    }

    /**
     * Test the generate endpoint of this controller.
     */
    @Test
    public void testGenerateEndpoint() {
        Mockito.when(companyService.getTime("Lee Transport")).thenReturn(LocalDateTime.now());
        //1st test
        GenerateStopTimesRequest generateStopTimesRequest = new GenerateStopTimesRequest();
        generateStopTimesRequest.setCompany("Lee Transport");
        generateStopTimesRequest.setStopNames(new String[]{ "Ferry Terminal", "Arena", "Cathedral", "Bus Station", "Airport"});
        generateStopTimesRequest.setRouteNumber("TravelExpress");
        generateStopTimesRequest.setStartTime("05:00");
        generateStopTimesRequest.setEndTime("23:00");
        generateStopTimesRequest.setFrequency(90);
        generateStopTimesRequest.setValidFromDate("11-12-2021");
        generateStopTimesRequest.setValidToDate("10-12-2022");
        generateStopTimesRequest.setOperatingDays("Monday,Tuesday,Wednesday,Thursday,Friday,25-12-2021,01-01-2022");
        assertEquals("GenerateStopTimesRequest(company=Lee Transport, stopNames=[Ferry Terminal, Arena, Cathedral, Bus Station, Airport], routeNumber=TravelExpress, startTime=05:00, endTime=23:00, startStop=null, endStop=null, frequency=90, numTours=0, validFromDate=11-12-2021, validToDate=10-12-2022, operatingDays=Monday,Tuesday,Wednesday,Thursday,Friday,25-12-2021,01-01-2022, stopDistances=null)", generateStopTimesRequest.toString());
        stopTimesController.generateStopTimes(generateStopTimesRequest);
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
