package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the StopTimesResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class StopTimesResponseTest {

    @Test
    public void testSetters() {
        StopTimeResponse stopTimeResponse = new StopTimeResponse();
        stopTimeResponse.setCompany("Mustermann Bus GmbH");
        stopTimeResponse.setStopName("Greenfield");
        stopTimeResponse.setRouteNumber("101");
        stopTimeResponse.setJourneyNumber("102");
        stopTimeResponse.setDestination("Lakeside");
        stopTimeResponse.setArrivalTime("22:10");
        stopTimeResponse.setDepartureTime("22:11");
        stopTimeResponse.setOperatingDays(List.of("Friday","Saturday"));
        stopTimeResponse.setValidFromDate("23-04-2021");
        stopTimeResponse.setValidToDate("23-10-2021");
        StopTimesResponse stopTimesResponse = new StopTimesResponse();
        stopTimesResponse.setCount(1L);
        stopTimesResponse.setStopTimeResponses(new StopTimeResponse[] {
                stopTimeResponse
        });
        assertEquals(1L, stopTimesResponse.getCount());
        assertEquals("Mustermann Bus GmbH", stopTimesResponse.getStopTimeResponses()[0].getCompany());
    }

}
