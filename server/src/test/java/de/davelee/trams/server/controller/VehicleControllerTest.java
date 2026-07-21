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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
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
        PurchaseVehicleRequest purchaseVehicleRequest = new PurchaseVehicleRequest();
        purchaseVehicleRequest.setAdditionalTypeInformationMap(Map.of("registrationNumber", "XXX2 BBB"));
        purchaseVehicleRequest.setModelName("Bendy Bus 2000");
        purchaseVehicleRequest.setVehicleType("BUS");
        purchaseVehicleRequest.setSeatingCapacity(50);
        purchaseVehicleRequest.setStandingCapacity(100);
        purchaseVehicleRequest.setCompany("Lee Transport");
        purchaseVehicleRequest.setLivery("Green with red text");
        purchaseVehicleRequest.setFleetNumber("213");
        ResponseEntity<PurchaseVehicleResponse> responseEntity = vehicleController.purchaseVehicle(purchaseVehicleRequest);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertTrue(responseEntity.getBody().isPurchased());
        assertEquals(200000, responseEntity.getBody().getPurchasePrice());
        //Purchase valid train
        PurchaseVehicleRequest purchaseVehicleRequestTrain = new PurchaseVehicleRequest();
        purchaseVehicleRequestTrain.setAdditionalTypeInformationMap(Map.of("Operating Mode", "Electric"));
        purchaseVehicleRequestTrain.setModelName("Elec Train 2000");
        purchaseVehicleRequestTrain.setVehicleType("TRAIN");
        purchaseVehicleRequestTrain.setSeatingCapacity(130);
        purchaseVehicleRequestTrain.setStandingCapacity(200);
        purchaseVehicleRequestTrain.setCompany("Lee Transport");
        purchaseVehicleRequestTrain.setLivery("Green with red text");
        purchaseVehicleRequestTrain.setFleetNumber("2300");
        ResponseEntity<PurchaseVehicleResponse> responseEntity2 = vehicleController.purchaseVehicle(purchaseVehicleRequestTrain);
        assertEquals(200, responseEntity2.getStatusCode().value());
        assertTrue(responseEntity2.getBody().isPurchased());
        assertEquals(1000000, responseEntity2.getBody().getPurchasePrice());
        //Purchase valid tram
        PurchaseVehicleRequest purchaseVehicleRequestTram = new PurchaseVehicleRequest();
        purchaseVehicleRequestTram.setAdditionalTypeInformationMap(Map.of("Operating Mode", "Electric"));
        purchaseVehicleRequestTram.setModelName("Elec Tram 2000");
        purchaseVehicleRequestTram.setVehicleType("TRAM");
        purchaseVehicleRequestTram.setSeatingCapacity(50);
        purchaseVehicleRequestTram.setStandingCapacity(130);
        purchaseVehicleRequestTram.setCompany("Lee Transport");
        purchaseVehicleRequestTram.setLivery("Green with red text");
        purchaseVehicleRequestTram.setFleetNumber("3300");
        ResponseEntity<PurchaseVehicleResponse> responseEntity3 = vehicleController.purchaseVehicle(purchaseVehicleRequestTram);
        assertEquals(200, responseEntity3.getStatusCode().value());
        assertTrue(responseEntity3.getBody().isPurchased());
        assertEquals(700000, responseEntity3.getBody().getPurchasePrice());
    }

    /**
     * Test the purchase endpoint of this controller with invalid requests.
     */
    @Test
    public void testInvalidPurchaseVehicle() {
        //Mock important methods
        Vehicle vehicle = new Vehicle();
        vehicle.setLivery("Green with red text");
        vehicle.setFleetNumber("213");
        vehicle.setAllocatedTour("1/1");
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicle.setCompany("Lee Buses");
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(vehicle));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "214")).thenReturn(null);
        Mockito.when(vehicleService.addVehicle(any())).thenReturn(false);
        //Purchase bus with missing company.
        PurchaseVehicleRequest purchaseVehicleRequest = new PurchaseVehicleRequest();
        purchaseVehicleRequest.setAdditionalTypeInformationMap(Map.of("registrationNumber", "XXX2 BBB"));
        purchaseVehicleRequest.setModelName("Bendy Bus 2000");
        purchaseVehicleRequest.setVehicleType("BUS");
        purchaseVehicleRequest.setSeatingCapacity(50);
        purchaseVehicleRequest.setStandingCapacity(100);
        purchaseVehicleRequest.setCompany("Lee Transport");
        purchaseVehicleRequest.setLivery("Green with red text");
        purchaseVehicleRequest.setFleetNumber("213");
        ResponseEntity<PurchaseVehicleResponse> responseEntity = vehicleController.purchaseVehicle(purchaseVehicleRequest);
        assertEquals(400, responseEntity.getStatusCode().value());
        //Purchase bus which already exists.
        ResponseEntity<PurchaseVehicleResponse> responseEntity2 = vehicleController.purchaseVehicle(purchaseVehicleRequest);
        assertEquals(409, responseEntity2.getStatusCode().value());
        //Purchase bus which does not exist but does not validate and cannot be added to the database.
        PurchaseVehicleRequest purchaseVehicleRequest2 = new PurchaseVehicleRequest();
        purchaseVehicleRequest2.setAdditionalTypeInformationMap(Map.of("registrationNumber", "XXX2 BBB"));
        purchaseVehicleRequest2.setModelName("Bendy Bus 2000");
        purchaseVehicleRequest2.setVehicleType("BUS");
        purchaseVehicleRequest2.setSeatingCapacity(50);
        purchaseVehicleRequest2.setStandingCapacity(100);
        purchaseVehicleRequest2.setCompany("Lee Transport");
        purchaseVehicleRequest2.setLivery("Green with red text");
        purchaseVehicleRequest2.setFleetNumber("214");
        ResponseEntity<PurchaseVehicleResponse> responseEntity3 = vehicleController.purchaseVehicle(purchaseVehicleRequest2);
        assertEquals(500, responseEntity3.getStatusCode().value());
    }

    /**
     * Test the addHoursForDate endpoint of this controller.
     */
    @Test
    public void testAddHoursForDate() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLivery("Green with red text");
        vehicle.setFleetNumber("213");
        vehicle.setAllocatedTour("1/1");
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicle.setCompany("Lee Buses");
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        //Mock important methods in the vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(vehicle));
        Mockito.when(vehicleService.addHoursForDate(any(), eq(14), any() )).thenReturn(true);
        //Do test with a valid request.
        AddVehicleHoursRequest addVehicleHoursRequest = new AddVehicleHoursRequest();
        addVehicleHoursRequest.setDate("21-10-2021");
        addVehicleHoursRequest.setHours(14);
        addVehicleHoursRequest.setCompany("Lee Buses");
        addVehicleHoursRequest.setFleetNumber("213");
        ResponseEntity<Void> responseEntity = vehicleController.addHoursForDate(addVehicleHoursRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //Do test with a bad request.
        AddVehicleHoursRequest addVehicleHoursRequest2 = new AddVehicleHoursRequest();
        addVehicleHoursRequest2.setDate("");
        addVehicleHoursRequest2.setHours(-1);
        addVehicleHoursRequest2.setCompany("Lee Buses");
        addVehicleHoursRequest2.setFleetNumber("213");
        ResponseEntity<Void> responseEntity2 = vehicleController.addHoursForDate(
                addVehicleHoursRequest2);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Do test with 0 results from db.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "212")).thenReturn(Lists.newArrayList());
        AddVehicleHoursRequest addVehicleHoursRequest3 = new AddVehicleHoursRequest();
        addVehicleHoursRequest3.setDate("21-10-2021");
        addVehicleHoursRequest3.setHours(14);
        addVehicleHoursRequest3.setCompany("Lee Buses");
        addVehicleHoursRequest3.setFleetNumber("212");
        ResponseEntity<Void> responseEntity3 = vehicleController.addHoursForDate(
                addVehicleHoursRequest3);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
        AddVehicleHoursRequest addVehicleHoursRequest4 = new AddVehicleHoursRequest();
        addVehicleHoursRequest4.setDate("210-10-2021");
        addVehicleHoursRequest4.setHours(14);
        addVehicleHoursRequest4.setCompany("Lee Buses");
        addVehicleHoursRequest4.setFleetNumber("213");
        ResponseEntity<Void> responseEntity4 = vehicleController.addHoursForDate(addVehicleHoursRequest4);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
    }

    /**
     * Test the getHoursForDate endpoint of this controller.
     */
    @Test
    public void testGetHoursForDate() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLivery("Green with red text");
        vehicle.setFleetNumber("213");
        vehicle.setAllocatedTour("1/1");
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicle.setCompany("Lee Buses");
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLivery("Green with red text");
        vehicle2.setFleetNumber("214");
        vehicle2.setAllocatedTour("1/1");
        vehicle2.setVehicleType(VehicleType.TRAM);
        vehicle2.setTypeSpecificInfos(Collections.singletonMap("Bidirectional", "true"));
        vehicle2.setCompany("Lee Buses");
        vehicle2.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle2.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle2.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setLivery("Green with red text");
        vehicle3.setFleetNumber("215");
        vehicle3.setAllocatedTour("1/1");
        vehicle3.setVehicleType(VehicleType.TRAIN);
        vehicle3.setTypeSpecificInfos(Collections.singletonMap("Power Mode", "Electric"));
        vehicle3.setCompany("Lee Buses");
        vehicle3.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle3.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle3.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        //Mock important methods in the vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(vehicle));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "214")).thenReturn(Lists.newArrayList(vehicle2));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "215")).thenReturn(Lists.newArrayList(vehicle3));
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
        Vehicle vehicle = new Vehicle();
        vehicle.setLivery("Green with red text");
        vehicle.setFleetNumber("213");
        vehicle.setAllocatedTour("1/1");
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicle.setCompany("Lee Buses");
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(vehicle));
        Mockito.when(vehicleService.addVehicleHistoryEntry(any(), any(), any(), anyString())).thenReturn(true);
        //Perform tests - valid request
        AddHistoryEntryRequest addHistoryEntryRequest = new AddHistoryEntryRequest();
        addHistoryEntryRequest.setFleetNumber("213");
        addHistoryEntryRequest.setCompany("Lee Buses");
        addHistoryEntryRequest.setDate("01-03-2020");
        addHistoryEntryRequest.setReason("PURCHASED");
        addHistoryEntryRequest.setComment("Welcome to the company!");
        ResponseEntity<Void> responseEntity = vehicleController.addHistoryEntry(addHistoryEntryRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
        //Perform tests - fleet number missing
        AddHistoryEntryRequest addHistoryEntryRequest2 = new AddHistoryEntryRequest();
        addHistoryEntryRequest2.setCompany("Lee Buses");
        addHistoryEntryRequest2.setDate("01-03-2020");
        addHistoryEntryRequest2.setReason("JOINED");
        addHistoryEntryRequest2.setComment("Welcome to the company!");
        ResponseEntity<Void> responseEntity2 = vehicleController.addHistoryEntry(addHistoryEntryRequest2);
        assertTrue(responseEntity2.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
        //Perform tests - no vehicle
        AddHistoryEntryRequest addHistoryEntryRequest3 = new AddHistoryEntryRequest();
        addHistoryEntryRequest3.setFleetNumber("210");
        addHistoryEntryRequest.setCompany("Example Company");
        addHistoryEntryRequest.setDate("01-03-2020");
        addHistoryEntryRequest.setReason("JOINED");
        addHistoryEntryRequest.setComment("Welcome to the company!");
        ResponseEntity<Void> responseEntity3 = vehicleController.addHistoryEntry(addHistoryEntryRequest3);
        assertTrue(responseEntity3.getStatusCode().value() == HttpStatus.NO_CONTENT.value());
    }

    /**
     * Test case: sell a vehicle.
     * Expected result: the selling price is returned.
     */
    @Test
    public void testSellVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLivery("Green with red text");
        vehicle.setFleetNumber("213");
        vehicle.setAllocatedTour("1/1");
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicle.setCompany("Lee Buses");
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "213")).thenReturn(Lists.newArrayList(vehicle));
        Mockito.when(vehicleService.sellVehicle(any())).thenReturn(VehicleType.BUS.getPurchasePrice());
        //Perform the test.
        ResponseEntity<SellVehicleResponse> responseEntity = vehicleController.sellVehicle(new SellVehicleRequest("213", "Lee Buses"));
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
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setLivery("Green with red text");
        vehicle3.setFleetNumber("223");
        vehicle3.setAllocatedTour("1/1");
        vehicle3.setVehicleType(VehicleType.TRAIN);
        vehicle3.setTypeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"));
        vehicle3.setCompany("Lee Transport");
        vehicle3.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle3.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle3.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(vehicle3));
        Mockito.when(vehicleService.inspectVehicle(any())).thenReturn(VehicleType.TRAIN.getInspectionPrice());
        //Perform the test.
        ResponseEntity<InspectVehicleResponse> responseEntity = vehicleController.inspectVehicle(new InspectVehicleRequest("223", "Lee Transport"));
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
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setLivery("Green with red text");
        vehicle4.setFleetNumber("233");
        vehicle4.setAllocatedTour("1/1");
        vehicle4.setVehicleType(VehicleType.BUS);
        vehicle4.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "HFJK23D"));
        vehicle4.setCompany("Lee Transport");
        vehicle4.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle4.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle4.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "233")).thenReturn(Lists.newArrayList(vehicle4));
        Mockito.when(vehicleService.inspectVehicle(any())).thenReturn(VehicleType.BUS.getInspectionPrice());
        //Perform the test.
        ResponseEntity<InspectVehicleResponse> responseEntity4 = vehicleController.inspectVehicle(new InspectVehicleRequest("233", "Lee Transport"));
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
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setLivery("Green with red text");
        vehicle3.setFleetNumber("223");
        vehicle3.setAllocatedTour("1/1");
        vehicle3.setVehicleType(VehicleType.TRAIN);
        vehicle3.setTypeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"));
        vehicle3.setCompany("Lee Transport");
        vehicle3.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle3.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle3.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(vehicle3));
        Mockito.when(vehicleService.allocateTourToVehicle(any(), any(), any())).thenReturn(true);
        //Perform the actual test.
        ResponseEntity responseEntity = vehicleController.allocateVehicle(new AllocateVehicleRequest("223", "Lee Transport", "1", "1"));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //Remove fleet number.
        ResponseEntity responseEntity2 = vehicleController.allocateVehicle(new AllocateVehicleRequest("", "Lee Transport", "1", "1"));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Test with vehicle that does not exist
        AllocateVehicleRequest allocateVehicleRequest = new AllocateVehicleRequest();
        allocateVehicleRequest.setAllocatedTour("1/1");
        allocateVehicleRequest.setFleetNumber("233");
        allocateVehicleRequest.setCompany("Lee Transport");
        ResponseEntity responseEntity3 = vehicleController.allocateVehicle(allocateVehicleRequest);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());
        //Test remove actual allocation.
        ResponseEntity responseEntity4 = vehicleController.removeVehicleAllocation("Lee Transport", "223");
        assertEquals(HttpStatus.OK, responseEntity4.getStatusCode());
        //Test with vehicle that does not exist
        ResponseEntity responseEntity6 = vehicleController.removeVehicleAllocation("Lee Transport", "233");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity6.getStatusCode());
    }

    /**
     * Test case: retrieve vehicles by allocations.
     * Expected result: the vehicles are retrieved as appropriate.
     */
    @Test
    public void testRetrieveAllocations() {
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setLivery("Green with red text");
        vehicle3.setFleetNumber("223");
        vehicle3.setAllocatedTour("1/2");
        vehicle3.setVehicleType(VehicleType.TRAIN);
        vehicle3.setTypeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"));
        vehicle3.setCompany("Lee Transport");
        vehicle3.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle3.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle3.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndAllocatedRouteAndAllocatedTour("Lee Transport", "1", "2")).thenReturn(Lists.newArrayList(vehicle3));
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
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setLivery("Green with red text");
        vehicle3.setFleetNumber("223");
        vehicle3.setAllocatedTour("1/1");
        vehicle3.setVehicleType(VehicleType.TRAIN);
        vehicle3.setTypeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"));
        vehicle3.setCompany("Lee Transport");
        vehicle3.setDelayInMinutes(5);
        vehicle3.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle3.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle3.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(vehicle3));
        //Attempt to adjust delay.
        assertEquals(HttpStatus.OK, vehicleController.adjustVehicleDelay(new AdjustVehicleDelayRequest("223", "Lee Transport", -3)).getStatusCode());
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
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setLivery("Green with red text");
        vehicle3.setFleetNumber("223");
        vehicle3.setAllocatedTour("1/2");
        vehicle3.setVehicleType(VehicleType.TRAIN);
        vehicle3.setTypeSpecificInfos(Collections.singletonMap("Operational Mode", "Electric"));
        vehicle3.setCompany("Lee Transport");
        vehicle3.setDelayInMinutes(5);
        vehicle3.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle3.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle3.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        //Mock the important methods in vehicle service.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(vehicle3));
        //Attempt to adjust delay.
        assertEquals(1000000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2021").getBody().getValue());
        assertEquals(950000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2022").getBody().getValue());
        assertEquals(500000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2031").getBody().getValue());
        assertEquals(0.0, vehicleController.getValue("Lee Transport", "223", "27-03-2041").getBody().getValue());
        assertEquals(0.0, vehicleController.getValue("Lee Transport", "223", "27-03-2051").getBody().getValue());
        assertEquals(HttpStatus.NO_CONTENT, vehicleController.getValue("Lee Transport", "224", "27-03-2021").getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, vehicleController.getValue("Lee Transport", "", "27-03-2021").getStatusCode());
        //Mock a bus.
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLivery("Green with red text");
        vehicle2.setFleetNumber("223");
        vehicle2.setAllocatedTour("1/2");
        vehicle2.setVehicleType(VehicleType.BUS);
        vehicle2.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "22-TEST"));
        vehicle2.setCompany("Lee Transport");
        vehicle2.setDelayInMinutes(5);
        vehicle2.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle2.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle2.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(vehicle2));
        assertEquals(180000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2022").getBody().getValue());
        //Mock a tram.
        Vehicle vehicle = new Vehicle();
        vehicle.setLivery("Green with red text");
        vehicle.setFleetNumber("223");
        vehicle.setAllocatedTour("1/2");
        vehicle.setVehicleType(VehicleType.TRAM);
        vehicle.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "22-TEST"));
        vehicle.setCompany("Lee Transport");
        vehicle.setDelayInMinutes(5);
        vehicle.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle.setTimesheet(Map.of(LocalDateTime.of(2021,10,21,0,0), 14));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Transport", "223")).thenReturn(Lists.newArrayList(vehicle));
        assertEquals(665000.0, vehicleController.getValue("Lee Transport", "223", "27-03-2022").getBody().getValue());
    }




}
