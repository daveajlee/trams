package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Vehicle;
import de.davelee.trams.operations.model.VehicleHistoryReason;
import de.davelee.trams.operations.model.VehicleType;
import de.davelee.trams.operations.request.AddHistoryEntryRequest;
import de.davelee.trams.operations.request.AddVehicleHoursRequest;
import de.davelee.trams.operations.request.PurchaseVehicleRequest;
import de.davelee.trams.operations.response.PurchaseVehicleResponse;
import de.davelee.trams.operations.response.VehicleHoursResponse;
import de.davelee.trams.operations.service.VehicleService;
import de.davelee.trams.operations.utils.DateUtils;
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
import java.util.Optional;

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
     * Purchase a vehicle. Most of the fields are supplied in the request. The purchase price of the bus will be returned.
     * @param purchaseVehicleRequest a <code>PurchaseVehicleRequest</code> object containing the information about the vehicle which should be purchased.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @ApiOperation(value = "Purchase a particular vehicle", notes="Purchase a particular vehicle and return the purchase price")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully purchased vehicles"), @ApiResponse(code=409,message="Vehicle conflicted with a vehicle that already exists")})
    public ResponseEntity<PurchaseVehicleResponse> purchaseVehicle (@RequestBody PurchaseVehicleRequest purchaseVehicleRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(purchaseVehicleRequest.getCompany()) || StringUtils.isBlank(purchaseVehicleRequest.getFleetNumber())
        || StringUtils.isBlank(purchaseVehicleRequest.getVehicleType()) || StringUtils.isBlank(purchaseVehicleRequest.getLivery())
        || purchaseVehicleRequest.getSeatingCapacity() <= 0 || purchaseVehicleRequest.getStandingCapacity() <= 0 ||
        StringUtils.isBlank(purchaseVehicleRequest.getModelName())) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this vehicle does not already exist.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(purchaseVehicleRequest.getCompany(), purchaseVehicleRequest.getFleetNumber());
        if ( vehicles != null && vehicles.size() != 0 ) {
            return ResponseEntity.of(Optional.of(PurchaseVehicleResponse.builder().purchasePrice(0).purchased(false).build())).status(409).build();
        }
        //Construct the vehicle and add it to the database.
        Vehicle vehicle = Vehicle.builder()
                .company(purchaseVehicleRequest.getCompany())
                .deliveryDate(LocalDate.now().plusDays(7))
                .fleetNumber(purchaseVehicleRequest.getFleetNumber())
                .vehicleType(VehicleType.valueOf(purchaseVehicleRequest.getVehicleType()))
                .livery(purchaseVehicleRequest.getLivery())
                .typeSpecificInfos(purchaseVehicleRequest.getAdditionalTypeInformationMap())
                .seatingCapacity(purchaseVehicleRequest.getSeatingCapacity())
                .standingCapacity(purchaseVehicleRequest.getStandingCapacity())
                .modelName(purchaseVehicleRequest.getModelName())
                .build();
        vehicle.addVehicleHistoryEntry(LocalDate.now(), VehicleHistoryReason.PURCHASED, "Vehicle Purchased for " + vehicle.getVehicleType().getPurchasePrice());
        if ( vehicleService.addVehicle(vehicle) ) {
            //Return the purchase price for the bus if it was purchased successfully.
            return ResponseEntity.ok(PurchaseVehicleResponse.builder().purchased(true).purchasePrice(vehicle.getVehicleType().getPurchasePrice().doubleValue()).build());
        }
        //Otherwise return an empty 500 response.
        return ResponseEntity.of(Optional.of(PurchaseVehicleResponse.builder().purchasePrice(0).purchased(false).build())).status(500).build();
    }

    /**
     * Add a number of hours for this vehicle for the particular date. If the vehicle already has hours for this
     * particular date, the hours will be increased by the supplied amount.
     * @param addVehicleHoursRequest a <code>AddVehicleHoursRequest</code> object containing the information to update.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @ApiOperation(value = "Add a number of hours to a particular vehicle", notes="Add a number of hours to a specified date for a specified vehicle")
    @PutMapping(value="/hours")
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

    /**
     * Retrieve the number of hours that a vehicle was in service for a particular date.
     * In addition whether the maximum number of hours for the day was already reached and/or how long until this maximum is reached.
     * @param company a <code>String</code> containing the company that the vehicle belongs to.
     * @param fleetNumber a <code>String</code> containing the fleet number of the vehicle.
     * @param date a <code>String</code> containing the date to retrieve the hours for in format dd-MM-yyyy.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @ApiOperation(value = "Retrieve the number of hours for a particular vehicle", notes="Add a number of hours to a specified date for a specified vehicle")
    @GetMapping(value="/hours")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully retrieved hours"), @ApiResponse(code=204,message="No vehicle found")})
    public ResponseEntity<VehicleHoursResponse> getHoursForDate(final String company, final String fleetNumber, final String date) {
        //Check valid request
        if (StringUtils.isBlank(company) || StringUtils.isBlank(fleetNumber) ||
                StringUtils.isBlank(date)) {
            return ResponseEntity.badRequest().build();
        }
        //Now retrieve the vehicle based on company and fleet number.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(company, fleetNumber);
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise retrieve the hours and build response.
        int numberOfHoursSoFar = vehicles.get(0).getHoursForDate(DateUtils.convertDateToLocalDate(date));
        return ResponseEntity.ok(VehicleHoursResponse.builder()
                        .maximumHoursReached(numberOfHoursSoFar >= vehicles.get(0).getVehicleType().getMaximumHoursPerDay())
                        .numberOfHoursAvailable(vehicles.get(0).getVehicleType().getMaximumHoursPerDay()-numberOfHoursSoFar)
                        .numberOfHoursSoFar(numberOfHoursSoFar)
                .build());
    }

    /**
     * Add a new history entry to the list.
     * @param addHistoryEntryRequest a <code>AddHistoryEntryRequest</code> object containing the information to update.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @ApiOperation(value = "Add a new history entry", notes="Add a new history entry for a particular vehicle.")
    @PutMapping(value="/history")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully added history entry"), @ApiResponse(code=204,message="No vehicle found")})
    public ResponseEntity<Void> addHistoryEntry (@RequestBody AddHistoryEntryRequest addHistoryEntryRequest) {
        //Check valid request
        if (StringUtils.isBlank(addHistoryEntryRequest.getCompany()) || StringUtils.isBlank(addHistoryEntryRequest.getFleetNumber()) ||
                StringUtils.isBlank(addHistoryEntryRequest.getComment()) || StringUtils.isBlank(addHistoryEntryRequest.getReason()) ||
                StringUtils.isBlank(addHistoryEntryRequest.getDate())) {
            return ResponseEntity.badRequest().build();
        }
        //Now retrieve the vehicle based on company and fleet number.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(addHistoryEntryRequest.getCompany(), addHistoryEntryRequest.getFleetNumber());
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now add history entry and return 200 or 500 depending on DB success.
        return vehicleService.addVehicleHistoryEntry(vehicles.get(0), DateUtils.convertDateToLocalDate(addHistoryEntryRequest.getDate()),
                VehicleHistoryReason.valueOf(addHistoryEntryRequest.getReason()), addHistoryEntryRequest.getComment()) ?
                ResponseEntity.status(200).build() : ResponseEntity.status(500).build();
    }

}
