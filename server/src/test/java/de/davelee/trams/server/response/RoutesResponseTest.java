package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the RoutesResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class RoutesResponseTest {

    @Test
    public void testSetters() {
        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setCompany("Mustermann Bus GmbH");
        routeResponse.setRouteNumber("405");
        RoutesResponse routesResponse = new RoutesResponse();
        routesResponse.setCount(1L);
        routesResponse.setRouteResponses(new RouteResponse[] {
                routeResponse
        });
        assertEquals(1L, routesResponse.getCount());
        assertEquals("Mustermann Bus GmbH", routesResponse.getRouteResponses()[0].getCompany());
    }

}
