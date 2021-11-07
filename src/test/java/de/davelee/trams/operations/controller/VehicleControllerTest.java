package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Vehicle;
import de.davelee.trams.operations.model.VehicleType;
import de.davelee.trams.operations.request.*;
import de.davelee.trams.operations.response.InspectVehicleResponse;
import de.davelee.trams.operations.response.PurchaseVehicleResponse;
import de.davelee.trams.operations.response.SellVehicleResponse;
import de.davelee.trams.operations.response.VehicleHoursResponse;
import de.davelee.trams.operations.service.VehicleService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
                        .additionalTypeInformationMap(Map.of("Registration Number", "XXX2 BBB"))
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
                .typeSpecificInfos(Collections.singletonMap("Registration Number", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .build()));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "214")).thenReturn(null);
        Mockito.when(vehicleService.addVehicle(any())).thenReturn(false);
        //Purchase bus with missing company.
        ResponseEntity<PurchaseVehicleResponse> responseEntity = vehicleController.purchaseVehicle(PurchaseVehicleRequest.builder()
                .additionalTypeInformationMap(Map.of("Registration Number", "XXX2 BBB"))
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
                .additionalTypeInformationMap(Map.of("Registration Number", "XXX2 BBB"))
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
                .additionalTypeInformationMap(Map.of("Registration Number", "XXX2 BBB"))
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
                .typeSpecificInfos(Collections.singletonMap("Registration Number", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
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
                .typeSpecificInfos(Collections.singletonMap("Registration Number", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .timesheet(Map.of(LocalDate.of(2021,10,21), 14))
                .build()));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "214")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("214")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.TRAM)
                .typeSpecificInfos(Collections.singletonMap("Bidirectional", "true"))
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .timesheet(Map.of(LocalDate.of(2021,10,21), 14))
                .build()));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "215")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("215")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Collections.singletonMap("Power Mode", "Electric"))
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .timesheet(Map.of(LocalDate.of(2021,10,21), 14))
                .build()));
        //Do test with a valid request for each vehicle Type.
        ResponseEntity<VehicleHoursResponse> responseEntity = vehicleController.getHoursForDate("Lee Buses", "213", "21-10-2021");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().getNumberOfHoursAvailable());
        assertEquals(14, responseEntity.getBody().getNumberOfHoursSoFar());
        assertFalse(responseEntity.getBody().isMaximumHoursReached());
        ResponseEntity<VehicleHoursResponse> responseEntityTrain = vehicleController.getHoursForDate("Lee Buses", "215", "21-10-2021");
        assertEquals(HttpStatus.OK, responseEntityTrain.getStatusCode());
        assertEquals(7, responseEntityTrain.getBody().getNumberOfHoursAvailable());
        assertEquals(14, responseEntityTrain.getBody().getNumberOfHoursSoFar());
        assertFalse(responseEntityTrain.getBody().isMaximumHoursReached());
        ResponseEntity<VehicleHoursResponse> responseEntityTram = vehicleController.getHoursForDate("Lee Buses", "214", "21-10-2021");
        assertEquals(HttpStatus.OK, responseEntityTram.getStatusCode());
        assertEquals(6, responseEntityTram.getBody().getNumberOfHoursAvailable());
        assertEquals(14, responseEntityTram.getBody().getNumberOfHoursSoFar());
        assertFalse(responseEntity.getBody().isMaximumHoursReached());
        //Do test with a valid request but no hours.
        ResponseEntity<VehicleHoursResponse> responseEntity1 = vehicleController.getHoursForDate("Lee Buses", "213", "22-10-2021");
        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
        assertEquals(16, responseEntity1.getBody().getNumberOfHoursAvailable());
        assertEquals(0, responseEntity1.getBody().getNumberOfHoursSoFar());
        assertFalse(responseEntity1.getBody().isMaximumHoursReached());
        //Do test with a bad request.
        ResponseEntity<VehicleHoursResponse> responseEntity2 = vehicleController.getHoursForDate("", "213", "21-10-2021");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        //Do test with 0 results from db.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "212")).thenReturn(Lists.newArrayList());
        ResponseEntity<VehicleHoursResponse> responseEntity3 = vehicleController.getHoursForDate("Lee Buses", "212", "21-10-2021");
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
                .typeSpecificInfos(Collections.singletonMap("Registration Number", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .timesheet(Map.of(LocalDate.of(2021,10,21), 14))
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
                .typeSpecificInfos(Collections.singletonMap("Registration Number", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .timesheet(Map.of(LocalDate.of(2021,10,21), 14))
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
                .deliveryDate(LocalDate.of(2021, 3, 25))
                .inspectionDate(LocalDate.of(2021, 4, 25))
                .timesheet(Map.of(LocalDate.of(2021, 10, 21), 14))
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
                .typeSpecificInfos(Collections.singletonMap("Registration Number", "HFJK23D"))
                .company("Lee Transport")
                .deliveryDate(LocalDate.of(2021, 3, 25))
                .inspectionDate(LocalDate.of(2021, 4, 25))
                .timesheet(Map.of(LocalDate.of(2021, 10, 21), 14))
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

}
