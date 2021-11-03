package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.*;
import de.davelee.trams.operations.response.VehicleResponse;
import de.davelee.trams.operations.response.VehiclesResponse;
import de.davelee.trams.operations.service.VehicleService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
                    .userHistory(VehicleUtils.convertHistoryEntries(vehicles.get(i).getVehicleHistoryEntryList()))
                    .build();
            VehicleUtils.processInspectionDate(vehicleResponses[i], vehicles.get(i).getInspectionDate(), vehicles.get(i).getVehicleType().getInspectionPeriod());
        }
        return ResponseEntity.ok(VehiclesResponse.builder()
                .count((long) vehicleResponses.length)
                .vehicleResponses(vehicleResponses).build());
    }

    /**
     * Temporary endpoint to add test data which will be removed as soon as data can be added through normal endpoints.
     * @return a <code>ResponseEntity</code> object which returns the http status of this method if it was successful or not.
     */
    @GetMapping("/testdata")
    @ApiOperation(value = "Populate test data", notes="returns nothing")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully added test data")})
    public ResponseEntity<Void> addTestData ( ) {
        //Create test bus
        Vehicle bus = Vehicle.builder()
                .typeSpecificInfos(Map.of("Registration Number", "W234DHDF"))
                .modelName("BendyBus 2000")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .livery("Green with black slide")
                .seatingCapacity(50)
                .standingCapacity(80)
                .vehicleStatus(VehicleStatus.DELIVERED)
                .fleetNumber("213")
                .company("Lee Transport")
                .vehicleType(VehicleType.BUS)
                .build();
        //Create test train
        Vehicle train = Vehicle.builder()
                .modelName("Train 2000 Di")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .livery("Green with black slide")
                .seatingCapacity(50)
                .standingCapacity(80)
                .vehicleStatus(VehicleStatus.DELIVERED)
                .fleetNumber("2130")
                .company("Lee Transport")
                .vehicleType(VehicleType.TRAIN)
                .typeSpecificInfos(Map.of("Power Mode", "Diesel"))
                .build();
        //Create test tram
        Vehicle tram = Vehicle.builder()
                .modelName("Tram 2000 Bi")
                .deliveryDate(LocalDate.of(2021,3,25))
                .inspectionDate(LocalDate.of(2021,4,25))
                .livery("Green with black slide")
                .seatingCapacity(50)
                .standingCapacity(80)
                .vehicleStatus(VehicleStatus.DELIVERED)
                .fleetNumber("2310")
                .company("Lee Transport")
                .vehicleType(VehicleType.TRAM)
                .typeSpecificInfos(Map.of("Bidirectional", "true"))
                .build();
        //Add all three to database
        vehicleService.addVehicle(bus);
        vehicleService.addVehicle(train);
        vehicleService.addVehicle(tram);
        //Return ok.
        return ResponseEntity.ok().build();
    }

}
