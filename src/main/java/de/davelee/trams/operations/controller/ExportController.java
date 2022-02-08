package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Route;
import de.davelee.trams.operations.model.Vehicle;
import de.davelee.trams.operations.response.ExportResponse;
import de.davelee.trams.operations.response.RouteResponse;
import de.davelee.trams.operations.response.VehicleResponse;
import de.davelee.trams.operations.service.RouteService;
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

/**
 * This class provides REST endpoints which provide operations associated with exporting data from the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/export")
@RequestMapping(value="/api/export")
public class ExportController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private VehicleService vehicleService;

    /**
     * Return all routes and vehicles for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>ExportResponse</code> object which may be null if there are no routes or vehicles in the database.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "Export routes and vehicles", description="Return all routes and vehicles")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned data"),@ApiResponse(responseCode="204",description="Successful but no data found")})
    public ResponseEntity<ExportResponse> getExport (final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Retrieve the routes for this company.
        List<Route> routes = routeService.getRoutesByCompany(company);
        //Retrieve the vehicles for this company.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompany(company);
        //If routes is null or empty then return 204.
        if ( (routes == null || routes.size() == 0) && (vehicles == null || vehicles.size() == 0) ) {
            return ResponseEntity.noContent().build();
        }
        RouteResponse[] routeResponses = new RouteResponse[routes.size()];
        for ( int i = 0; i < routeResponses.length; i++ ) {
            routeResponses[i] = RouteResponse.builder()
                    .company(routes.get(i).getCompany())
                    .routeNumber(routes.get(i).getRouteNumber())
                    .build();
        }
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
        //Return export data.
        return ResponseEntity.ok(ExportResponse
                .builder()
                        .routeResponses(routeResponses)
                        .vehicleResponses(vehicleResponses)
                .build());
    }

}
