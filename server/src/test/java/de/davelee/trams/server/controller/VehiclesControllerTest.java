package de.davelee.trams.server.controller;

import de.davelee.trams.server.constant.VehicleHistoryReason;
import de.davelee.trams.server.constant.VehicleStatus;
import de.davelee.trams.server.constant.VehicleType;
import de.davelee.trams.server.model.*;
import de.davelee.trams.server.request.LoadVehicleRequest;
import de.davelee.trams.server.request.LoadVehiclesRequest;
import de.davelee.trams.server.request.VehicleHistoryRequest;
import de.davelee.trams.server.response.VehiclesResponse;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * This class tests the VehiclesController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VehiclesControllerTest {

    @InjectMocks
    private VehiclesController vehiclesController;

    @Mock
    private VehicleService vehicleService;

    /**
     * Test the get vehicles endpoint of this controller.
     */
    @Test
    public void testGetVehiclesEndpoint() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLivery("Green with red text");
        vehicle.setFleetNumber("213");
        vehicle.setAllocatedRoute("1");
        vehicle.setAllocatedTour("1");
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicle.setDeliveryDate(LocalDateTime.of(2017,3,25,0,0));
        vehicle.setInspectionDate(LocalDateTime.of(2017,4,25,0,0));
        vehicle.setCompany("Lee Buses");
        vehicle.setVehicleStatus(VehicleStatus.DELIVERED);
        // Train
        Vehicle vehicleTrain = new Vehicle();
        vehicleTrain.setLivery("Red with green text");
        vehicleTrain.setFleetNumber("2134");
        vehicleTrain.setAllocatedRoute("RE1");
        vehicleTrain.setAllocatedTour("1");
        vehicleTrain.setVehicleType(VehicleType.TRAIN);
        vehicleTrain.setTypeSpecificInfos(Collections.singletonMap("Power Mode", "Electric"));
        vehicleTrain.setDeliveryDate(LocalDateTime.of(2009,3,25,0,0));
        vehicleTrain.setInspectionDate(LocalDateTime.of(2009,4,25,0,0));
        vehicleTrain.setVehicleStatus(VehicleStatus.DELIVERED);
        vehicleTrain.setCompany("Lee Buses");
        // Tram
        Vehicle vehicleTram = new Vehicle();
        vehicleTram.setLivery("Red with blue text");
        vehicleTram.setFleetNumber("4213");
        vehicleTram.setAllocatedRoute("121");
        vehicleTram.setAllocatedTour("1");
        vehicleTram.setVehicleType(VehicleType.TRAM);
        vehicleTram.setTypeSpecificInfos(Collections.singletonMap("Bidirectional", "false"));
        vehicleTram.setDeliveryDate(LocalDateTime.of(2010,3,25,0,0));
        vehicleTram.setInspectionDate(LocalDateTime.of(2010,4,25,0,0));
        vehicleTram.setVehicleStatus(VehicleStatus.DELIVERED);
        vehicleTram.setCompany("Lee Buses");
        //First test is for only retrieving by company
        Mockito.when(vehicleService.retrieveVehiclesByCompany("Lee Buses")).thenReturn(Lists.newArrayList(vehicle,
                vehicleTrain,
                vehicleTram));
        ResponseEntity<VehiclesResponse> responseEntity = vehiclesController.getVehiclesByCompanyAndFleetNumber("Lee Buses", Optional.empty(), Optional.empty());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
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
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLivery("Green with red text");
        vehicle2.setFleetNumber("213");
        vehicle2.setAllocatedRoute("1");
        vehicle2.setAllocatedTour("1");
        vehicle2.setVehicleType(VehicleType.BUS);
        vehicle2.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        vehicle2.setCompany("Lee Buses");
        vehicle2.setDeliveryDate(LocalDateTime.of(2021,3,25,0,0));
        vehicle2.setInspectionDate(LocalDateTime.of(2021,4,25,0,0));
        vehicle2.setVehicleStatus(VehicleStatus.DELIVERED);
        vehicle2.setVehicleHistoryEntryList(List.of(
                new VehicleHistoryEntry(null, LocalDateTime.of(2021,3,1,0,0),VehicleHistoryReason.PURCHASED, "Purchased!"),
                new VehicleHistoryEntry(null, LocalDateTime.of(2021,3,25,0,0), VehicleHistoryReason.DELIVERED, "Delivered!")
        ));
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "21")).thenReturn(Lists.newArrayList(vehicle2));
        ResponseEntity<VehiclesResponse> responseEntity2 = vehiclesController.getVehiclesByCompanyAndFleetNumber("Lee Buses", Optional.of("21"), Optional.empty());
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
        assertNotNull(responseEntity2.getBody());
        assertEquals(1, responseEntity2.getBody().getCount());
        assertEquals("Bus", responseEntity2.getBody().getVehicleResponses()[0].getVehicleType());
        assertEquals("Purchased!", responseEntity2.getBody().getVehicleResponses()[0].getUserHistory().get(0).getComment());
        assertEquals("Purchased", responseEntity2.getBody().getVehicleResponses()[0].getUserHistory().get(0).getVehicleHistoryReason());
        assertEquals("01-03-2021 00:00", responseEntity2.getBody().getVehicleResponses()[0].getUserHistory().get(0).getDate());
        //Check that days until next inspection is calculated correctly.
        assertEquals("Inspection Due!",responseEntity2.getBody().getVehicleResponses()[0].getInspectionStatus());
        assertTrue(responseEntity2.getBody().getVehicleResponses()[0].getNextInspectionDueInDays() > -1);
        //Third test is for retrieving by route number and company.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndAllocatedRoute("Lee Buses", "1")).thenReturn(Lists.newArrayList(vehicle2));
        ResponseEntity<VehiclesResponse> responseEntity3 = vehiclesController.getVehiclesByCompanyAndFleetNumber("Lee Buses", Optional.empty(), Optional.of("1"));
        assertEquals(HttpStatus.OK, responseEntity3.getStatusCode());
        assertNotNull(responseEntity3.getBody());
        assertEquals(1, responseEntity3.getBody().getCount());
        assertEquals("Bus", responseEntity3.getBody().getVehicleResponses()[0].getVehicleType());
        assertEquals("Purchased!", responseEntity3.getBody().getVehicleResponses()[0].getUserHistory().get(0).getComment());
        assertEquals("Purchased", responseEntity3.getBody().getVehicleResponses()[0].getUserHistory().get(0).getVehicleHistoryReason());
        assertEquals("01-03-2021 00:00", responseEntity3.getBody().getVehicleResponses()[0].getUserHistory().get(0).getDate());
        //Fourth test is for retrieving without supplying a company.
        ResponseEntity<VehiclesResponse> responseEntity4 = vehiclesController.getVehiclesByCompanyAndFleetNumber("", Optional.empty(), Optional.empty());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity4.getStatusCode());
        //Fifth test is when there are no vehicles to retrieve.
        Mockito.when(vehicleService.retrieveVehiclesByCompanyAndFleetNumber("Lee Buses", "22")).thenReturn(null);
        ResponseEntity<VehiclesResponse> responseEntity5 = vehiclesController.getVehiclesByCompanyAndFleetNumber("Lee Buses", Optional.of("22"), Optional.empty());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity5.getStatusCode());
    }

    /**
     * Test the load vehicles endpoint of this controller.
     */
    @Test
    public void testLoadVehiclesEndpoint() {
        //Mock important methods.
        Mockito.when(vehicleService.addVehicle(any())).thenReturn(true);
        //Create test data.
        LoadVehicleRequest loadVehicleRequest = new LoadVehicleRequest();
        loadVehicleRequest.setFleetNumber("1213");
        loadVehicleRequest.setCompany("Lee Buses");
        loadVehicleRequest.setDeliveryDate("25-04-2021");
        loadVehicleRequest.setInspectionDate("25-05-2021");
        loadVehicleRequest.setVehicleType("Tram");
        loadVehicleRequest.setVehicleStatus("DELIVERED");
        loadVehicleRequest.setSeatingCapacity(50);
        loadVehicleRequest.setStandingCapacity(80);
        loadVehicleRequest.setModelName("Bendy Bus 2000");
        loadVehicleRequest.setLivery("Blue with orange text");
        loadVehicleRequest.setAllocatedTour("1/2");
        loadVehicleRequest.setAdditionalTypeInformationMap(Map.of("Bidirectional", "true"));
        loadVehicleRequest.setUserHistory(List.of(new VehicleHistoryRequest("25-04-2021", "PURCHASED", "Love on first sight")));
        loadVehicleRequest.setTimesheet(Map.of("01-11-2021", 8));

        LoadVehiclesRequest loadVehiclesRequest = new LoadVehiclesRequest(1L, new LoadVehicleRequest[] {
                loadVehicleRequest,
                loadVehicleRequest});
        //Perform actual test.
        assertEquals(HttpStatus.OK, vehiclesController.loadVehicles(loadVehiclesRequest).getStatusCode());
        //Perform test where database does not work.
        Mockito.when(vehicleService.addVehicle(any())).thenReturn(false);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, vehiclesController.loadVehicles(loadVehiclesRequest).getStatusCode());
        //Set count to 0 and rerun test.
        loadVehiclesRequest.setCount(0L);
        assertEquals(HttpStatus.BAD_REQUEST, vehiclesController.loadVehicles(loadVehiclesRequest).getStatusCode());
    }

    /**
     * Test the delete vehicles endpoint of this controller.
     */
    @Test
    public void testDeleteVehiclesEndpoint() {
        //Do successful request.
        ResponseEntity<Void> responseEntity = vehiclesController.deleteVehicles("Mustermann Bus GmbH");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //Assume bad request if company is empty.
        ResponseEntity<Void> responseEntity2 = vehiclesController.deleteVehicles("");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
    }

}
