package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.repository.RouteRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

/**
 * This class tests the RouteService class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
public class RouteServiceTest {

    @InjectMocks
    private RouteService routeService;

    @Mock
    private RouteRepository routeRepository;

    /**
     * Verify that a route can be added to the database.
     */
    @Test
    public void testAddRoute() {
        Mockito.when(routeRepository.save(any())).thenReturn(Route.builder()
                .routeNumber("1A")
                .id("1")
                .company("Mustermann Bus GmbH")
                .build());
        assertTrue(routeService.addRoute(Route.builder()
                .routeNumber("1A")
                .id("1")
                .company("Mustermann Bus GmbH")
                .build()));
    }

    /**
     * Verify that routes can be retrieved from the database correctly.
     */
    @Test
    public void testService ( ) {
        Mockito.when(routeRepository.findByCompany("Mustermann Bus GmbH")).thenReturn(Lists.newArrayList(Route.builder()
                .routeNumber("1A")
                .id("1")
                .company("Mustermann Bus GmbH")
                .build()));
        assertEquals(1, routeService.getRoutesByCompany("Mustermann Bus GmbH").size());
        assertEquals("1A", routeService.getRoutesByCompany("Mustermann Bus GmbH").get(0).getRouteNumber());
        assertEquals("1", routeService.getRoutesByCompany("Mustermann Bus GmbH").get(0).getId());
        assertEquals("Mustermann Bus GmbH", routeService.getRoutesByCompany("Mustermann Bus GmbH").get(0).getCompany());
        //Test retrieval of single route.
        Mockito.when(routeRepository.findByCompanyAndRouteNumber("Mustermann Bus GmbH", "1C")).thenReturn(Lists.newArrayList(Route.builder()
                .routeNumber("1A")
                .id("1")
                .company("Mustermann Bus GmbH")
                .build()));
        assertEquals(1, routeService.getRoutesByCompanyAndRouteNumber("Mustermann Bus GmbH", "1C").size());
    }

    /**
     * Verify that routes can be deleted successfully.
     */
    @Test
    public void testDeleteRoutes ( ) {
        routeService.deleteRoute(Route.builder().company("Mustermann Bus Gmbh").routeNumber("1C").build());
    }

}
