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
        LoadVehiclesRequest loadVehiclesRequest = new LoadVehiclesRequest();
        loadVehiclesRequest.setCount(1L);
        loadVehiclesRequest.setLoadVehicleRequests(new LoadVehicleRequest[] { LoadVehicleRequest.builder()
                .fleetNumber("1213")
                .company("Lee Buses")
                .deliveryDate("25-04-2021")
                .inspectionDate("25-05-2021")
                .vehicleType("Tram")
                .vehicleStatus("DELIVERED")
                .seatingCapacity(50)
                .standingCapacity(80)
                .modelName("Bendy Bus 2000")
                .livery("Blue with orange text")
                .allocatedTour("1/2")
                .additionalTypeInformationMap(Map.of("Bidirectional", "true"))
                .userHistory(List.of(VehicleHistoryRequest.builder().vehicleHistoryReason("PURCHASED")
                        .comment("Love on first sight").date("25-04-2021").build()))
                .timesheet(Map.of("01-11-2021", 8))
                .build()});
        assertEquals(1L, loadVehiclesRequest.getCount());
        assertEquals(1, loadVehiclesRequest.getLoadVehicleRequests().length);
    }

}
