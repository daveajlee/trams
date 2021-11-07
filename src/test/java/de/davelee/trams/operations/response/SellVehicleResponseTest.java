package de.davelee.trams.operations.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the SellVehicleResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class SellVehicleResponseTest {

    /**
     * Ensure that a SellVehicleResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
        SellVehicleResponse sellVehicleResponse = new SellVehicleResponse();
        sellVehicleResponse.setSold(true);
        sellVehicleResponse.setSoldPrice(200000);
        assertTrue(sellVehicleResponse.isSold());
        assertEquals(200000, sellVehicleResponse.getSoldPrice());
    }

}
