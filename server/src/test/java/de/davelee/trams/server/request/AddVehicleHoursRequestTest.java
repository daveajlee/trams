package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the AddVehicleHoursRequest class and ensures that its works correctly.
 * @author Dave Lee
 */
public class AddVehicleHoursRequestTest {

    /**
     * Ensure that a AddVehicleHoursRequest class can be correctly instantiated.
     */
    @Test
    public void testCreateRequest( ) {
        AddVehicleHoursRequest addVehicleHoursRequest = new AddVehicleHoursRequest();
        addVehicleHoursRequest.setHours(10);
        addVehicleHoursRequest.setCompany("Lee Transport");
        addVehicleHoursRequest.setFleetNumber("123");
        addVehicleHoursRequest.setDate("21-10-2021");
        assertEquals(10, addVehicleHoursRequest.getHours());
        assertEquals("Lee Transport", addVehicleHoursRequest.getCompany());
        assertEquals("123", addVehicleHoursRequest.getFleetNumber());
        assertEquals("21-10-2021", addVehicleHoursRequest.getDate());
    }

}
