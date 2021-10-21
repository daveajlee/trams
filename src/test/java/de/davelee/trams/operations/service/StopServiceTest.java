package de.davelee.trams.operations.service;

import de.davelee.trams.operations.model.Stop;
import de.davelee.trams.operations.repository.StopRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the StopService class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
public class StopServiceTest {

    @InjectMocks
    private StopService stopService;

    @Mock
    private StopRepository stopRepository;

    /**
     * Verify that stops can be retrieved from the database correctly.
     */
    @Test
    public void testService ( ) {
        Mockito.when(stopRepository.findByCompany("Mustermann Bus GmbH")).thenReturn(Lists.newArrayList(Stop.builder()
                .id("123")
                .name("Greenfield")
                .latitude(50.03)
                .longitude(123.04)
                .company("Mustermann Bus GmbH")
                .build()));
        assertEquals(1, stopService.getStopsByCompany("Mustermann Bus GmbH").size());
        assertEquals("123", stopService.getStopsByCompany("Mustermann Bus GmbH").get(0).getId());
        assertEquals("Greenfield", stopService.getStopsByCompany("Mustermann Bus GmbH").get(0).getName());
        assertEquals(50.03, stopService.getStopsByCompany("Mustermann Bus GmbH").get(0).getLatitude());
        assertEquals(123.04, stopService.getStopsByCompany("Mustermann Bus GmbH").get(0).getLongitude());
    }
}
