package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Vehicle;
import de.davelee.trams.operations.constant.VehicleHistoryReason;
import de.davelee.trams.operations.constant.VehicleType;
import de.davelee.trams.operations.request.*;
import de.davelee.trams.operations.response.*;
import de.davelee.trams.operations.service.VehicleService;
import de.davelee.trams.operations.utils.DateUtils;
import de.davelee.trams.operations.utils.VehicleUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * This class provides REST endpoints which provide operations associated with a single vehicle in the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/vehicle")
@RequestMapping(value="/api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Purchase a vehicle. Most of the fields are supplied in the request. The purchase price of the vehicle will be returned.
     * @param purchaseVehicleRequest a <code>PurchaseVehicleRequest</code> object containing the information about the vehicle which should be purchased.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Purchase a particular vehicle", description="Purchase a particular vehicle and return the purchase price")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully purchased vehicles"), @ApiResponse(responseCode="409",description="Vehicle conflicted with a vehicle that already exists")})
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
    @Operation(summary = "Add a number of hours to a particular vehicle", description="Add a number of hours to a specified date for a specified vehicle")
    @PatchMapping(value="/hours")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully added hours"), @ApiResponse(responseCode="204",description="No vehicle found")})
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
    @Operation(summary = "Retrieve the number of hours for a particular vehicle", description="Add a number of hours to a specified date for a specified vehicle")
    @GetMapping(value="/hours")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully retrieved hours"), @ApiResponse(responseCode="204",description="No vehicle found")})
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
    @Operation(summary = "Add a new history entry", description="Add a new history entry for a particular vehicle.")
    @PatchMapping(value="/history")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully added history entry"), @ApiResponse(responseCode="204",description="No vehicle found")})
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

    /**
     * Sell the vehicle matching the supplied company and fleet number. The purchase price of the vehicle will be returned without depreciation.
     * @param sellVehicleRequest a <code>SellVehicleRequest</code> object containing the information about the vehicle which should be sold.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Sell a particular vehicle", description="Sell a particular vehicle and return the money gained from the sale")
    @PatchMapping(value="/sell")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully sold vehicle"), @ApiResponse(responseCode="204",description="No vehicle found")})
    public ResponseEntity<SellVehicleResponse> sellVehicle (@RequestBody SellVehicleRequest sellVehicleRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(sellVehicleRequest.getCompany()) || StringUtils.isBlank(sellVehicleRequest.getFleetNumber())) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this vehicle exists otherwise it cannot be sold.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(sellVehicleRequest.getCompany(), sellVehicleRequest.getFleetNumber());
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now sell the vehicle.
        BigDecimal soldPrice = vehicleService.sellVehicle(vehicles.get(0));
        //Return response of selling the vehicle with sold price that is 0 if vehicle could not be sold.
        return ResponseEntity.ok(SellVehicleResponse.builder()
                .sold(soldPrice.doubleValue() > 0)
                .soldPrice(soldPrice.doubleValue())
                .build());
    }

    /**
     * Inspect the vehicle matching the supplied company and fleet number. The inspection price of the vehicle will be returned.
     * @param inspectVehicleRequest a <code>InspectVehicleRequest</code> object containing the information about the vehicle which should be inspected.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Inspect a particular vehicle", description="Inspect a particular vehicle and return the cost of the inspection")
    @PatchMapping(value="/inspect")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully inspected vehicle"), @ApiResponse(responseCode="204",description="No vehicle found")})
    public ResponseEntity<InspectVehicleResponse> inspectVehicle (@RequestBody InspectVehicleRequest inspectVehicleRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(inspectVehicleRequest.getCompany()) || StringUtils.isBlank(inspectVehicleRequest.getFleetNumber())) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this vehicle exists otherwise it cannot be inspected.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(inspectVehicleRequest.getCompany(), inspectVehicleRequest.getFleetNumber());
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now inspect the vehicle.
        BigDecimal inspectionPrice = vehicleService.inspectVehicle(vehicles.get(0));
        //Return response of inspecting the vehicle with price that is 0 if vehicle could not be inspected.
        return ResponseEntity.ok(InspectVehicleResponse.builder()
                .inspected(inspectionPrice.doubleValue() > 0)
                .inspectionPrice(inspectionPrice.doubleValue())
                .build());
    }

    /**
     * Allocate the vehicle matching the supplied company and fleet number to the supplied tour.
     * @param allocateVehicleRequest a <code>AllocateVehicleRequest</code> object containing the information about the vehicle and the allocation.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Allocate a particular vehicle", description="Allocate a particular vehicle to a particular tour")
    @PatchMapping(value="/allocate")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully allocated vehicle"), @ApiResponse(responseCode="204",description="No vehicle found")})
    public ResponseEntity<Void> allocateVehicle (@RequestBody AllocateVehicleRequest allocateVehicleRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(allocateVehicleRequest.getCompany()) || StringUtils.isBlank(allocateVehicleRequest.getFleetNumber())
                || StringUtils.isBlank(allocateVehicleRequest.getAllocatedTour()) ) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this vehicle exists otherwise it cannot be allocated.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(allocateVehicleRequest.getCompany(), allocateVehicleRequest.getFleetNumber());
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now allocate the vehicle.
        return vehicleService.allocateTourToVehicle(vehicles.get(0), allocateVehicleRequest.getAllocatedRoute(), allocateVehicleRequest.getAllocatedTour()) ?
                ResponseEntity.ok().build() : ResponseEntity.status(500).build();
    }

    /**
     * Return the vehicle matching the supplied company and which is allocated to the supplied route and tour.
     * @param company a <code>String</code> object containing the name of the company to return the vehicle for.
     * @param allocatedRoute a <code>String</code> object containing the route number which the vehicle must match.
     * @param allocatedTour a <code>String</code> object containing the tour name which the vehicle must match.
     * @return a <code>ResponseEntity</code> containing the results of the matching vehicle which may be null if no vehicle exists.
     */
    @Operation(summary = "Return an allocated vehicle", description="Return a particular vehicle which is allocated to a particular route and tour")
    @GetMapping(value="/allocate")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully retrieved vehicle"), @ApiResponse(responseCode="204",description="No vehicle found")})
    public ResponseEntity<VehicleResponse> getAllocatedVehicle (final String company, final String allocatedRoute, final String allocatedTour) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(company) || StringUtils.isBlank(allocatedTour)) {
            return ResponseEntity.badRequest().build();
        }
        //Check that a single vehicle is allocated to this tour, otherwise return no content.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndAllocatedRouteAndAllocatedTour(company, allocatedRoute, allocatedTour);
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Return the vehicle information.
        return ResponseEntity.ok(VehicleResponse.builder()
                .allocatedRoute(vehicles.get(0).getAllocatedRoute())
                .allocatedTour(vehicles.get(0).getAllocatedTour())
                .delayInMinutes(vehicles.get(0).getDelayInMinutes())
                .fleetNumber(vehicles.get(0).getFleetNumber())
                .livery(vehicles.get(0).getLivery())
                .company(vehicles.get(0).getCompany())
                .additionalTypeInformationMap(vehicles.get(0).getTypeSpecificInfos())
                .vehicleType(vehicles.get(0).getVehicleType().getTypeName())
                .userHistory(VehicleUtils.convertHistoryEntriesToResponse(vehicles.get(0).getVehicleHistoryEntryList()))
                .modelName(vehicles.get(0).getModelName())
                .purchasePrice(vehicles.get(0).getVehicleType().getPurchasePrice().doubleValue())
                .seatingCapacity(vehicles.get(0).getSeatingCapacity())
                .standingCapacity(vehicles.get(0).getStandingCapacity())
                .deliveryDate(DateUtils.convertLocalDateToDate(vehicles.get(0).getDeliveryDate()))
                .inspectionDate(DateUtils.convertLocalDateToDate(vehicles.get(0).getInspectionDate()))
                .vehicleStatus(vehicles.get(0).getVehicleStatus() != null ? vehicles.get(0).getVehicleStatus().name() : null)
                .timesheet(VehicleUtils.convertTimesheetToResponse(vehicles.get(0).getTimesheet()))
                .build());
    }

    /**
     * Remove the allocation of the vehicle matching the supplied company and fleet number.
     * @param removeVehicleRequest a <code>RemoveVehicleRequest</code> object containing the information about the vehicle.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Remove a particular allocation", description="Remove the allocation of a particular vehicle to a particular tour")
    @DeleteMapping(value="/allocate")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully removed allocation"), @ApiResponse(responseCode="204",description="No vehicle found")})
    public ResponseEntity<Void> removeVehicleAllocation (@RequestBody RemoveVehicleRequest removeVehicleRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(removeVehicleRequest.getCompany()) || StringUtils.isBlank(removeVehicleRequest.getFleetNumber())) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this vehicle exists otherwise allocations cannot be removed.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(removeVehicleRequest.getCompany(), removeVehicleRequest.getFleetNumber());
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now remove the allocation of this vehicle.
        return vehicleService.allocateTourToVehicle(vehicles.get(0), "", "") ?
                ResponseEntity.ok().build() : ResponseEntity.status(500).build();
    }

    /**
     * Adjust the delay of the vehicle matching the supplied company and fleet number. The current delay after adjustment will be returned.
     * @param adjustVehicleDelayRequest a <code>AdjustVehicleDelayRequest</code> object containing the information about the vehicle which should have its delay adjusted.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Adjust delay of a particular vehicle", description="Adjust the delay of a particular vehicle in minutes")
    @PatchMapping(value="/delay")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully adjusted delay of vehicle"), @ApiResponse(responseCode="204",description="No vehicle found")})
    public ResponseEntity<VehicleDelayResponse> adjustVehicleDelay (@RequestBody AdjustVehicleDelayRequest adjustVehicleDelayRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(adjustVehicleDelayRequest.getCompany()) || StringUtils.isBlank(adjustVehicleDelayRequest.getFleetNumber())) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this vehicle exists otherwise the delay cannot be adjusted.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(adjustVehicleDelayRequest.getCompany(), adjustVehicleDelayRequest.getFleetNumber());
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now adjust the delay of the vehicle and return current delay.
        return ResponseEntity.ok(VehicleDelayResponse.builder()
                .company(vehicles.get(0).getCompany())
                .fleetNumber(vehicles.get(0).getFleetNumber())
                .delayInMinutes(vehicleService.adjustVehicleDelay(vehicles.get(0), adjustVehicleDelayRequest.getDelayInMinutes()))
                .build());
    }

    /**
     * Get the current value of the vehicle matching the supplied company and fleet number. The current value is based on purchase price + depreciation factor.
     * @param company a <code>String</code> object containing the name of the company to return the vehicle for.
     * @param fleetNumber a <code>String</code> containing the fleet number of the vehicle.
     * @param date a <code>String</code> containing the date to retrieve the hours for in format dd-MM-yyyy.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Get current value of a particular vehicle", description="Get current value of a particular vehicle at this date")
    @GetMapping(value="/value")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully calculated value of vehicle"), @ApiResponse(responseCode="204",description="No vehicle found")})
    public ResponseEntity<VehicleValueResponse> getValue (final String company, final String fleetNumber, final String date) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(company) || StringUtils.isBlank(fleetNumber) || StringUtils.isBlank(date)) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this vehicle exists otherwise the value cannot be calculated.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndFleetNumber(company, fleetNumber);
        if ( vehicles == null || vehicles.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now calculate the current value of this vehicle.
        Vehicle vehicle = vehicles.get(0);
        int age = Period.between(vehicle.getDeliveryDate(), LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))).getYears();
        double value = age == 0 ? vehicle.getVehicleType().getPurchasePrice().doubleValue() : vehicle.getVehicleType().getPurchasePrice().doubleValue() - (vehicle.getVehicleType().getDepreciationFactor() * age) * vehicle.getVehicleType().getPurchasePrice().doubleValue();
        //A value below 0 is not allowed.
        value = value < 0 ? 0.0 : value;
        //Now adjust the delay of the vehicle and return current delay.
        return ResponseEntity.ok(VehicleValueResponse.builder()
                .company(vehicles.get(0).getCompany())
                .fleetNumber(vehicles.get(0).getFleetNumber())
                .value(value)
                .build());
    }

}
