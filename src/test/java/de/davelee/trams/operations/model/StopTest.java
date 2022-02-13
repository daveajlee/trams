package de.davelee.trams.operations.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the Stop class and ensures that its works correctly.
 * @author Dave Lee
 */
public class StopTest {

    /**
     * Ensure that a Stop class can be correctly instantiated.
     */
    @Test
    public void testBuilderGetterSetterToString ( ) {
        //Test builder
        Stop stop = Stop.builder()
                .id("123")
                .name("Greenfield")
                .latitude(50.03)
                .longitude(123.04)
                .waitingTime(1)
                .distances(Map.of("City Centre",20))
                .company("Mustermann Bus GmbH")
                .build();
        //Verify the builder functionality through getter methods
        assertEquals("123", stop.getId());
        assertEquals("Greenfield", stop.getName());
        assertEquals(50.03, stop.getLatitude());
        assertEquals(123.04, stop.getLongitude());
        assertEquals("Mustermann Bus GmbH", stop.getCompany());
        //Verify the toString method
        assertEquals("Stop(id=123, name=Greenfield, company=Mustermann Bus GmbH, waitingTime=1, distances={City Centre=20}, latitude=50.03, longitude=123.04)", stop.toString());
        //Now use the setter methods
        stop.setId("1234");
        stop.setName("Greenerfield");
        stop.setCompany("Mustermann Bus GmbH");
        stop.setLatitude(52.03);
        stop.setLongitude(133.04);
        //And verify again through the toString methods
        assertEquals("Stop(id=1234, name=Greenerfield, company=Mustermann Bus GmbH, waitingTime=1, distances={City Centre=20}, latitude=52.03, longitude=133.04)", stop.toString());
    }

}
