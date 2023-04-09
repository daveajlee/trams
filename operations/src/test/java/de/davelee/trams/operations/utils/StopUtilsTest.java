package de.davelee.trams.operations.utils;

import de.davelee.trams.operations.model.Stop;
import de.davelee.trams.operations.repository.StopRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the StopsUtils class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
public class StopUtilsTest {

    @Mock
    StopRepository stopRepository;

    /**
     * Prevent that duplicates can be added to the database.
     */
    @Test
    public void testDuplicates() {
        Mockito.when(stopRepository.findByCompanyAndName("Mustermann Bus GmbH", "Greenfield")).thenReturn(List.of(Stop.builder()
                .id("123")
                .name("Greenfield")
                .latitude(50.03)
                .longitude(123.04)
                .company("Mustermann Bus GmbH")
                .build()));
        assertTrue(StopUtils.hasStopAlreadyBeenImported("Greenfield", "Mustermann Bus GmbH", stopRepository));
        assertFalse(StopUtils.hasStopAlreadyBeenImported("Bus Depot", "Mustermann Bus GmbH", stopRepository));
    }

}
