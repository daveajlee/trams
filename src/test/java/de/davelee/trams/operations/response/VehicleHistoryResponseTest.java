package de.davelee.trams.operations.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the VehicleHistoryResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehicleHistoryResponseTest {

    /**
     * Ensure that a VehicleHistoryResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
        VehicleHistoryResponse vehicleHistoryResponse = new VehicleHistoryResponse();
        vehicleHistoryResponse.setVehicleHistoryReason("Purchased");
        vehicleHistoryResponse.setComment("Purchased!");
        vehicleHistoryResponse.setDate("01-03-2021");
        assertEquals("Purchased", vehicleHistoryResponse.getVehicleHistoryReason());
        assertEquals("Purchased!", vehicleHistoryResponse.getComment());
        assertEquals("01-03-2021", vehicleHistoryResponse.getDate());
        assertEquals("VehicleHistoryResponse(date=01-03-2021, vehicleHistoryReason=Purchased, comment=Purchased!)", vehicleHistoryResponse.toString());
    }

}
