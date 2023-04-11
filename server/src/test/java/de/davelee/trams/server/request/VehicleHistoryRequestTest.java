package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the VehicleHistoryRequest class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehicleHistoryRequestTest {

    /**
     * Ensure that a VehicleHistoryRequest class can be correctly instantiated.
     */
    @Test
    public void testCreateRequest( ) {
        VehicleHistoryRequest vehicleHistoryRequest = new VehicleHistoryRequest();
        vehicleHistoryRequest.setVehicleHistoryReason("PURCHASED");
        vehicleHistoryRequest.setComment("Love at first sight");
        vehicleHistoryRequest.setDate("01-11-2021");
        assertEquals("PURCHASED", vehicleHistoryRequest.getVehicleHistoryReason());
        assertEquals("Love at first sight", vehicleHistoryRequest.getComment());
        assertEquals("01-11-2021", vehicleHistoryRequest.getDate());
    }

}
