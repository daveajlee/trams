package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Vehicle;
import de.davelee.trams.operations.model.VehicleType;
import de.davelee.trams.operations.response.VehiclesResponse;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

/**
 * This class tests the VehiclesController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class VehiclesControllerTest {

    @InjectMocks
    private VehiclesController vehiclesController;

    @Mock
    private VehicleService vehicleService;

    /**
     * Test the test data endpoint of this controller.
     */
    @Test
    public void testTestDataEndpoint() {
        Mockito.when(vehicleService.addVehicle(any())).thenReturn(true);
        ResponseEntity<Void> responseEntity = vehiclesController.addTestData();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test the get vehicles endpoint of this controller.
     */
    @Test
    public void testGetVehiclesEndpoint() {
        //First test is for only retrieving by company
        Mockito.when(vehicleService.retrieveVehiclesByCompany("Lee Buses")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("213")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("Registration Number", "XXX2 BBB"))
                .deliveryDate(LocalDate.of(2017,3,25))
                .inspectionDate(LocalDate.of(2017,4,25))
                .company("Lee Buses")
                .build(),
                Vehicle.builder()
                .livery("Red with green text")
                .fleetNumber("2134")
                .allocatedTour("RE1/1")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Collections.singletonMap("Power Mode", "Electric"))
                .deliveryDate(LocalDate.of(2009,3,25))
                .inspectionDate(LocalDate.of(2009,4,25))
                .company("Lee Buses")
                .build(),
                Vehicle.builder()
                .livery("Red with blue text")
                .fleetNumber("4213")
                .allocatedTour("121/1")
                .vehicleType(VehicleType.TRAM)
                .typeSpecificInfos(Collections.singletonMap("Bidirectional", "false"))
                .deliveryDate(LocalDate.of(2010,3,25))
                .inspectionDate(LocalDate.of(2010,4,25))
                .company("Lee Buses")
                .build()));
        ResponseEntity<VehiclesResponse> responseEntity = vehiclesController.getVehiclesByCompanyAndFleetNumber("Lee Buses", Optional.empty());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(3, responseEntity.getBody().getCount());
        assertEquals("Bus", responseEntity.getBody().getVehicleResponses()[0].getVehicleType());
        //Check that days until next inspection is calculated correctly.
        assertEquals("Inspection Due!", responseEntity.getBody().getVehicleResponses()[0].getInspectionStatus());
        assertEquals(0, responseEntity.getBody().getVehicleResponses()[0].getNextInspectionDueInDays());
        assertEquals("Inspection Due!", responseEntity.getBody().getVehicleResponses()[1].getInspectionStatus());
        assertEquals(0, responseEntity.getBody().getVehicleResponses()[1].getNextInspectionDueInDays());
        assertEquals("Inspection Due!", responseEntity.getBody().getVehicleResponses()[2].getInspectionStatus());
        assertEquals(0, responseEntity.getBody().getVehicleResponses()[2].getNextInspectionDueInDays());
        //Second test is for retrieving by fleet number and company.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "21")).thenReturn(Lists.newArrayList(Vehicle.builder()
                .livery("Green with red text")
                .fleetNumber("213")
                .allocatedTour("1/1")
                .vehicleType(VehicleType.BUS)
                .typeSpecificInfos(Collections.singletonMap("Registration Number", "XXX2 BBB"))
                .company("Lee Buses")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .build()));
        ResponseEntity<VehiclesResponse> responseEntity2 = vehiclesController.getVehiclesByCompanyAndFleetNumber("Lee Buses", Optional.of("21"));
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
        assertEquals(1, responseEntity2.getBody().getCount());
        assertEquals("Bus", responseEntity2.getBody().getVehicleResponses()[0].getVehicleType());
        //Check that days until next inspection is calculated correctly.
        assertEquals("Inspected",responseEntity2.getBody().getVehicleResponses()[0].getInspectionStatus());
        assertNotNull(responseEntity2.getBody().getVehicleResponses()[0].getNextInspectionDueInDays());
        //Third test is for retrieving without supplying a company.
        ResponseEntity<VehiclesResponse> responseEntity3 = vehiclesController.getVehiclesByCompanyAndFleetNumber("", Optional.empty());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity3.getStatusCode());
        //Fourth test is when there are no vehicles to retrieve.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "22")).thenReturn(null);
        ResponseEntity<VehiclesResponse> responseEntity4 = vehiclesController.getVehiclesByCompanyAndFleetNumber("Lee Buses", Optional.of("22"));
        assertEquals(HttpStatus.NO_CONTENT, responseEntity4.getStatusCode());
    }

}
