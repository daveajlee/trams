package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Driver;
import de.davelee.trams.server.response.DriverResponse;
import de.davelee.trams.server.response.DriversResponse;
import de.davelee.trams.server.service.DriverService;
import de.davelee.trams.server.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class provides REST endpoints which provide operations associated with drivers in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/drivers")
@RequestMapping(value="/api/drivers")
public class DriversController {

    @Autowired
    private DriverService driverService;

    /**
     * Return all drivers currently stored in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>List</code> of <code>Driver</code> objects which may be null if there are no drivers in the database.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "Get drivers", description="Return all drivers for the company")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned drivers")})
    public ResponseEntity<DriversResponse> getDriversAndName (final String company, final Optional<String> name) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Retrieve the drivers for this company.
        List<Driver> drivers;
        if ( name.isPresent() ) {
            drivers = driverService.retrieveDriversByCompanyAndName(company, name.get());
        } else {
            drivers = driverService.retrieveDriversByCompany(company);
        }
        //If timetables is null or empty then return 204.
        if ( drivers == null || drivers.isEmpty() ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise convert to drivers response and return.
        DriverResponse[] driverResponses = new DriverResponse[drivers.size()];
        for ( int i = 0; i < driverResponses.length; i++ ) {
            driverResponses[i] = DriverResponse.builder()
                    .company(drivers.get(i).getCompany())
                    .name(drivers.get(i).getName())
                    .contractedHours(drivers.get(i).getContractedHours())
                    .startDate(DateUtils.convertLocalDateTimeToDate(drivers.get(i).getStartDate()))
                    .build();
        }
        return ResponseEntity.ok(DriversResponse.builder()
                .count((long) driverResponses.length)
                .driverResponses(driverResponses).build());
    }

    /**
     * Delete all drivers currently stored in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete drivers", description="Delete all drivers")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted drivers")})
    public ResponseEntity<Void> deleteDrivers (final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete all drivers for this company.
        driverService.deleteDrivers(company);
        //Return ok.
        return ResponseEntity.ok().build();
    }

}
