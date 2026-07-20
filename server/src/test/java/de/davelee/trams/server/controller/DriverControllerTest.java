package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Driver;
import de.davelee.trams.server.request.*;
import de.davelee.trams.server.response.*;
import de.davelee.trams.server.service.DriverService;
import de.davelee.trams.server.utils.DateUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
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
        EmployDriverRequest employDriverRequest = new EmployDriverRequest();
        employDriverRequest.setName("Max Mustermann");
        employDriverRequest.setStartDate("29-06-2024 00:00");
        employDriverRequest.setContractedHours(35);
        employDriverRequest.setCompany("Lee Transport");
        ResponseEntity<EmployDriverResponse> responseEntity = driverController.employDriver(employDriverRequest);
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
        Driver driver = new Driver();
        driver.setName("Max Mustermann");
        driver.setStartDate(DateUtils.convertDateToLocalDateTime("29-06-2024 00:00"));
        driver.setContractedHours(35);
        driver.setCompany("Lee Transport");
        Mockito.when(driverService.retrieveDriversByCompanyAndName("Lee Transport", "Max Mustermann")).thenReturn(Lists.newArrayList(driver));
        //Employ Driver with missing company.
        EmployDriverRequest employDriverRequest = new EmployDriverRequest();
        employDriverRequest.setName("Max Mustermann");
        employDriverRequest.setStartDate("29-06-2024 00:00");
        employDriverRequest.setContractedHours(35);
        ResponseEntity<EmployDriverResponse> responseEntity = driverController.employDriver(employDriverRequest);
        assertEquals(400, responseEntity.getStatusCode().value());
        //Employ Driver which already exists.
        EmployDriverRequest employDriverRequest2 = new EmployDriverRequest();
        employDriverRequest2.setName("Max Mustermann");
        employDriverRequest2.setStartDate("29-06-2024 00:00");
        employDriverRequest2.setContractedHours(35);
        employDriverRequest2.setCompany("Lee Transport");
        ResponseEntity<EmployDriverResponse> responseEntity2 = driverController.employDriver(employDriverRequest2);
        assertEquals(409, responseEntity2.getStatusCode().value());
        //Employ driver which does not exist but does not validate and cannot be added to the database.
        employDriverRequest2.setContractedHours(-2);
        ResponseEntity<EmployDriverResponse> responseEntity3 = driverController.employDriver(employDriverRequest2);
        assertEquals(400, responseEntity3.getStatusCode().value());
    }

}
