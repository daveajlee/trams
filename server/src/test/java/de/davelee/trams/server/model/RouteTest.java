package de.davelee.trams.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the Route class and ensures that its works correctly.
 * @author Dave Lee
 */
public class RouteTest {

    /**
     * Ensure that a Route class can be correctly instantiated.
     */
    @Test
    public void testBuilderGetterSetterToString ( ) {
        //Test builder
        Route route = Route.builder()
                .company("Mustermann Bus GmbH")
                .id("123")
                .routeNumber("405")
                .startStop("Beach")
                .stops(new String[] { "City Centre", "Hospital"})
                .endStop("Airport")
                .nightRoute(false)
                .build();
        //Verify the builder functionality through getter methods
        assertEquals("Mustermann Bus GmbH", route.getCompany());
        assertEquals("123", route.getId());
        assertEquals("405", route.getRouteNumber());
        assertEquals("Beach", route.getStartStop());
        assertEquals("Airport", route.getEndStop());
        assertEquals(2, route.getStops().length);
        //Verify the toString method
        assertEquals("Route(id=123, routeNumber=405, company=Mustermann Bus GmbH, startStop=Beach, endStop=Airport, stops=[City Centre, Hospital], nightRoute=false)", route.toString());
        //Now use the setter methods
        route.setCompany("Max Mustermann Buses");
        route.setId("1234");
        route.setRouteNumber("405A");
        //And verify again through the toString methods
        assertEquals("Route(id=1234, routeNumber=405A, company=Max Mustermann Buses, startStop=Beach, endStop=Airport, stops=[City Centre, Hospital], nightRoute=false)", route.toString());
    }

}
