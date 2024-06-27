package de.davelee.trams.server.controller;

import de.davelee.trams.server.constant.VehicleStatus;
import de.davelee.trams.server.constant.VehicleType;
import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.model.Vehicle;
import de.davelee.trams.server.response.ExportResponse;
import de.davelee.trams.server.service.RouteService;
import de.davelee.trams.server.service.VehicleService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the ExportController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class ExportControllerTest {

    @InjectMocks
    private ExportController exportController;

    @Mock
    private RouteService routeService;

    @Mock
    private VehicleService vehicleService;

    /**
     * Test the export endpoint of this controller.
     */
    @Test
    public void testExportEndpoint() {
        //Mock route method.
        Mockito.when(routeService.getRoutesByCompany("Lee Buses")).thenReturn(
                List.of(Route.builder().routeNumber("1A").company("Lee Buses").build()));
        //Mock vehicle method.
        Mockito.when(vehicleService.retrieveVehiclesByCompany("Lee Buses")).thenReturn(Lists.newArrayList(Vehicle.builder()
                        .livery("Green with red text")
                        .fleetNumber("213")
                        .allocatedTour("1/1")
                        .vehicleType(VehicleType.BUS)
                        .typeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"))
                        .deliveryDate(LocalDateTime.of(2017,3,25, 0, 0))
                        .inspectionDate(LocalDateTime.of(2017,4,25,0,0))
                        .company("Lee Buses")
                        .vehicleStatus(VehicleStatus.DELIVERED)
                        .build(),
                Vehicle.builder()
                        .livery("Red with green text")
                        .fleetNumber("2134")
                        .allocatedTour("RE1/1")
                        .vehicleType(VehicleType.TRAIN)
                        .typeSpecificInfos(Collections.singletonMap("Power Mode", "Electric"))
                        .deliveryDate(LocalDateTime.of(2009,3,25,0,0))
                        .inspectionDate(LocalDateTime.of(2009,4,25,0,0))
                        .vehicleStatus(VehicleStatus.DELIVERED)
                        .company("Lee Buses")
                        .build(),
                Vehicle.builder()
                        .livery("Red with blue text")
                        .fleetNumber("4213")
                        .allocatedTour("121/1")
                        .vehicleType(VehicleType.TRAM)
                        .typeSpecificInfos(Collections.singletonMap("Bidirectional", "false"))
                        .deliveryDate(LocalDateTime.of(2010,3,25,0,0))
                        .inspectionDate(LocalDateTime.of(2010,4,25,0,0))
                        .vehicleStatus(VehicleStatus.DELIVERED)
                        .company("Lee Buses")
                        .build()));
        ResponseEntity<ExportResponse> exportResponse = exportController.getExport("Lee Buses");
        assertEquals(HttpStatus.OK, exportResponse.getStatusCode());
        assertEquals(1, exportResponse.getBody().getRouteResponses().length);
        assertEquals(3, exportResponse.getBody().getVehicleResponses().length);
        //Now do test with no content.
        Mockito.when(routeService.getRoutesByCompany("Lee Buses")).thenReturn(List.of());
        Mockito.when(vehicleService.retrieveVehiclesByCompany("Lee Buses")).thenReturn(List.of());
        exportResponse = exportController.getExport("Lee Buses");
        assertEquals(HttpStatus.NO_CONTENT, exportResponse.getStatusCode());
        //Now do test with no company.
        exportResponse = exportController.getExport("");
        assertEquals(HttpStatus.BAD_REQUEST, exportResponse.getStatusCode());
    }

}
