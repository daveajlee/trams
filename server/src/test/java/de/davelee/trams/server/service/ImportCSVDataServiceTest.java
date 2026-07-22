package de.davelee.trams.server.service;

import de.davelee.trams.server.repository.RouteRepository;
import de.davelee.trams.server.repository.StopRepository;
import de.davelee.trams.server.repository.StopTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the ImportCSVDataService class and ensures that the import work successfully. Mocks are used
 * for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ImportCSVDataServiceTest {

    @InjectMocks
    private ImportCSVDataService importCSVDataService;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private StopRepository stopRepository;

    @Mock
    private StopTimeRepository stopTimeRepository;

    /**
     * Verify that it is possible to import the sample directory.
     */
    @Test
    public void testCSVDataService ( ) {
        File file = new File("src/test/resources/my-network-landuff");
        assertTrue(importCSVDataService.readCSVFile(file.getAbsolutePath(), "2021-01-01 00:00", "2021-12-31 00:00"));
        assertFalse(importCSVDataService.readCSVFile("no-feed", "2021-01-01 00:00", "2021-12-31 00:00"));
    }

}
