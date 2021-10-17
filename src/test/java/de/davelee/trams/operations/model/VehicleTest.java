package de.davelee.trams.operations.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the VehicleModel class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehicleTest {

    /**
     * Ensure that a VehicleModel class can be correctly instantiated.
     */
    @Test
    public void testBuilderGetterSetterToString ( ) {
        //Test builder
        Vehicle vehicle = Vehicle.builder()
                .fleetNumber("213")
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .seatingCapacity(50)
                .standingCapacity(80)
                .vehicleStatus(VehicleStatus.DELIVERED)
                .modelName("BendyBus 2000")
                .livery("Green with black slide")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Map.of("Registration Number", "W234 DFFKD"))
                .build();
        //Verify the builder functionality through getter methods
        assertEquals("BendyBus 2000", vehicle.getModelName());
        assertEquals(LocalDate.of(2021,3,25), vehicle.getDeliveryDate());
        assertEquals(LocalDate.of(2021,4,25), vehicle.getInspectionDate());
        assertEquals("Green with black slide", vehicle.getLivery());
        assertEquals(50, vehicle.getSeatingCapacity());
        assertEquals(80, vehicle.getStandingCapacity());
        assertEquals(VehicleStatus.DELIVERED, vehicle.getVehicleStatus());
        assertEquals("213", vehicle.getFleetNumber());
        assertEquals("Lee Buses", vehicle.getCompany());
        assertEquals("1/1", vehicle.getAllocatedTour());
        //Verify the toString method
        assertEquals("Vehicle(fleetNumber=213, company=Lee Buses, deliveryDate=2021-03-25, inspectionDate=2021-04-25, seatingCapacity=50, standingCapacity=80, modelName=BendyBus 2000, livery=Green with black slide, vehicleStatus=DELIVERED, allocatedTour=1/1, vehicleType=BUS, typeSpecificInfos={Registration Number=W234 DFFKD})", vehicle.toString());
        //Now use the setter methods
        vehicle.setTypeSpecificInfos(Map.of("Power Mode", "Electric"));
        vehicle.setModelName("BendyTrain 500 Plus");
        vehicle.setDeliveryDate(LocalDate.of(2021,3,31));
        vehicle.setInspectionDate(LocalDate.of(2021,4,5));
        vehicle.setLivery("Red with plus logo");
        vehicle.setSeatingCapacity(54);
        vehicle.setStandingCapacity(82);
        vehicle.setVehicleStatus(VehicleStatus.PURCHASED);
        vehicle.setFleetNumber("214");
        vehicle.setCompany("Lee Buses 2");
        vehicle.setVehicleType(VehicleType.TRAIN);
        //And verify again through the toString methods
        assertEquals("Vehicle(fleetNumber=214, company=Lee Buses 2, deliveryDate=2021-03-31, inspectionDate=2021-04-05, seatingCapacity=54, standingCapacity=82, modelName=BendyTrain 500 Plus, livery=Red with plus logo, vehicleStatus=PURCHASED, allocatedTour=1/1, vehicleType=TRAIN, typeSpecificInfos={Power Mode=Electric})", vehicle.toString());
    }

}
