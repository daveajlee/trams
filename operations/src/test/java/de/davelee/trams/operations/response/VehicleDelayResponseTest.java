package de.davelee.trams.operations.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the VehicleDelayResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehicleDelayResponseTest {

    /**
     * Ensure that a VehicleDelayResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
        VehicleDelayResponse vehicleDelayResponse = new VehicleDelayResponse();
        vehicleDelayResponse.setDelayInMinutes(2);
        vehicleDelayResponse.setCompany("Lee Transport");
        vehicleDelayResponse.setFleetNumber("224");
        assertEquals(2, vehicleDelayResponse.getDelayInMinutes());
        assertEquals("Lee Transport", vehicleDelayResponse.getCompany());
        assertEquals("224", vehicleDelayResponse.getFleetNumber());
    }

}
