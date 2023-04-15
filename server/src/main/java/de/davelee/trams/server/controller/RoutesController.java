package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.response.RouteResponse;
import de.davelee.trams.server.response.RoutesResponse;
import de.davelee.trams.server.service.RouteService;
import de.davelee.trams.server.service.StopTimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides REST endpoints which provide operations associated with routes in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/routes")
@RequestMapping(value="/api/routes")
public class RoutesController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopTimeService stopTimeService;

    /**
     * Return all routes for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>List</code> of <code>Route</code> objects which may be null if there are no routes in the database.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "Get routes", description="Return all routes")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned routes"),@ApiResponse(responseCode="204",description="Successful but no vehicles found")})
    public ResponseEntity<RoutesResponse> getRoutesByCompany ( final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Retrieve the routes for this company.
        List<Route> routes = routeService.getRoutesByCompany(company);
        //If routes is null or empty then return 204.
        if ( routes == null || routes.size() == 0 ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise convert to routes response and return.
        RouteResponse[] routeResponses = new RouteResponse[routes.size()];
        for ( int i = 0; i < routeResponses.length; i++ ) {
            routeResponses[i] = RouteResponse.builder()
                    .company(routes.get(i).getCompany())
                    .routeNumber(routes.get(i).getRouteNumber())
                    .build();
        }
        return ResponseEntity.ok(RoutesResponse.builder()
                .count((long) routeResponses.length)
                .routeResponses(routeResponses).build());
    }


    /**
     * Return all routes for a particular stop and company.
     * @param stop a <code>String</code> containing the stop to return routes for.
     * @param company a <code>String</code> containing the name of the company to return routes for.
     * @return a <code>List</code> of <code>Route</code> objects which may be null if there are no routes in the database.
     */
    @GetMapping("/allRoutesForStop")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "View all routes for a company for a particular stop", description="Return all matching routes")
    public ResponseEntity<RoutesResponse> allRoutesForStop ( final String stop, final String company) {
        // Retrieve the route numbers for this company and stop.
        List<String> routeNumbers = stopTimeService.getAllRouteNumbersByStop(company, stop);
        //Convert the list of route numbers to a list of routes.
        List<Route> routes = new ArrayList<>();
        for ( String routeNumber : routeNumbers ) {
            routes.addAll(routeService.getRoutesByCompanyAndRouteNumber(company, routeNumber));
        }
        //If routes is null or empty then return 204.
        if ( routes == null || routes.size() == 0 ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise convert to routes response and return.
        RouteResponse[] routeResponses = new RouteResponse[routes.size()];
        for ( int i = 0; i < routeResponses.length; i++ ) {
            routeResponses[i] = RouteResponse.builder()
                    .company(routes.get(i).getCompany())
                    .routeNumber(routes.get(i).getRouteNumber())
                    .build();
        }
        return ResponseEntity.ok(RoutesResponse.builder()
                .count((long) routeResponses.length)
                .routeResponses(routeResponses).build());
    }

    /**
     * Delete all routes for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to delete routes for.
     * @return a <code>List</code> of <code>Route</code> objects which may be null if there are no routes in the database.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete routes", description="Delete all routes")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted routes"),@ApiResponse(responseCode="204",description="Successful but no vehicles found")})
    public ResponseEntity<Void> deleteRoutesByCompany ( final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Retrieve the routes for this company.
        List<Route> routes = routeService.getRoutesByCompany(company);
        //If routes is null or empty then return 204.
        if ( routes == null || routes.size() == 0 ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise delete all routes and return.
        for ( Route route : routes ) {
            routeService.deleteRoute(route);
        }
        return ResponseEntity.ok().build();
    }

}
