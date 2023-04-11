package de.davelee.trams.server.controller;

import de.davelee.trams.server.request.ImportZipRequest;
import de.davelee.trams.server.service.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

/**
 * This class tests the UploadController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class UploadControllerTest {

    @InjectMocks
    private UploadController controller;

    @Mock
    private ImportGTFSDataService importGTFSDataService;

    @Mock
    private ImportCSVDataService csvDataService;

    @Mock
    private FileSystemStorageService fileSystemStorageService;

    /**
     * Test the file upload endpoint of this controller.
     */
    @Test
    public void testHandleFileUpload() {
        //First of all test the happy case that it works as planned.
        ImportZipRequest importZipRequest = new ImportZipRequest();
        importZipRequest.setZipFile(new MockMultipartFile("test", new byte[8]));
        importZipRequest.setFileFormat("General Transit Feed Specification (GTFS)");
        importZipRequest.setRoutesToImport("1A,2B");
        Mockito.when(fileSystemStorageService.store(importZipRequest.getZipFile())).thenReturn("testFolder");
        Mockito.when(importGTFSDataService.readGTFSFile("testFolder", Lists.newArrayList("1A", "2B"))).thenReturn(true);
        ResponseEntity<Void> uploadResponse = controller.handleFileUpload(importZipRequest);
        assertEquals(HttpStatus.OK, uploadResponse.getStatusCode());
        //Second test the case where file could not be loaded.
        ImportZipRequest importGtfsZipBadRequest = new ImportZipRequest();
        importGtfsZipBadRequest.setZipFile(new MockMultipartFile("test", new byte[8]));
        importGtfsZipBadRequest.setRoutesToImport("3C,4D");
        importGtfsZipBadRequest.setFileFormat("General Transit Feed Specification (GTFS)");
        Mockito.when(fileSystemStorageService.store(importGtfsZipBadRequest.getZipFile())).thenReturn("testBadFolder");
        Mockito.when(importGTFSDataService.readGTFSFile("testFolder", Lists.newArrayList("3C", "3D"))).thenReturn(false);
        ResponseEntity<Void> uploadBadResponse = controller.handleFileUpload(importGtfsZipBadRequest);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, uploadBadResponse.getStatusCode());
        //Third test the case where a csv file is uploaded.
        ImportZipRequest importCsvZipRequest = new ImportZipRequest();
        try {
            importCsvZipRequest.setZipFile(new MockMultipartFile("test", this.getClass().getResourceAsStream("my-network-landuff/ft1.csv")));
            importCsvZipRequest.setFileFormat("Comma Separated Value (CSV)");
            importCsvZipRequest.setValidFromDate("09-08-2020");
            importCsvZipRequest.setValidToDate("16-08-2020");
            Mockito.when(fileSystemStorageService.store(importCsvZipRequest.getZipFile())).thenReturn("testCsvFolder");
            Mockito.when(csvDataService.readCSVFile(anyString(), anyString(), anyString())).thenReturn(true);
            ResponseEntity<Void> uploadGoodResponse = controller.handleFileUpload(importCsvZipRequest);
            assertEquals(HttpStatus.OK, uploadGoodResponse.getStatusCode());
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
