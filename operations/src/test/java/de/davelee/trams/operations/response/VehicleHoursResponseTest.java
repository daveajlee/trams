package de.davelee.trams.operations.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the VehicleHoursResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehicleHoursResponseTest {

    @Test
    public void testSetters() {
        VehicleHoursResponse vehicleHoursResponse = new VehicleHoursResponse();
        vehicleHoursResponse.setMaximumHoursReached(true);
        vehicleHoursResponse.setNumberOfHoursAvailable(0);
        vehicleHoursResponse.setNumberOfHoursSoFar(16);
        assertEquals(0, vehicleHoursResponse.getNumberOfHoursAvailable());
        assertEquals(16, vehicleHoursResponse.getNumberOfHoursSoFar());
        assertTrue(vehicleHoursResponse.isMaximumHoursReached());
        assertEquals("VehicleHoursResponse(numberOfHoursSoFar=16, numberOfHoursAvailable=0, maximumHoursReached=true)", vehicleHoursResponse.toString());
    }
}
