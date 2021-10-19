package de.davelee.trams.operations.service;

import de.davelee.trams.operations.model.Route;
import de.davelee.trams.operations.repository.RouteRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }
}
