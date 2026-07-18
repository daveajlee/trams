package de.davelee.trams.server.utils;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the RouteUtils class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RouteUtilsTest {

    @Mock
    private RouteRepository routeRepository;

    /**
     * Prevent that duplicates can be added to the database.
     */
    @Test
    public void testDuplicates() {
        Route route = new Route();
        route.setCompany("Mustermann Bus GmbH");
        route.setId("123");
        route.setRouteNumber("405");
        Mockito.when(routeRepository.findByCompanyAndRouteNumber("Mustermann Bus GmbH", "405")).thenReturn(List.of(route));
        assertTrue(RouteUtils.hasRouteAlreadyBeenImported("405", "Mustermann Bus GmbH", routeRepository));
        assertFalse(RouteUtils.hasRouteAlreadyBeenImported("406", "Mustermann Bus GmbH", routeRepository));
    }

}
