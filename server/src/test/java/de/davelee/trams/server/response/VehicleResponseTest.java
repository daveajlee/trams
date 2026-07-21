package de.davelee.trams.server.response;

import de.davelee.trams.server.constant.VehicleType;
import de.davelee.trams.server.utils.VehicleUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the VehicleResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehicleResponseTest {

    /**
     * Ensure that a VehicleResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setLivery("Green with red text");
        vehicleResponse.setFleetNumber("213");
        vehicleResponse.setAllocatedRoute("1A");
        vehicleResponse.setAllocatedTour("1");
        vehicleResponse.setVehicleType("Bus");
        vehicleResponse.setAdditionalTypeInformationMap(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicleResponse.setInspectionStatus("Inspected");
        vehicleResponse.setNextInspectionDueInDays(100);
        vehicleResponse.setCompany("Lee Buses");
        assertEquals("Green with red text", vehicleResponse.getLivery());
        assertEquals("213", vehicleResponse.getFleetNumber());
        assertEquals("1A", vehicleResponse.getAllocatedRoute());
        assertEquals("1", vehicleResponse.getAllocatedTour());
        assertEquals("Bus", vehicleResponse.getVehicleType());
        assertEquals("XXX2 BBB", vehicleResponse.getAdditionalTypeInformationMap().get("registrationNumber"));
        assertEquals("Inspected", vehicleResponse.getInspectionStatus());
        assertEquals(100, vehicleResponse.getNextInspectionDueInDays());
        vehicleResponse.setLivery("Blue with orange text");
        vehicleResponse.setFleetNumber("1213");
        vehicleResponse.setAllocatedRoute("1");
        vehicleResponse.setAllocatedTour("2");
        vehicleResponse.setVehicleType("Tram");
        vehicleResponse.setAdditionalTypeInformationMap(Collections.singletonMap("Bidirectional", "true"));
        vehicleResponse.setInspectionStatus("Inspection Due!");
        vehicleResponse.setNextInspectionDueInDays(0);
        vehicleResponse.setDeliveryDate("25-04-2021");
        vehicleResponse.setInspectionDate("25-05-2021");
        vehicleResponse.setVehicleStatus("DELIVERED");
        vehicleResponse.setModelName("Bendy Bus 2000");
        vehicleResponse.setPurchasePrice(VehicleType.TRAM.getPurchasePrice().doubleValue());
        vehicleResponse.setUserHistory(List.of(new VehicleHistoryResponse("25-04-2021", "PURCHASED", "Love on first sight")));
        vehicleResponse.setTimesheet(Map.of("01-11-2021", 8));
        assertEquals(1, VehicleUtils.convertTimesheetToResponse(Map.of(LocalDateTime.of(2021,11,1,0,0), 8)).size());
        assertEquals("VehicleResponse(fleetNumber=1213, company=Lee Buses, deliveryDate=25-04-2021, inspectionDate=25-05-2021, vehicleType=Tram, purchasePrice=700000.0, vehicleStatus=DELIVERED, seatingCapacity=0, standingCapacity=0, modelName=Bendy Bus 2000, livery=Blue with orange text, allocatedRoute=1, allocatedTour=2, delayInMinutes=0, inspectionStatus=Inspection Due!, nextInspectionDueInDays=0, additionalTypeInformationMap={Bidirectional=true}, userHistory=[VehicleHistoryResponse(date=25-04-2021, vehicleHistoryReason=PURCHASED, comment=Love on first sight)], timesheet={01-11-2021=8})", vehicleResponse.toString());
    }

}
