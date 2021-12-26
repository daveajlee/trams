package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.request.ImportZipRequest;
import de.davelee.trams.operations.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * This class provides REST endpoints which provide operations associated with uploading of data.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/upload")
@RequestMapping(value="/api/upload")
public class UploadController {

    @Autowired
    private ImportGTFSDataService gtfsDataService;

    @Autowired
    private ImportCSVDataService csvDataService;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    /**
     * Upload a zip file containing files either fulfilling the GTFS specification or the CSV specification.
     * Optionally a list of routes can be provided which should be imported and may be null if all routes should be imported.
     * Optionally a valid from and valid to date can also be provided (which are only read in the csv import).
     * The GTFS specification is specified here: https://developers.google.com/transit/gtfs
     * @param importZipRequest a <code>ImportZipRequest</code> containing the zip file, list of routes to import
     *                            and valid from and valid to dates.
     * @return a <code>ResponseEntity</code> object which returns the http status of this method if it was successful or not.
     */
    @PostMapping("/")
    @CrossOrigin
    @Operation(summary = "Upload Data file", description="Upload a GTFS or CSV Zip file to TraMS")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully imported GTFS/CSV Data"), @ApiResponse(responseCode="422",description="Entity could not be processed because zip file was not valid")})
    public ResponseEntity<Void> handleFileUpload(@ModelAttribute final ImportZipRequest importZipRequest) {
        String folderName = fileSystemStorageService.store(importZipRequest.getZipFile());
        List<String> routesToImport =  importZipRequest.getRoutesToImport() != null ?
                                        Arrays.asList(importZipRequest.getRoutesToImport().split(",")) : new ArrayList<>();
        if ( importZipRequest.getFileFormat().contentEquals("General Transit Feed Specification (GTFS)")) {
            if (gtfsDataService.readGTFSFile(folderName, routesToImport)) {
                return ResponseEntity.ok().build();
            }
        } else if ( importZipRequest.getFileFormat().contentEquals("Comma Separated Value (CSV)")) {
            if (csvDataService.readCSVFile(folderName, importZipRequest.getValidFromDate(), importZipRequest.getValidToDate())) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.unprocessableEntity().build();
    }

}
