package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Vehicle;
import de.davelee.trams.operations.model.VehicleType;
import de.davelee.trams.operations.request.AddVehicleHoursRequest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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

}
