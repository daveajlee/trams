package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Stop;
import de.davelee.trams.server.repository.StopRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

/**
 * This class tests the StopService class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StopServiceTest {

    @InjectMocks
    private StopService stopService;

    @Mock
    private StopRepository stopRepository;

    /**
     * Verify that a stop can be added to the database.
     */
    @Test
    public void testAddStop() {
        Stop stop = new Stop();
        stop.setId("123");
        stop.setName("Greenfield");
        stop.setLatitude(50.03);
        stop.setLongitude(123.04);
        stop.setCompany("Mustermann Bus GmbH");
        Mockito.when(stopRepository.save(any())).thenReturn(stop);
        assertTrue(stopService.addStop(stop));
    }

    /**
     * Verify that stops can be retrieved from the database correctly.
     */
    @Test
    public void testService ( ) {
        Stop stop = new Stop();
        stop.setId("123");
        stop.setName("Greenfield");
        stop.setLatitude(50.03);
        stop.setLongitude(123.04);
        stop.setCompany("Mustermann Bus GmbH");
        Mockito.when(stopRepository.findByCompany("Mustermann Bus GmbH")).thenReturn(Lists.newArrayList(stop));
        assertEquals(1, stopService.getStopsByCompany("Mustermann Bus GmbH").size());
        assertEquals("123", stopService.getStopsByCompany("Mustermann Bus GmbH").get(0).getId());
        assertEquals("Greenfield", stopService.getStopsByCompany("Mustermann Bus GmbH").get(0).getName());
        assertEquals(50.03, stopService.getStopsByCompany("Mustermann Bus GmbH").get(0).getLatitude());
        assertEquals(123.04, stopService.getStopsByCompany("Mustermann Bus GmbH").get(0).getLongitude());
    }

    /**
     * Verify that a stop can be retrieved from the database successfully.
     */
    @Test
    public void testGetStop ( ) {
        Stop stop = new Stop();
        stop.setId("123");
        stop.setName("Greenfield");
        stop.setLatitude(50.03);
        stop.setLongitude(123.04);
        stop.setCompany("Mustermann Bus GmbH");
        Mockito.when(stopRepository.findByCompanyAndName("Mustermann Bus GmbH", "Greenfield")).thenReturn(Lists.newArrayList(stop));
        assertEquals("Greenfield", stopService.getStop("Mustermann Bus GmbH", "Greenfield").getName());
    }

    /**
     * Verify that a stop can be deleted from the database correctly.
     */
    @Test
    public void testDeleteStop ( ) {
        Stop stop = new Stop();
        stop.setId("123");
        stop.setName("Greenfield");
        stop.setLatitude(50.03);
        stop.setLongitude(123.04);
        stop.setCompany("Mustermann Bus GmbH");
        //Mock important method in repository.
        Mockito.when(stopRepository.findByCompany("Mustermann Bus GmbH")).thenReturn(Lists.newArrayList(stop));
        //Do test.
        stopService.deleteStops("Mustermann Bus GmbH");
    }
}
