package de.davelee.trams.server.controller;

import de.davelee.trams.server.constant.VehicleType;
import de.davelee.trams.server.model.Vehicle;
import de.davelee.trams.server.request.*;
import de.davelee.trams.server.response.InspectVehicleResponse;
import de.davelee.trams.server.response.PurchaseVehicleResponse;
import de.davelee.trams.server.response.SellVehicleResponse;
import de.davelee.trams.server.response.VehicleHoursResponse;
import de.davelee.trams.server.service.VehicleService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * This class tests the VehiclsController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    /**
     * Test the purchase endpoint of this controller with valid requests.
     */
    @Test
    public void testValidPurchaseVehicle() {
        //Mock important methods
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber(eq("Lee Transport"), any())).thenReturn(null);
        Mockito.when(vehicleService.addVehicle(any())).thenReturn(true);
        //Purchase valid bus
        ResponseEntity<PurchaseVehicleResponse> responseEntity = vehicleController.purchaseVehicle(PurchaseVehicleRequest.builder()
                        .additionalTypeInformationMap(Map.of("registrationNumber", "XXX2 BBB"))
                        .modelName("Bendy Bus 2000")
                        .vehicleType("BUS")
                        .seatingCapacity(50)
                        .standingCapacity(100)
                        .company("Lee Transport")
                        .livery("Green with red text")
                        .fleetNumber("213")
                        .build());
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().isPurchased());
        assertEquals(200000, responseEntity.getBody().getPurchasePrice());
        //Purchase valid train
        ResponseEntity<PurchaseVehicleResponse> responseEntity2 = vehicleController.purchaseVehicle(PurchaseVehicleRequest.builder()
                .additionalTypeInformationMap(Map.of("Operating Mode", "Electric"))
                .modelName("Elec Train 2000")
                .vehicleType("TRAIN")
                .seatingCapacity(130)
                .standingCapacity(200)
                .company("Lee Transport")
                .livery("Green with red text")
                .fleetNumber("2300")
                .build());
        assertEquals(200, responseEntity2.getStatusCodeValue());
        assertTrue(responseEntity2.getBody().isPurchased());
        assertEquals(1000000, responseEntity2.getBody().getPurchasePrice());
        //Purchase valid tram
        PurchaseVehicleRequest purchaseVehicleRequest = new PurchaseVehicleRequest();
        purchaseVehicleRequest.setAdditionalTypeInformationMap(Map.of("Operating Mode", "Electric"));
        purchaseVehicleRequest.setModelName("Elec Tram 2000");
        purchaseVehicleRequest.setVehicleType("TRAM");
        purchaseVehicleRequest.setSeatingCapacity(50);
        purchaseVehicleRequest.setStandingCapacity(130);
        purchaseVehicleRequest.setCompany("Lee Transport");
        purchaseVehicleRequest.setLivery("Green with red text");
        purchaseVehicleRequest.setFleetNumber("3300");
        ResponseEntity<PurchaseVehicleResponse> responseEntity3 = vehicleController.purchaseVehicle(purchaseVehicleRequest);
        assertEquals(200, responseEntity3.getStatusCodeValue());
        assertTrue(responseEntity3.getBody().isPurchased());
        assertEquals(700000, responseEntity3.getBody().getPurchasePrice());
    }

    /**
     * Test the purchase endpoint of this controller with invalid requests.
     */
    @Test
    public void testInvalidPurchaseVehicle() {
        //Mock important methods
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("213")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDateTime.of(2021,3,25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .build()));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "214")).thenReturn(null);
        Mockito.when(vehicleService.addVehicle(any())).thenReturn(false);
        //Purchase bus with missing company.
        ResponseEntity<PurchaseVehicleResponse> responseEntity = vehicleController.purchaseVehicle(PurchaseVehicleRequest.builder()
                .additionalTypeInformationMap(Map.of("registrationNumber", "XXX2 BBB"))
                .modelName("Bendy Bus 2000")
                .vehicleType("BUS")
                .seatingCapacity(50)
                .standingCapacity(100)
                .livery("Green with red text")
                .fleetNumber("213")
                .build());
        assertEquals(400, responseEntity.getStatusCodeValue());
        //Purchase bus which already exists.
        ResponseEntity<PurchaseVehicleResponse> responseEntity2 = vehicleController.purchaseVehicle(PurchaseVehicleRequest.builder()
                .additionalTypeInformationMap(Map.of("registrationNumber", "XXX2 BBB"))
                .modelName("Bendy Bus 2000")
                .vehicleType("BUS")
                .seatingCapacity(50)
                .standingCapacity(100)
                .livery("Green with red text")
                .company("Lee Buses")
                .fleetNumber("213")
                .build());
        assertEquals(409, responseEntity2.getStatusCodeValue());
        //Purchase bus which does not exist but does not validate and cannot be added to the database.
        ResponseEntity<PurchaseVehicleResponse> responseEntity3 = vehicleController.purchaseVehicle(PurchaseVehicleRequest.builder()
                .additionalTypeInformationMap(Map.of("registrationNumber", "XXX2 BBB"))
                .modelName("Bendy Bus 2000")
                .vehicleType("BUS")
                .seatingCapacity(50)
                .standingCapacity(100)
                .livery("Green with red text")
                .company("Lee Buses")
                .fleetNumber("214")
                .build());
        assertEquals(500, responseEntity3.getStatusCodeValue());
    }

    /**
     * Test the addHoursForDate endpoint of this controller.
     */
    @Test
    public void testAddHoursForDate() {
        //Mock important methods in the vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("213")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDateTime.of(2021,3,25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .build()));
        Mockito.when(vehicleService.addHoursForDate(any(), eq(14), any() )).thenReturn(true);
        //Do test with a valid request.
        ResponseEntity<Void> responseEntity = vehicleController.addHoursForDate(
                AddVehicleHoursRequest.builder()
                        .date("21-10-2021")
                        .hours(14)
                        .company("Lee Buses")
                        .fleetNumber("213")
                        .build());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //Do test with a bad request.
        ResponseEntity<Void> responseEntity2 = vehicleController.addHoursForDate(
                AddVehicleHoursRequest.builder()
                        .date("")
                        .hours(-1)
                        .company("Lee Buses")
                        .fleetNumber("213")
                        .build());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Do test with 0 results from db.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "212")).thenReturn(Lists.newArrayList());
        ResponseEntity<Void> responseEntity3 = vehicleController.addHoursForDate(
                AddVehicleHoursRequest.builder()
                        .date("21-10-2021")
                        .hours(14)
                        .company("Lee Buses")
                        .fleetNumber("212")
                        .build());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
        ResponseEntity<Void> responseEntity4 = vehicleController.addHoursForDate(
                AddVehicleHoursRequest.builder()
                        .date("210-10-2021")
                        .hours(14)
                        .company("Lee Buses")
                        .fleetNumber("213")
                        .build());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
    }

    /**
     * Test the getHoursForDate endpoint of this controller.
     */
    @Test
    public void testGetHoursForDate() {
        //Mock important methods in the vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("213")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDateTime.of(2021,3,25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14))
                .build()));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "214")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("214")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.TRAM)
                .typeSpecificInfos(Collections.singletonMap("Bidirectional", "true"))
                .company("Lee Buses")
                .deliveryDate(LocalDateTime.of(2021,3,25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14))
                .build()));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "215")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("215")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Collections.singletonMap("Power Mode", "Electric"))
                .company("Lee Buses")
                .deliveryDate(LocalDateTime.of(2021,3,25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14))
                .build()));
        //Do test with a valid request for each vehicle Type.
        ResponseEntity<VehicleHoursResponse> responseEntity = vehicleController.getHoursForDate("Lee Buses", "213", "21-10-2021 00:00");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().getNumberOfHoursAvailable());
        assertEquals(14, responseEntity.getBody().getNumberOfHoursSoFar());
        assertFalse(responseEntity.getBody().isMaximumHoursReached());
        ResponseEntity<VehicleHoursResponse> responseEntityTrain = vehicleController.getHoursForDate("Lee Buses", "215", "21-10-2021 00:00");
        assertEquals(HttpStatus.OK, responseEntityTrain.getStatusCode());
        assertEquals(7, responseEntityTrain.getBody().getNumberOfHoursAvailable());
        assertEquals(14, responseEntityTrain.getBody().getNumberOfHoursSoFar());
        assertFalse(responseEntityTrain.getBody().isMaximumHoursReached());
        ResponseEntity<VehicleHoursResponse> responseEntityTram = vehicleController.getHoursForDate("Lee Buses", "214", "21-10-2021 00:00");
        assertEquals(HttpStatus.OK, responseEntityTram.getStatusCode());
        assertEquals(6, responseEntityTram.getBody().getNumberOfHoursAvailable());
        assertEquals(14, responseEntityTram.getBody().getNumberOfHoursSoFar());
        assertFalse(responseEntity.getBody().isMaximumHoursReached());
        //Do test with a valid request but no hours.
        ResponseEntity<VehicleHoursResponse> responseEntity1 = vehicleController.getHoursForDate("Lee Buses", "213", "22-10-2021 00:00");
        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
        assertEquals(16, responseEntity1.getBody().getNumberOfHoursAvailable());
        assertEquals(0, responseEntity1.getBody().getNumberOfHoursSoFar());
        assertFalse(responseEntity1.getBody().isMaximumHoursReached());
        //Do test with a bad request.
        ResponseEntity<VehicleHoursResponse> responseEntity2 = vehicleController.getHoursForDate("", "213", "21-10-2021 00:00");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Do test with 0 results from db.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "212")).thenReturn(Lists.newArrayList());
        ResponseEntity<VehicleHoursResponse> responseEntity3 = vehicleController.getHoursForDate("Lee Buses", "212", "21-10-2021 00:00");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
    }

    /**
     * Test case: add a history entry for the specified vehicle.
     * Expected Result: forbidden or no content or ok depending on request.
     */
    @Test
    public void testAddHistoryEntry() {
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("213")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDateTime.of(2021,3,25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14))
                .build()));
        Mockito.when(vehicleService.addVehicleHistoryEntry(any(), any(), any(), anyString())).thenReturn(true);
        //Perform tests - valid request
        AddHistoryEntryRequest addHistoryEntryRequest = AddHistoryEntryRequest.builder()
                .fleetNumber("213")
                .company("Lee Buses")
                .date("01-03-2020")
                .reason("PURCHASED")
                .comment("Welcome to the company!")
                .build();
        ResponseEntity<Void> responseEntity = vehicleController.addHistoryEntry(addHistoryEntryRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
        //Perform tests - fleet number missing
        AddHistoryEntryRequest addHistoryEntryRequest2 = AddHistoryEntryRequest.builder()
                .fleetNumber("")
                .company("Example Company")
                .date("01-03-2020")
                .reason("JOINED")
                .comment("Welcome to the company!")
                .build();
        ResponseEntity<Void> responseEntity2 = vehicleController.addHistoryEntry(addHistoryEntryRequest2);
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        //Perform tests - no vehicle
        AddHistoryEntryRequest addHistoryEntryRequest3 = AddHistoryEntryRequest.builder()
                .fleetNumber("210")
                .company("Example Company")
                .date("01-03-2020")
                .reason("JOINED")
                .comment("Welcome to the company!")
                .build();
        ResponseEntity<Void> responseEntity3 = vehicleController.addHistoryEntry(addHistoryEntryRequest3);
        assertTrue(responseEntity3.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
    }

    /**
     * Test case: sell a vehicle.
     * Expected result: the selling price is returned.
     */
    @Test
    public void testSellVehicle() {
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("213")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDateTime.of(2021,3,25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14))
                .build()));
        Mockito.when(vehicleService.sellVehicle(any())).thenReturn(VehicleType.BUS.getPurchasePrice());
        //Perform the test.
        ResponseEntity<SellVehicleResponse> responseEntity = vehicleController.sellVehicle(SellVehicleRequest.builder()
                .company("Lee Buses")
                .fleetNumber("213")
                .build());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSold());
        assertEquals(VehicleType.BUS.getPurchasePrice().doubleValue(), responseEntity.getBody().getSoldPrice());
        //Perform an unsuccessful test with bad request.
        SellVehicleRequest sellVehicleRequest = new SellVehicleRequest();
        sellVehicleRequest.setCompany("Lee Buses");
        ResponseEntity<SellVehicleResponse> responseEntity2 = vehicleController.sellVehicle(sellVehicleRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Perform an unsuccessful test with no content.
        sellVehicleRequest.setFleetNumber("214");
        ResponseEntity<SellVehicleResponse> responseEntity3 = vehicleController.sellVehicle(sellVehicleRequest);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
    }

    /**
     * Test case: inspect a vehicle.
     * Expected result: the inspection price is returned.
     */
    @Test
    public void testInspectVehicle() {
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("223")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"))
                .company("Lee Transport")
                .deliveryDate(LocalDateTime.of(2021, 3, 25,0,0))
                .inspectionDate(LocalDateTime.of(2021, 4, 25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021, 10, 21,0,0), 14))
                .build()));
        Mockito.when(vehicleService.inspectVehicle(any())).thenReturn(VehicleType.TRAIN.getInspectionPrice());
        //Perform the test.
        ResponseEntity<InspectVehicleResponse> responseEntity = vehicleController.inspectVehicle(InspectVehicleRequest.builder()
                .company("Lee Transport")
                .fleetNumber("223")
                .build());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isInspected());
        assertEquals(VehicleType.TRAIN.getInspectionPrice().doubleValue(), responseEntity.getBody().getInspectionPrice());
        //Perform an unsuccessful test with bad request.
        InspectVehicleRequest inspectVehicleRequest = new InspectVehicleRequest();
        inspectVehicleRequest.setCompany("Lee Buses");
        ResponseEntity<InspectVehicleResponse> responseEntity2 = vehicleController.inspectVehicle(inspectVehicleRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Perform an unsuccessful test with no content.
        inspectVehicleRequest.setFleetNumber("214");
        ResponseEntity<InspectVehicleResponse> responseEntity3 = vehicleController.inspectVehicle(inspectVehicleRequest);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
        //Perform a further test with bus to ensure full test coverage.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "233")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("233")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("registrationNumber", "HFJK23D"))
                .company("Lee Transport")
                .deliveryDate(LocalDateTime.of(2021, 3, 25,0,0))
                .inspectionDate(LocalDateTime.of(2021, 4, 25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021, 10, 21,0,0), 14))
                .build()));
        Mockito.when(vehicleService.inspectVehicle(any())).thenReturn(VehicleType.BUS.getInspectionPrice());
        //Perform the test.
        ResponseEntity<InspectVehicleResponse> responseEntity4 = vehicleController.inspectVehicle(InspectVehicleRequest.builder()
                .company("Lee Transport")
                .fleetNumber("233")
                .build());
        assertEquals(HttpStatus.OK, responseEntity4.getStatusCode());
        assertTrue(responseEntity4.getBody().isInspected());
        assertEquals(VehicleType.BUS.getInspectionPrice().doubleValue(), responseEntity4.getBody().getInspectionPrice());
    }

    /**
     * Test case: allocate and remove allocations for vehicles.
     * Expected result: the allocations are allowed or removed as appropriate.
     */
    @Test
    public void testAllocations() {
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("223")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"))
                .company("Lee Transport")
                .deliveryDate(LocalDateTime.of(2021, 3, 25,0,0))
                .inspectionDate(LocalDateTime.of(2021, 4, 25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021, 10, 21,0,0), 14))
                .build()));
        Mockito.when(vehicleService.allocateTourToVehicle(any(), any(), any())).thenReturn(true);
        //Perform the actual test.
        ResponseEntity responseEntity = vehicleController.allocateVehicle(AllocateVehicleRequest.builder()
                .company("Lee Transport")
                .fleetNumber("223")
                .allocatedTour("1/1")
                .build());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //Remove fleet number.
        ResponseEntity responseEntity2 = vehicleController.allocateVehicle(AllocateVehicleRequest.builder()
                .company("Lee Transport")
                .allocatedTour("1/1")
                .build());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Test with vehicle that does not exist
        AllocateVehicleRequest allocateVehicleRequest = new AllocateVehicleRequest();
        allocateVehicleRequest.setAllocatedTour("1/1");
        allocateVehicleRequest.setFleetNumber("233");
        allocateVehicleRequest.setCompany("Lee Transport");
        ResponseEntity responseEntity3 = vehicleController.allocateVehicle(allocateVehicleRequest);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
        //Test remove actual allocation.
        ResponseEntity responseEntity4 = vehicleController.removeVehicleAllocation(RemoveVehicleRequest.builder()
                .company("Lee Transport")
                .fleetNumber("223")
                .build());
        assertEquals(HttpStatus.OK, responseEntity4.getStatusCode());
        //Remove fleet number.
        ResponseEntity responseEntity5 = vehicleController.removeVehicleAllocation(RemoveVehicleRequest.builder()
                .company("Lee Transport")
                .build());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity5.getStatusCode());
        //Test with vehicle that does not exist
        RemoveVehicleRequest removeVehicleRequest = new RemoveVehicleRequest();
        removeVehicleRequest.setFleetNumber("233");
        removeVehicleRequest.setCompany("Lee Transport");
        ResponseEntity responseEntity6 = vehicleController.removeVehicleAllocation(removeVehicleRequest);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity6.getStatusCode());
    }

    /**
     * Test case: retrieve vehicles by allocations.
     * Expected result: the vehicles are retrieved as appropriate.
     */
    @Test
    public void testRetrieveAllocations() {
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndAllocatedRouteAndAllocatedTour("Lee Transport", "1", "2")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("223")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"))
                .company("Lee Transport")
                .allocatedTour("1/2")
                .deliveryDate(LocalDateTime.of(2021, 3, 25,0,0))
                .inspectionDate(LocalDateTime.of(2021,4,25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021, 10, 21,0,0), 14))
                .build()));
        //Attempt to retrieve the vehicle.
        assertEquals(HttpStatus.OK, vehicleController.getAllocatedVehicle("Lee Transport", "1", "2").getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, vehicleController.getAllocatedVehicle("Lee Transport", "1", "1").getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, vehicleController.getAllocatedVehicle("Lee Transport", "", "").getStatusCode());
    }

    /**
     * Test case: adjust delay of a vehicle.
     * Expected result: the delay of the vehicle is adjusted as appropriate.
     */
    @Test
    public void testDelayVehicle() {
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("223")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"))
                .company("Lee Transport")
                .allocatedTour("1/2")
                .delayInMinutes(5)
                .deliveryDate(LocalDateTime.of(2021, 3, 25,0,0))
                .inspectionDate(LocalDateTime.of(2021, 4, 25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021, 10, 21,0,0), 14))
                .build()));
        //Attempt to adjust delay.
        assertEquals(HttpStatus.OK, vehicleController.adjustVehicleDelay(AdjustVehicleDelayRequest.builder().delayInMinutes(-3).company("Lee Transport").fleetNumber("223").build()).getStatusCode());
        AdjustVehicleDelayRequest adjustVehicleDelayRequest = new AdjustVehicleDelayRequest();
        adjustVehicleDelayRequest.setDelayInMinutes(4);
        adjustVehicleDelayRequest.setCompany("Lee Transport");
        adjustVehicleDelayRequest.setFleetNumber("224");
        assertEquals(HttpStatus.NO_CONTENT, vehicleController.adjustVehicleDelay(adjustVehicleDelayRequest).getStatusCode());
        adjustVehicleDelayRequest.setFleetNumber("");
        assertEquals(HttpStatus.BAD_REQUEST, vehicleController.adjustVehicleDelay(adjustVehicleDelayRequest).getStatusCode());
    }

    /**
     * Test case: calculate current value of a vehicle.
     * Expected result: the value of the vehicle is calculated as appropriate.
     */
    @Test
    public void testVehicleValue() {
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("223")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"))
                .company("Lee Transport")
                .allocatedTour("1/2")
                .delayInMinutes(5)
                .deliveryDate(LocalDateTime.of(2021, 3, 25,0,0))
                .inspectionDate(LocalDateTime.of(2021, 4, 25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021, 10, 21,0,0), 14))
                .build()));
        //Attempt to adjust delay.
        assertEquals(1000000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2021").getBody().getValue());
        assertEquals(950000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2022").getBody().getValue());
        assertEquals(500000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2031").getBody().getValue());
        assertEquals(0.0, vehicleController.getValue("Lee Transport", "223", "27-03-2041").getBody().getValue());
        assertEquals(0.0, vehicleController.getValue("Lee Transport", "223", "27-03-2051").getBody().getValue());
        assertEquals(HttpStatus.NO_CONTENT, vehicleController.getValue("Lee Transport", "224", "27-03-2021").getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, vehicleController.getValue("Lee Transport", "", "27-03-2021").getStatusCode());
        //Mock a bus.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("223")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("registrationNumber", "22-TEST"))
                .company("Lee Transport")
                .allocatedTour("1/2")
                .delayInMinutes(5)
                .deliveryDate(LocalDateTime.of(2021, 3, 25,0,0))
                .inspectionDate(LocalDateTime.of(2021, 4, 25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021, 10, 21,0,0), 14))
                .build()));
        assertEquals(180000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2022").getBody().getValue());
        //Mock a tram.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("223")
                .vehicleType(VehicleType.TRAM)
                .company("Lee Transport")
                .allocatedTour("1/2")
                .delayInMinutes(5)
                .deliveryDate(LocalDateTime.of(2021, 3, 25,0,0))
                .inspectionDate(LocalDateTime.of(2021, 4, 25,0,0))
                .timesheet(Map.of(LocalDateTime.of(2021, 10, 21,0,0), 14))
                .build()));
        assertEquals(665000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2022").getBody().getValue());
    }




}
