package de.davelee.trams.server.service;

import de.davelee.trams.server.constant.VehicleHistoryReason;
import de.davelee.trams.server.constant.VehicleStatus;
import de.davelee.trams.server.constant.VehicleType;
import de.davelee.trams.server.model.*;
import de.davelee.trams.server.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * This class tests the VehicleService class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Autowired
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    /**
     * Ensure that a vehicle can be added successfully to the mock database.
     */
    @Test
    public void testAddVehicle() {
        //Test tram
        Vehicle vehicle = new Vehicle();
        vehicle.setModelName("Tram 2000 Bi");
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle.setLivery("Green with black slide");
        vehicle.setSeatingCapacity(50);
        vehicle.setStandingCapacity(80);
        vehicle.setVehicleStatus(VehicleStatus.DELIVERED);
        vehicle.setFleetNumber("213");
        vehicle.setCompany("Lee Buses");
        vehicle.setVehicleType(VehicleType.TRAM);
        vehicle.setTypeSpecificInfos(Map.of("bidirectional", "true"));
        Mockito.when(vehicleRepository.insert(vehicle)).thenReturn(vehicle);
        assertTrue(vehicleService.addVehicle(vehicle));
    }

    /**
     * Ensure that invalid vehicles cannot be added.
     */
    @Test
    public void testAddInvalidVehicle() {
        //Test bus without delivery date.
        Vehicle vehicle = new Vehicle();
        vehicle.setModelName("Bus 2025 Plus");
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle.setLivery("Green with black slide");
        vehicle.setSeatingCapacity(50);
        vehicle.setStandingCapacity(80);
        vehicle.setVehicleStatus(VehicleStatus.DELIVERED);
        vehicle.setFleetNumber("213");
        vehicle.setCompany("Lee Buses");
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setTypeSpecificInfos(Map.of("registrationNumber", "HJK234D2"));
        assertFalse(vehicleService.addVehicle(vehicle));
        //Add delivery date but set seating capacity to -20.
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle.setSeatingCapacity(-20);
        assertFalse(vehicleService.addVehicle(vehicle));
        //Set the seating capacity back to 50 but remove registration number.
        vehicle.setSeatingCapacity(50);
        vehicle.setTypeSpecificInfos(Map.of("Feedback", "Bus is great"));
        assertFalse(vehicleService.addVehicle(vehicle));
        //Test train without operating mode.
        Vehicle train = new Vehicle();
        train.setModelName("Elec Train Plus");
        train.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        train.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        train.setLivery("Green with black slide");
        train.setSeatingCapacity(200);
        train.setStandingCapacity(380);
        train.setVehicleStatus(VehicleStatus.DELIVERED);
        train.setFleetNumber("613");
        train.setCompany("Lee Tranport");
        train.setVehicleType(VehicleType.TRAIN);
        train.setTypeSpecificInfos(Map.of("Depot Number", "HJK234D2"));
        assertFalse(vehicleService.addVehicle(train));
    }

    /**
     * Ensure that data can be retrieved from the mock database and supplied as a response.
     */
    @Test
    public void testRetrieveByCompany() {
        //Test data.
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusYears(10));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        Mockito.when(vehicleRepository.findByCompany("Lee Buses")).thenReturn(List.of(tram));
        //Now do actual test.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompany("Lee Buses");
        assertEquals(VehicleType.TRAM, vehicles.get(0).getVehicleType());
    }

    /**
     * Ensure that data can be retrieved by searching for fleet number and company name
     * from the mock database and supplied as a response.
     */
    @Test
    public void testRetrieveVehiclesByCompanyAndFleetNumber() {
        //Test data.
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusDays(7));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
                tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        Mockito.when(vehicleRepository.findByCompanyAndFleetNumberStartsWith("Lee", "21")).thenReturn(List.of(tram));
        //Now do actual test.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee", "21");
        assertEquals(VehicleType.TRAM, vehicles.get(0).getVehicleType());
    }

    /**
     * Ensure that data can be retrieved by searching for fleet number
     * from the mock database and supplied as a response.
     */
    @Test
    public void testRetrieveVehiclesByFleetNumber() {
        //Test data.
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusDays(7));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        tram.addVehicleHistoryEntry(LocalDateTime.of(2021,3,1,0,0), VehicleHistoryReason.PURCHASED, "Purchased!" );
        tram.addVehicleHistoryEntry(LocalDateTime.of(2021,3,25,0,0), VehicleHistoryReason.DELIVERED, "Delivered!");
        tram.addVehicleHistoryEntry(LocalDateTime.of(2021,4,1,0,0), VehicleHistoryReason.INSPECTED, "Inspected!");
        tram.addVehicleHistoryEntry(LocalDateTime.of(2021,10,1,0,0), VehicleHistoryReason.SOLD, "Sold!");
        Mockito.when(vehicleRepository.findByCompanyAndFleetNumberStartsWith("Lee Buses", "21")).thenReturn(List.of(tram));
        //Now do actual test.
        List<Vehicle> vehicleResponseList = vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "21");
        assertEquals(VehicleType.TRAM, vehicleResponseList.get(0).getVehicleType());
        assertEquals(vehicleResponseList.get(0).getVehicleHistoryEntryList().get(0).getVehicleHistoryReason().getText(), "Purchased");
        assertEquals(vehicleResponseList.get(0).getVehicleHistoryEntryList().get(0).getDate(), LocalDateTime.of(2021,3,1,0,0));
        assertEquals(vehicleResponseList.get(0).getVehicleHistoryEntryList().get(0).getComment(), "Purchased!");
        assertNull(vehicleResponseList.get(0).getVehicleHistoryEntryList().get(0).getId());
        assertEquals(vehicleResponseList.get(0).getVehicleHistoryEntryList().get(1).getVehicleHistoryReason().getText(), "Delivered");
        assertEquals(vehicleResponseList.get(0).getVehicleHistoryEntryList().get(2).getVehicleHistoryReason().getText(), "Inspected");
        assertEquals(vehicleResponseList.get(0).getVehicleHistoryEntryList().get(3).getVehicleHistoryReason().getText(), "Sold");
    }

    /**
     * Test case: add timesheet hours.
     * Expected result: true.
     */
    @Test
    public void testAddHoursForDate() {
        //Test data
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusDays(7));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        //Mock important method in repository.
        Mockito.when(vehicleRepository.save(tram)).thenReturn(tram);
        //do actual test.
        assertTrue(vehicleService.addHoursForDate(tram, 8, LocalDateTime.of(2020,3,1,0,0) ));
        assertTrue(vehicleService.addHoursForDate(tram, 1, LocalDateTime.of(2020,3,1,0,0) ));
    }

    /**
     * Test case: add a new history entry.
     * Expected result: true.
     */
    @Test
    public void testAddUserHistoryEntry() {
        //Test data
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusDays(7));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        //Mock important method in repository.
        Mockito.when(vehicleRepository.save(tram)).thenReturn(tram);
        //do actual test.
        assertTrue(vehicleService.addVehicleHistoryEntry(tram, LocalDateTime.of(2020,3,1,0,0), VehicleHistoryReason.PURCHASED, "Welcome to the company!"));
        assertTrue(vehicleService.addVehicleHistoryEntry(tram, LocalDateTime.of(2020,3,31,0,0), VehicleHistoryReason.DELIVERED, "Vehicle has been delivered!"));
    }

    /**
     * Test case: sell the supplied vehicle.
     * Expected result: selling price of vehicle.
     */
    @Test
    public void testSellVehicle () {
        //Test data
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusDays(7));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        //Mock important methods in Mockito.
        Mockito.when(vehicleRepository.save(tram)).thenReturn(tram);
        //Do actual test.
        BigDecimal sellingPrice = vehicleService.sellVehicle(tram);
        assertEquals(tram.getVehicleType().getPurchasePrice(), sellingPrice);
        //Now mock an error and perform test again.
        Mockito.when(vehicleRepository.save(tram)).thenReturn(null);
        BigDecimal sellingPrice2 = vehicleService.sellVehicle(tram);
        assertEquals(BigDecimal.ZERO, sellingPrice2);
    }

    /**
     * Test case: inspect the supplied vehicle.
     * Expected result: inspection price of vehicle.
     */
    @Test
    public void testInspectVehicle () {
        //Test data
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusDays(7));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        //Mock important methods in Mockito.
        Mockito.when(vehicleRepository.save(tram)).thenReturn(tram);
        //Do actual test.
        BigDecimal inspectionPrice = vehicleService.inspectVehicle(tram);
        assertEquals(tram.getVehicleType().getInspectionPrice(), inspectionPrice);
        //Now mock an error and perform test again.
        Mockito.when(vehicleRepository.save(tram)).thenReturn(null);
        BigDecimal inspectionPrice2 = vehicleService.inspectVehicle(tram);
        assertEquals(BigDecimal.ZERO, inspectionPrice2);
    }

    /**
     * Test case: allocate a tour to the supplied vehicle.
     * Expected result: the allocation works successfully.
     */
    @Test
    public void testAllocateVehicle () {
        //Test data
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusDays(7));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        //Mock important methods in Mockito.
        Mockito.when(vehicleRepository.save(tram)).thenReturn(tram);
        //Do actual test.
        assertTrue(vehicleService.allocateTourToVehicle(tram, "1", "1"));
    }

    /**
     * Verify that a vehicle can be deleted from the database correctly.
     */
    @Test
    public void testDeleteVehicles( ) {
        //Mock important method in repository.
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusYears(10));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        Mockito.when(vehicleRepository.findByCompany("Lee Buses")).thenReturn(List.of(tram));
        //Do test.
        vehicleService.deleteVehicles("Lee Buses");
    }

    /**
     * Verify that vehicles can be retrieved according to their allocated route.
     */
    @Test
    public void testRetrieveAllocatedVehiclesForRoute ( ) {
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusYears(10));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setAllocatedRoute("1");
        tram.setAllocatedTour("2");
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        Vehicle tram2 = new Vehicle();
        tram2.setModelName("Tram 2000 Bi");
        tram2.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram2.setInspectionDate(LocalDateTime.now().minusYears(10));
        tram2.setLivery("Green with black slide");
        tram2.setSeatingCapacity(50);
        tram2.setStandingCapacity(80);
        tram2.setVehicleStatus(VehicleStatus.DELIVERED);
        tram2.setFleetNumber("214");
        tram2.setCompany("Lee Buses");
        tram2.setVehicleType(VehicleType.TRAM);
        tram2.setAllocatedRoute("1");
        tram2.setAllocatedTour("1");
        tram2.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        //Mock important method in repository.
        Mockito.when(vehicleRepository.findByCompanyAndAllocatedRoute("Lee Buses", "1")).thenReturn(List.of(tram,
                tram2));
        //Do test.
        assertEquals(2, vehicleService.retrieveVehiclesByCompanyAndAllocatedRoute("Lee Buses", "1").size());
    }

    /**
     * Verify that a vehicle can be retrieved according to their allocated route and tour.
     */
    @Test
    public void testRetrieveAllocatedVehicle ( ) {
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusYears(10));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setAllocatedRoute("1");
        tram.setAllocatedTour("2");
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        //Mock important method in repository.
        Mockito.when(vehicleRepository.findByCompanyAndAllocatedRouteAndAllocatedTour("Lee Buses", "1", "2")).thenReturn(List.of(tram));
        //Do test.
        assertEquals(1, vehicleService.retrieveVehiclesByCompanyAndAllocatedRouteAndAllocatedTour("Lee Buses", "1", "2").size());
    }

    /**
     * Verify that the delay of a vehicle can be adjusted appropriately.
     */
    @Test
    public void testAdjustDelayVehicle ( ) {
        Vehicle tram = new Vehicle();
        tram.setModelName("Tram 2000 Bi");
        tram.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.now().minusYears(10));
        tram.setLivery("Green with black slide");
        tram.setSeatingCapacity(50);
        tram.setStandingCapacity(80);
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setFleetNumber("213");
        tram.setCompany("Lee Buses");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setAllocatedRoute("1");
        tram.setAllocatedTour("2");
        tram.setTypeSpecificInfos(Map.of("Bidirectional", "true"));
        //Mock important method in repository.
        Mockito.when(vehicleRepository.save(any())).thenReturn(tram);
        //Do test.
        assertEquals(6, vehicleService.adjustVehicleDelay(tram, 2));
        assertEquals(3, vehicleService.adjustVehicleDelay(tram, -3));
        assertEquals(0, vehicleService.adjustVehicleDelay(tram, -4));
        //Do test if database does not work.
        Mockito.when(vehicleRepository.save(any())).thenReturn(null);
        assertEquals(Integer.MIN_VALUE, vehicleService.adjustVehicleDelay(tram, -1));
    }

}
