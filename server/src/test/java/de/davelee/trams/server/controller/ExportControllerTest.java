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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
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
        Route route = new Route();
        route.setRouteNumber("1A");
        route.setCompany("Lee Buses");
        Mockito.when(routeService.getRoutesByCompany("Lee Buses")).thenReturn(
                List.of(route));
        //Mock vehicle method.
        Vehicle bus = new Vehicle();
        bus.setLivery("Green with red text");
        bus.setFleetNumber("213");
        bus.setAllocatedTour("1/1");
        bus.setVehicleType(VehicleType.BUS);
        bus.setTypeSpecificInfos(Collections.singletonMap("registrationNumber", "XXX2 BBB"));
        bus.setDeliveryDate(LocalDateTime.of(2017,3,25, 0, 0));
        bus.setInspectionDate(LocalDateTime.of(2017,4,25,0,0));
        bus.setCompany("Lee Buses");
        bus.setVehicleStatus(VehicleStatus.DELIVERED);
        Vehicle train = new Vehicle();
        train.setLivery("Red with green text");
        train.setFleetNumber("2134");
        train.setAllocatedTour("RE1/1");
        train.setVehicleType(VehicleType.TRAIN);
        train.setTypeSpecificInfos(Collections.singletonMap("Power Mode", "Electric"));
        train.setDeliveryDate(LocalDateTime.of(2009,3,25,0,0));
        train.setInspectionDate(LocalDateTime.of(2009,4,25,0,0));
        train.setVehicleStatus(VehicleStatus.DELIVERED);
        train.setCompany("Lee Buses");
        Vehicle tram = new Vehicle();
        tram.setLivery("Red with blue text");
        tram.setFleetNumber("4213");
        tram.setAllocatedTour("121/1");
        tram.setVehicleType(VehicleType.TRAM);
        tram.setTypeSpecificInfos(Collections.singletonMap("Bidirectional", "false"));
        tram.setDeliveryDate(LocalDateTime.of(2010,3,25,0,0));
        tram.setInspectionDate(LocalDateTime.of(2010,4,25,0,0));
        tram.setVehicleStatus(VehicleStatus.DELIVERED);
        tram.setCompany("Lee Buses");
        Mockito.when(vehicleService.retrieveVehiclesByCompany("Lee Buses")).thenReturn(Lists.newArrayList(bus,
                train,
                tram));
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
