package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.*;
import de.davelee.trams.operations.request.LoadVehicleRequest;
import de.davelee.trams.operations.request.LoadVehiclesRequest;
import de.davelee.trams.operations.response.VehicleResponse;
import de.davelee.trams.operations.response.VehiclesResponse;
import de.davelee.trams.operations.service.VehicleService;
import de.davelee.trams.operations.utils.DateUtils;
import de.davelee.trams.operations.utils.VehicleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value="/trams-operations/vehicles")
@RequestMapping(value="/trams-operations/vehicles")
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
    @ApiOperation(value = "Load vehicles", notes="Load all vehicles for a particular company")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully loaded vehicles")})
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
    @ApiOperation(value = "Get vehicles", notes="Return all vehicles matching company and if supplied fleet number")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully returned vehicles"), @ApiResponse(code=204,message="Successful but no vehicles found")})
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
                    .fleetNumber(vehicles.get(i).getFleetNumber())
                    .livery(vehicles.get(i).getLivery())
                    .company(vehicles.get(i).getCompany())
                    .additionalTypeInformationMap(vehicles.get(i).getTypeSpecificInfos())
                    .vehicleType(vehicles.get(i).getVehicleType().getTypeName())
                    .userHistory(VehicleUtils.convertHistoryEntriesToResponse(vehicles.get(i).getVehicleHistoryEntryList()))
                    .modelName(vehicles.get(i).getModelName())
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

}
