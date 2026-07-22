package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Stop;
import de.davelee.trams.server.response.StopsResponse;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the StopsController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StopsControllerTest {

    @InjectMocks
    private StopsController stopsController;

    @Mock
    private StopService stopService;

    @Mock
    private StopTimeService stopTimeService;

    /**
     * Test the stops endpoint of this controller.
     */
    @Test
    public void testStopsEndpoint() {
        Stop stop = new Stop();
        stop.setId("123");
        stop.setName("Greenfield");
        stop.setCompany("Mustermann Bus GmbH");
        stop.setLatitude(50.03);
        stop.setLongitude(123.04);
        Stop stop2 = new Stop();
        stop2.setId("124");
        stop2.setName("City Centre");
        stop2.setCompany("Mustermann Bus GmbH");
        stop2.setLatitude(50.03);
        stop2.setLongitude(123.04);
        Mockito.when(stopService.getStopsByCompany("Mustermann Bus GmbH")).thenReturn(Lists.newArrayList(stop, stop2));
        ResponseEntity<StopsResponse> responseEntity = stopsController.getStops("Mustermann Bus GmbH", Optional.empty());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().getStopResponses().length);
        assertEquals("Greenfield", responseEntity.getBody().getStopResponses()[0].getName());
        ResponseEntity<StopsResponse> responseEntity2 = stopsController.getStops("", Optional.empty());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        ResponseEntity<StopsResponse> responseEntity3 = stopsController.getStops("Mustermann Bus", Optional.empty());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
        //Test stops with route number.
        Mockito.when(stopTimeService.countStopTimes("Mustermann Bus GmbH", "Greenfield", "1A")).thenReturn(0L);
        Mockito.when(stopTimeService.countStopTimes("Mustermann Bus GmbH", "City Centre", "1A")).thenReturn(1L);
        ResponseEntity<StopsResponse> responseEntity4 = stopsController.getStops("Mustermann Bus GmbH", Optional.of("1A"));
        assertEquals(HttpStatus.OK, responseEntity4.getStatusCode());
        assertEquals(1, responseEntity4.getBody().getCount());
    }

    /**
     * Test the delete stops endpoint of this controller.
     */
    @Test
    public void testDeleteStopsEndpoint() {
        //Do successful request.
        ResponseEntity<Void> responseEntity = stopsController.deleteStops("Mustermann Bus GmbH");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //Assume bad request if company is empty.
        ResponseEntity<Void> responseEntity2 = stopsController.deleteStops("");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
    }

}
