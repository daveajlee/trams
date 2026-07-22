package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the LoadVehiclesRequest class and ensures that its works correctly.
 * @author Dave Lee
 */
public class LoadVehiclesRequestTest {

    /**
     * Ensure that a LoadVehiclesRequest class can be correctly instantiated.
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
        loadVehicleRequest.setUserHistory(List.of(new VehicleHistoryRequest("25-04-2021", "PURCHASED", "Love on first sight")));
        loadVehicleRequest.setTimesheet(Map.of("01-11-2021", 8));
        LoadVehiclesRequest loadVehiclesRequest = new LoadVehiclesRequest();
        loadVehiclesRequest.setCount(1L);
        loadVehiclesRequest.setLoadVehicleRequests(new LoadVehicleRequest[] { loadVehicleRequest});
        assertEquals(1L, loadVehiclesRequest.getCount());
        assertEquals(1, loadVehiclesRequest.getLoadVehicleRequests().length);
    }

}
