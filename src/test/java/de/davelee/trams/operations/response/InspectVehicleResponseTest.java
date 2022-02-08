package de.davelee.trams.operations.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the InspectVehicleResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class InspectVehicleResponseTest {

    /**
     * Ensure that a InspectVehicleResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
        InspectVehicleResponse inspectVehicleResponse = new InspectVehicleResponse();
        inspectVehicleResponse.setInspected(true);
        inspectVehicleResponse.setInspectionPrice(1000);
        assertTrue(inspectVehicleResponse.isInspected());
        assertEquals(1000, inspectVehicleResponse.getInspectionPrice());
    }

}
