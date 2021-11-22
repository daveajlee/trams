package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.request.AddRouteRequest;
import de.davelee.trams.operations.service.RouteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

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

}
