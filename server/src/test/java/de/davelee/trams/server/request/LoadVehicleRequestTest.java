package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the LoadVehicleRequest class and ensures that its works correctly.
 * @author Dave Lee
 */
public class LoadVehicleRequestTest {

    /**
     * Ensure that a LoadVehicleRequest class can be correctly instantiated.
     */
    @Test
    public void testCreateRequest( ) {
        LoadVehicleRequest loadVehicleRequest = new LoadVehicleRequest();
        loadVehicleRequest.setFleetNumber("1213");
        loadVehicleRequest.setCompany("Lee Buses");
        loadVehicleRequest.setDeliveryDate("25-04-2021");
        loadVehicleRequest.setInspectionDate("25-05-2021");
        loadVehicleRequest.setVehicleType("Tram");
        loadVehicleRequest.setVehicleStatus("DELIVERED");
        loadVehicleRequest.setSeatingCapacity(50);
        loadVehicleRequest.setStandingCapacity(80);
        loadVehicleRequest.setModelName("Bendy Bus 2000");
        loadVehicleRequest.setLivery("Blue with orange text");
        loadVehicleRequest.setAllocatedTour("1/2");
        loadVehicleRequest.setAdditionalTypeInformationMap(Map.of("Bidirectional", "true"));
        loadVehicleRequest.setUserHistory(List.of(VehicleHistoryRequest.builder().vehicleHistoryReason("PURCHASED")
                        .comment("Love on first sight").date("25-04-2021").build()));
        loadVehicleRequest.setTimesheet(Map.of("01-11-2021", 8));
        assertEquals("1213", loadVehicleRequest.getFleetNumber());
        assertEquals("Lee Buses", loadVehicleRequest.getCompany());
        assertEquals("25-04-2021", loadVehicleRequest.getDeliveryDate());
        assertEquals("25-05-2021", loadVehicleRequest.getInspectionDate());
        assertEquals("Tram", loadVehicleRequest.getVehicleType());
        assertEquals("DELIVERED", loadVehicleRequest.getVehicleStatus());
        assertEquals(50, loadVehicleRequest.getSeatingCapacity());
        assertEquals(80, loadVehicleRequest.getStandingCapacity());
        assertEquals("Bendy Bus 2000", loadVehicleRequest.getModelName());
        assertEquals("Blue with orange text", loadVehicleRequest.getLivery());
        assertEquals("1/2", loadVehicleRequest.getAllocatedTour());
        assertEquals(1, loadVehicleRequest.getAdditionalTypeInformationMap().size());
        assertEquals(1, loadVehicleRequest.getUserHistory().size());
    }

}
