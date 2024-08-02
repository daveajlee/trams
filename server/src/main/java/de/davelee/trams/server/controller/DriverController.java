package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Driver;
import de.davelee.trams.server.request.EmployDriverRequest;
import de.davelee.trams.server.response.EmployDriverResponse;
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
 * This class provides REST endpoints which provide operations associated with a single driver in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/driver")
@RequestMapping(value="/api/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    /**
     * Employ a driver. Most of the fields are supplied in the request. The price to employ the driver will be returned.
     *
     * @param employDriverRequest a <code>EmployDriverRequest</code> object containing the information about the driver which should be employed.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Employ a driver", description = "Employ a driver and return the price to employ this driver")
    @PostMapping(value = "/")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully employed driver"), @ApiResponse(responseCode = "409", description = "Driver conflicted with a driver that already exists")})
    public ResponseEntity<EmployDriverResponse> employDriver(@RequestBody EmployDriverRequest employDriverRequest) {
        //Check that the request is valid.
        if (StringUtils.isBlank(employDriverRequest.getCompany()) || StringUtils.isBlank(employDriverRequest.getName())
                || StringUtils.isBlank(employDriverRequest.getStartDate()) || employDriverRequest.getContractedHours() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this driver does not already exist.
        List<Driver> drivers = driverService.retrieveDriversByCompanyAndName(employDriverRequest.getCompany(), employDriverRequest.getName());
        System.out.println(drivers);
        if (drivers != null && !drivers.isEmpty()) {
            return ResponseEntity.of(Optional.of(EmployDriverResponse.builder().employmentCost(0).employed(false).build())).status(409).build();
        }
        //Construct the driver and add it to the database.
        Driver driver = Driver.builder()
                .company(employDriverRequest.getCompany())
                .name(employDriverRequest.getName())
                .contractedHours(employDriverRequest.getContractedHours())
                .startDate(DateUtils.convertDateToLocalDateTime(employDriverRequest.getStartDate()))
                .build();
        if (driverService.addDriver(driver)) {
            //Return the hiring costs for the driver if they were employed successfully.
            return ResponseEntity.ok(EmployDriverResponse.builder().employed(true).employmentCost(500).build());
        }
        //Otherwise return an empty 500 response.
        return ResponseEntity.of(Optional.of(EmployDriverResponse.builder().employmentCost(0).employed(false).build())).status(500).build();
    }

    /**
     * Delete the driver currently stored in the database for a particular company with the particular name.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @param name a <code>String</code> containing the name of the driver.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete driver", description="Delete driver with name and company")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted driver")})
    public ResponseEntity<Void> deleteDriver (final String company, final String name ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company) && StringUtils.isBlank(name)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete the driver.
        driverService.deleteDriversByCompanyAndName(company, name);
        //Return ok.
        return ResponseEntity.ok().build();
    }

}