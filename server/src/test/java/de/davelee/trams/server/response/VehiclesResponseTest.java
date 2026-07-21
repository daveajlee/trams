package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the VehicleResponses class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehiclesResponseTest {

    @Test
    public void testSetters() {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setLivery("Green with red text");
        vehicleResponse.setFleetNumber("213");
        vehicleResponse.setAllocatedTour("1/1");
        vehicleResponse.setVehicleType("Bus");
        vehicleResponse.setAdditionalTypeInformationMap(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicleResponse.setInspectionStatus("Inspected");
        vehicleResponse.setNextInspectionDueInDays(100);
        vehicleResponse.setCompany("Lee Buses");
        VehiclesResponse vehiclesResponse = new VehiclesResponse();
        vehiclesResponse.setCount(1L);
        vehiclesResponse.setVehicleResponses(new VehicleResponse[] {
                vehicleResponse
        });
        assertEquals(1L, vehiclesResponse.getCount());
        assertEquals(1, vehiclesResponse.getVehicleResponses().length);
    }

}
