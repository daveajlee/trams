package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.response.RoutesResponse;
import de.davelee.trams.server.service.RouteService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the RoutesController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class RoutesControllerTest {

    @InjectMocks
    private RoutesController routesController;

    @Mock
    private RouteService routeService;

    /**
     * Test the routes endpoint of this controller.
     */
    @Test
    public void testRoutesEndpoint() {
        Mockito.when(routeService.getRoutesByCompany("Mustermann Bus GmbH")).thenReturn(Lists.newArrayList(Route.builder()
                .company("Mustermann Bus GmbH")
                .id("123")
                .routeNumber("405")
                .build()));
        ResponseEntity<RoutesResponse> responseEntity = routesController.getRoutesByCompany("Mustermann Bus GmbH");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getCount());
        assertEquals("405", responseEntity.getBody().getRouteResponses()[0].getRouteNumber());
        //Second test - do not supply company.
        ResponseEntity<RoutesResponse> responseEntity2 = routesController.getRoutesByCompany("");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Third test - company has no buses.
        ResponseEntity<RoutesResponse> responseEntity3 = routesController.getRoutesByCompany("Mustermann Buses GmbH");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
    }

    /**
     * Test the delete routes endpoint of this controller.
     */
    @Test
    public void testDeleteRoutesEndpoint() {
        Mockito.when(routeService.getRoutesByCompany("Mustermann Bus GmbH")).thenReturn(Lists.newArrayList(Route.builder()
                .company("Mustermann Bus GmbH")
                .id("123")
                .routeNumber("405")
                .build()));
        ResponseEntity<Void> responseEntity = routesController.deleteRoutesByCompany("Mustermann Bus GmbH");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //Second test - do not supply company.
        ResponseEntity<Void> responseEntity2 = routesController.deleteRoutesByCompany("");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Third test - company has no buses.
        ResponseEntity<Void> responseEntity3 = routesController.deleteRoutesByCompany("Mustermann Buses GmbH");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
    }

}
