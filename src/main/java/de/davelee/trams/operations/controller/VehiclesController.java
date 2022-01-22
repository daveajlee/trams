package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.*;
import de.davelee.trams.operations.request.LoadVehicleRequest;
import de.davelee.trams.operations.request.LoadVehiclesRequest;
import de.davelee.trams.operations.response.VehicleResponse;
import de.davelee.trams.operations.response.VehiclesResponse;
import de.davelee.trams.operations.service.VehicleService;
import de.davelee.trams.operations.utils.DateUtils;
import de.davelee.trams.operations.utils.VehicleUtils;
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
 * This class provides REST endpoints which provide operations associated with vehicles in the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/vehicles")
@RequestMapping(value="/api/vehicles")
public class VehiclesController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Endpoint to load existing vehicles for a company.
     * @param loadVehiclesRequest a <code>LoadVehiclesRequest</code> object containing the vehicle information to add.
     * @return a <code>ResponseEntity</code>
     */
    @PostMapping("/")
    @CrossOrigin
    @Operation(summary = "Load vehicles", description="Load all vehicles for a particular company")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully loaded vehicles")})
    public ResponseEntity<Void> loadVehicles (@RequestBody final LoadVehiclesRequest loadVehiclesRequest) {
        //First of all check that the request is valid i.e. the list is not empty.
        if (loadVehiclesRequest.getCount() <= 0 || loadVehiclesRequest.getLoadVehicleRequests().length <= 0 ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Now go through the array and convert to a vehicle and add it,
        for (LoadVehicleRequest loadVehicleRequest : loadVehiclesRequest.getLoadVehicleRequests()) {
            if (!vehicleService.addVehicle(VehicleUtils.convertToVehicle(loadVehicleRequest))) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve vehicle information for a particular company and optionally for a supplied fleet number that
     * the vehicle starts with.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @param fleetNumber a <code>String</code> containing the fleet number to search for (optional).
     * @return a <code>List</code> of <code>VehicleResponse</code> objects which may be null if there are no vehicles found.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "Get vehicles", description="Return all vehicles matching company and if supplied fleet number")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned vehicles"), @ApiResponse(responseCode="204",description="Successful but no vehicles found")})
    public ResponseEntity<VehiclesResponse> getVehiclesByCompanyAndFleetNumber (final String company, final Optional<String> fleetNumber ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Retrieve the vehicle based on whether the optional fleet number parameter was supplied.
        List<Vehicle> vehicles;
        if ( fleetNumber.isPresent() ) {
            vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(company, fleetNumber.get());
        } else {
            vehicles = vehicleService.retrieveVehiclesByCompany(company);
        }
        //If vehicles is null or empty then return 204.
        if ( vehicles == null || vehicles.size() == 0 ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise convert to vehicles response and return.
        VehicleResponse[] vehicleResponses = new VehicleResponse[vehicles.size()];
        for ( int i = 0; i < vehicleResponses.length; i++ ) {
            vehicleResponses[i] = VehicleResponse.builder()
                    .allocatedTour(vehicles.get(i).getAllocatedTour())
                    .delayInMinutes(vehicles.get(i).getDelayInMinutes())
                    .fleetNumber(vehicles.get(i).getFleetNumber())
                    .livery(vehicles.get(i).getLivery())
                    .company(vehicles.get(i).getCompany())
                    .additionalTypeInformationMap(vehicles.get(i).getTypeSpecificInfos())
                    .vehicleType(vehicles.get(i).getVehicleType().getTypeName())
                    .userHistory(VehicleUtils.convertHistoryEntriesToResponse(vehicles.get(i).getVehicleHistoryEntryList()))
                    .modelName(vehicles.get(i).getModelName())
                    .purchasePrice(vehicles.get(i).getVehicleType().getPurchasePrice().doubleValue())
                    .seatingCapacity(vehicles.get(i).getSeatingCapacity())
                    .standingCapacity(vehicles.get(i).getStandingCapacity())
                    .deliveryDate(DateUtils.convertLocalDateToDate(vehicles.get(i).getDeliveryDate()))
                    .inspectionDate(DateUtils.convertLocalDateToDate(vehicles.get(i).getInspectionDate()))
                    .vehicleStatus(vehicles.get(i).getVehicleStatus() != null ? vehicles.get(i).getVehicleStatus().name() : null)
                    .timesheet(VehicleUtils.convertTimesheetToResponse(vehicles.get(i).getTimesheet()))
                    .build();
            VehicleUtils.processInspectionDate(vehicleResponses[i], vehicles.get(i).getInspectionDate(), vehicles.get(i).getVehicleType().getInspectionPeriod());
        }
        return ResponseEntity.ok(VehiclesResponse.builder()
                .count((long) vehicleResponses.length)
                .vehicleResponses(vehicleResponses).build());
    }

    /**
     * Delete all vehicles currently stored in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete vehicles", description="Delete all vehicles")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted vehicles")})
    public ResponseEntity<Void> deleteVehicles (final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete all vehicles for this company.
        vehicleService.deleteVehicles(company);
        //Return ok.
        return ResponseEntity.ok().build();
    }

}
