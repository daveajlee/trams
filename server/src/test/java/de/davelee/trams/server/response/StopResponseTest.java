package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the StopResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class StopResponseTest {

    /**
     * Ensure that a StopResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
        StopResponse stopResponse = new StopResponse();
        stopResponse.setCompany("Mustermann Bus GmbH");
        stopResponse.setName("Greenfield");
        stopResponse.setWaitingTime(1);
        stopResponse.setDistances(Map.of("City Centre",20));
        stopResponse.setLatitude(50.03);
        stopResponse.setLongitude(123.04);
        assertEquals("Greenfield", stopResponse.getName());
        assertEquals("Mustermann Bus GmbH", stopResponse.getCompany());
        assertEquals(50.03, stopResponse.getLatitude());
        assertEquals(123.04, stopResponse.getLongitude());
        stopResponse.setCompany("Mustermann Buses GmbH");
        stopResponse.setLatitude(50.04);
        stopResponse.setLongitude(122.04);
        stopResponse.setName("Greenerfield");
        assertEquals("Greenerfield", stopResponse.getName());
        assertEquals("Mustermann Buses GmbH", stopResponse.getCompany());
        assertEquals(50.04, stopResponse.getLatitude());
        assertEquals(122.04, stopResponse.getLongitude());
        assertEquals("StopResponse(name=Greenerfield, company=Mustermann Buses GmbH, waitingTime=1, distances={City Centre=20}, latitude=50.04, longitude=122.04)", stopResponse.toString());
    }

}
