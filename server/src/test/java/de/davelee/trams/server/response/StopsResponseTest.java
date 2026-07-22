package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the StopsResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class StopsResponseTest {

    @Test
    public void testSetters() {
        StopResponse stopResponse = new StopResponse();
        stopResponse.setCompany("Mustermann Bus GmbH");
        stopResponse.setName("Greenfield");
        stopResponse.setLatitude(50.03);
        stopResponse.setLongitude(123.04);
        StopsResponse stopsResponse = new StopsResponse();
        stopsResponse.setCount(1L);
        stopsResponse.setStopResponses(new StopResponse[] {
                stopResponse
        });
        assertEquals(1L, stopsResponse.getCount());
        assertEquals("Mustermann Bus GmbH", stopsResponse.getStopResponses()[0].getCompany());
    }

}
