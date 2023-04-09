package de.davelee.trams.operations.utils;

import de.davelee.trams.operations.model.Route;
import de.davelee.trams.operations.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the RouteUtils class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
public class RouteUtilsTest {

    @Mock
    private RouteRepository routeRepository;

    /**
     * Prevent that duplicates can be added to the database.
     */
    @Test
    public void testDuplicates() {
        Mockito.when(routeRepository.findByCompanyAndRouteNumber("Mustermann Bus GmbH", "405")).thenReturn(List.of(Route.builder()
                .company("Mustermann Bus GmbH")
                .id("123")
                .routeNumber("405")
                .build()));
        assertTrue(RouteUtils.hasRouteAlreadyBeenImported("405", "Mustermann Bus GmbH", routeRepository));
        assertFalse(RouteUtils.hasRouteAlreadyBeenImported("406", "Mustermann Bus GmbH", routeRepository));
    }

}
