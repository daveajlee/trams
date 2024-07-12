package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Driver;
import de.davelee.trams.server.request.*;
import de.davelee.trams.server.response.*;
import de.davelee.trams.server.service.DriverService;
import de.davelee.trams.server.utils.DateUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * This class tests the DriverController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class DriverControllerTest {

    @InjectMocks
    private DriverController driverController;

    @Mock
    private DriverService driverService;

    /**
     * Test the employ endpoint of this controller with valid requests.
     */
    @Test
    public void testValidEmployDriver() {
        //Mock important methods
        Mockito.when(driverService.retrieveDriversByCompanyAndName(eq("Lee Transport"), any())).thenReturn(null);
        Mockito.when(driverService.addDriver(any())).thenReturn(true);
        //Employ valid driver
        ResponseEntity<EmployDriverResponse> responseEntity = driverController.employDriver(EmployDriverRequest.builder()
                        .name("Max Mustermann")
                        .startDate("29-06-2024 00:00")
                        .contractedHours(35)
                        .company("Lee Transport")
                        .build());
        assertEquals(200, responseEntity.getStatusCode().value());
        assertTrue(responseEntity.getBody() != null && responseEntity.getBody().isEmployed());
        assertEquals(500, responseEntity.getBody().getEmploymentCost());
    }

    /**
     * Test the employ endpoint of this controller with invalid requests.
     */
    @Test
    public void testInvalidEmployDriver() {
        //Mock important methods
        Mockito.when(driverService.retrieveDriversByCompanyAndName("Lee Transport", "Max Mustermann")).thenReturn(Lists.newArrayList(Driver.builder()
                .name("Max Mustermann")
                .startDate(DateUtils.convertDateToLocalDateTime("29-06-2024 00:00"))
                .contractedHours(35)
                .company("Lee Transport")
                .build()));
        Mockito.when(driverService.retrieveDriversByCompanyAndName("Lee Transport", "Maximus")).thenReturn(null);
        Mockito.when(driverService.addDriver(any())).thenReturn(false);
        //Employ Driver with missing company.
        ResponseEntity<EmployDriverResponse> responseEntity = driverController.employDriver(EmployDriverRequest.builder()
                .name("Max Mustermann")
                .startDate("29-06-2024 00:00")
                .contractedHours(35)
                .build());
        assertEquals(400, responseEntity.getStatusCode().value());
        //Employ Driver which already exists.
        ResponseEntity<EmployDriverResponse> responseEntity2 = driverController.employDriver(EmployDriverRequest.builder()
                .name("Max Mustermann")
                .startDate("29-06-2024 00:00")
                .contractedHours(35)
                .company("Lee Transport")
                .build());
        assertEquals(409, responseEntity2.getStatusCode().value());
        //Employ driver which does not exist but does not validate and cannot be added to the database.
        ResponseEntity<EmployDriverResponse> responseEntity3 = driverController.employDriver(EmployDriverRequest.builder()
                .name("Max Mustermann")
                .startDate("29-06-2024 00:00")
                .contractedHours(-2)
                .company("Lee Transport")
                .build());
        assertEquals(400, responseEntity3.getStatusCode().value());
    }

}
