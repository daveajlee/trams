package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Vehicle;
import de.davelee.trams.operations.request.AddVehicleHoursRequest;
import de.davelee.trams.operations.service.VehicleService;
import de.davelee.trams.operations.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class provides REST endpoints which provide operations associated with a single vehicle in the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Api(value="/trams-operations/vehicle")
@RequestMapping(value="/trams-operations/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Add a number of hours for this vehicle for the particular date. If the vehicle already has hours for this
     * particular date, the hours will be increased by the supplied amount.
     * @param addVehicleHoursRequest a <code>AddVehicleHoursRequest</code> object containing the information to update.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @ApiOperation(value = "Add a number of hours to a particular vehicle", notes="Add a number of hours to a specified date for a specified vehicle")
    @PutMapping(value="/timesheet")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully added hours"), @ApiResponse(code=204,message="No vehicle found")})
    public ResponseEntity<Void> addHoursForDate (@RequestBody AddVehicleHoursRequest addVehicleHoursRequest) {
        //Check valid request
        if (StringUtils.isBlank(addVehicleHoursRequest.getCompany()) || StringUtils.isBlank(addVehicleHoursRequest.getFleetNumber()) ||
                StringUtils.isBlank(addVehicleHoursRequest.getDate()) || addVehicleHoursRequest.getHours() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        //Now retrieve the vehicle based on company and fleet number.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(addVehicleHoursRequest.getCompany(), addVehicleHoursRequest.getFleetNumber());
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now add the hours and return 200 or 500 depending on DB success.
        return vehicleService.addHoursForDate(vehicles.get(0), addVehicleHoursRequest.getHours(), DateUtils.convertDateToLocalDate(addVehicleHoursRequest.getDate())) ?
                ResponseEntity.status(200).build() : ResponseEntity.status(500).build();
    }

}
