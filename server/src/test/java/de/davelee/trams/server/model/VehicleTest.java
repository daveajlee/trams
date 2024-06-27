package de.davelee.trams.server.model;

import de.davelee.trams.server.constant.VehicleStatus;
import de.davelee.trams.server.constant.VehicleType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the Vehicle class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehicleTest {

    /**
     * Ensure that a Vehicle class can be correctly instantiated.
     */
    @Test
    public void testBuilderGetterSetterToString ( ) {
        //Test builder
        Vehicle vehicle = Vehicle.builder()
                .fleetNumber("213")
                .company("Lee Buses")
                .deliveryDate(LocalDateTime.of(2021,3,25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .seatingCapacity(50)
                .standingCapacity(80)
                .vehicleStatus(VehicleStatus.DELIVERED)
                .modelName("BendyBus 2000")
                .livery("Green with black slide")
                .allocatedRoute("1")
                .allocatedTour("1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Map.of("registrationNumber", "W234 DFFKD"))
                .timesheet(Map.of(LocalDateTime.of(2021,10,29,0,0), 16))
                .build();
        //Verify the builder functionality through getter methods
        assertEquals("BendyBus 2000", vehicle.getModelName());
        assertEquals(LocalDateTime.of(2021,3,25,0,0), vehicle.getDeliveryDate());
        assertEquals(LocalDateTime.of(2021,4,25,0,0), vehicle.getInspectionDate());
        assertEquals("Green with black slide", vehicle.getLivery());
        assertEquals(50, vehicle.getSeatingCapacity());
        assertEquals(80, vehicle.getStandingCapacity());
        assertEquals(VehicleStatus.DELIVERED, vehicle.getVehicleStatus());
        assertEquals("213", vehicle.getFleetNumber());
        assertEquals("Lee Buses", vehicle.getCompany());
        assertEquals("1", vehicle.getAllocatedRoute());
        assertEquals("1", vehicle.getAllocatedTour());
        assertEquals(1, vehicle.getTimesheet().size());
        //Verify the toString method
        assertEquals("Vehicle(id=null, fleetNumber=213, company=Lee Buses, deliveryDate=2021-03-25T00:00, inspectionDate=2021-04-25T00:00, seatingCapacity=50, standingCapacity=80, modelName=BendyBus 2000, livery=Green with black slide, vehicleStatus=DELIVERED, allocatedRoute=1, allocatedTour=1, delayInMinutes=0, vehicleType=BUS, typeSpecificInfos={registrationNumber=W234 DFFKD}, timesheet={2021-10-29T00:00=16}, vehicleHistoryEntryList=null)", vehicle.toString());
        //Now use the setter methods
        vehicle.setTypeSpecificInfos(Map.of("Power Mode", "Electric"));
        vehicle.setModelName("BendyTrain 500 Plus");
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,31,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,5,0,0));
        vehicle.setLivery("Red with plus logo");
        vehicle.setSeatingCapacity(54);
        vehicle.setStandingCapacity(82);
        vehicle.setVehicleStatus(VehicleStatus.PURCHASED);
        vehicle.setFleetNumber("214");
        vehicle.setCompany("Lee Buses 2");
        vehicle.setVehicleType(VehicleType.TRAIN);
        vehicle.setTimesheet(Map.of(LocalDateTime.of(2021,10,26,0,0), 14));
        //And verify again through the toString methods
        assertEquals("Vehicle(id=null, fleetNumber=214, company=Lee Buses 2, deliveryDate=2021-03-31T00:00, inspectionDate=2021-04-05T00:00, seatingCapacity=54, standingCapacity=82, modelName=BendyTrain 500 Plus, livery=Red with plus logo, vehicleStatus=PURCHASED, allocatedRoute=1, allocatedTour=1, delayInMinutes=0, vehicleType=TRAIN, typeSpecificInfos={Power Mode=Electric}, timesheet={2021-10-26T00:00=14}, vehicleHistoryEntryList=null)", vehicle.toString());
    }

}
