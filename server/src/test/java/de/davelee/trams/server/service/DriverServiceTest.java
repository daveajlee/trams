package de.davelee.trams.server.service;

import de.davelee.trams.server.model.*;
import de.davelee.trams.server.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the DriverService class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
public class DriverServiceTest {

    @InjectMocks
    private DriverService driverService;

    @Mock
    private DriverRepository driverRepository;

    /**
     * Ensure that a driver can be added successfully to the mock database.
     */
    @Test
    public void testAddDriver() {
        Driver driver = Driver.builder()
                .name("Max Mustermann")
                .startDate(LocalDateTime.of(2021, 3, 25, 0, 0))
                .contractedHours(35)
                .company("Lee Buses")
                .build();
        Mockito.when(driverRepository.insert(driver)).thenReturn(driver);
        assertTrue(driverService.addDriver(driver));
    }

}

