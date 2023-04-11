package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.request.AddRouteRequest;
import de.davelee.trams.server.service.RouteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * This class tests the RouteController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class RouteControllerTest {

    @InjectMocks
    private RouteController routeController;

    @Mock
    private RouteService routeService;

    /**
     * Test the add route endpoint of this controller.
     */
    @Test
    public void testRouteEndpoint() {
        //Mock important method.
        Mockito.when(routeService.addRoute(any())).thenReturn(true);
        //Test success route.
        AddRouteRequest addRouteRequest = AddRouteRequest.builder()
                .company("Example Company")
                .routeNumber("1A")
                .build();
        assertEquals(HttpStatus.CREATED, routeController.addRoute(addRouteRequest).getStatusCode());
        //Test unsuccessful route.
        Mockito.when(routeService.addRoute(any())).thenReturn(false);
        AddRouteRequest addRouteRequest2 = new AddRouteRequest();
        addRouteRequest2.setCompany("Example Company");
        addRouteRequest2.setRouteNumber("1B");
        assertEquals(500, routeController.addRoute(addRouteRequest2).getStatusCodeValue());
        //Test missing company and name.
        addRouteRequest2.setCompany(""); addRouteRequest2.setRouteNumber("");
        assertEquals(HttpStatus.BAD_REQUEST, routeController.addRoute(addRouteRequest2).getStatusCode());
    }

    /**
     * Test the get route endpoint of this controller.
     */
    @Test
    public void testGetRouteEndpoint() {
        //Mock important method.
        Mockito.when(routeService.getRoutesByCompanyAndRouteNumber("Example Company", "1A")).thenReturn(
                List.of(Route.builder().routeNumber("1A").company("Example Company").build()));
        //Test successfully retrieve.
        assertEquals(HttpStatus.OK, routeController.getRoute("Example Company", "1A").getStatusCode());
        //Test unsuccessful retrieve.
        Mockito.when(routeService.getRoutesByCompanyAndRouteNumber("Example Company", "1C")).thenReturn(
                List.of());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, routeController.getRoute("Example Company", "1C").getStatusCode());
        //Test missing company.
        assertEquals(HttpStatus.BAD_REQUEST, routeController.getRoute("", "1C").getStatusCode());
    }

    /**
     * Test the delete route endpoint of this controller.
     */
    @Test
    public void testDeleteRouteEndpoint() {
        //Mock important method.
        Mockito.when(routeService.getRoutesByCompanyAndRouteNumber("Example Company", "1A")).thenReturn(
                List.of(Route.builder().routeNumber("1A").company("Example Company").build()));
        //Test successfully retrieve.
        assertEquals(HttpStatus.OK, routeController.deleteRoute("Example Company", "1A").getStatusCode());
        //Test unsuccessful retrieve.
        Mockito.when(routeService.getRoutesByCompanyAndRouteNumber("Example Company", "1C")).thenReturn(
                List.of());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, routeController.deleteRoute("Example Company", "1C").getStatusCode());
        //Test missing company.
        assertEquals(HttpStatus.BAD_REQUEST, routeController.deleteRoute("", "1C").getStatusCode());
    }

}
