package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Route;
import de.davelee.trams.operations.response.RouteResponse;
import de.davelee.trams.operations.response.RoutesResponse;
import de.davelee.trams.operations.service.RouteService;
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

/**
 * This class provides REST endpoints which provide operations associated with routes in the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Api(value="/api/routes")
@RequestMapping(value="/api/routes")
public class RoutesController {

    @Autowired
    private RouteService routeService;

    /**
     * Return all routes for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>List</code> of <code>Route</code> objects which may be null if there are no routes in the database.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @ApiOperation(value = "Get routes", notes="Return all routes")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully returned routes"),@ApiResponse(code=204,message="Successful but no vehicles found")})
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
     * Delete all routes for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to delete routes for.
     * @return a <code>List</code> of <code>Route</code> objects which may be null if there are no routes in the database.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @ApiOperation(value = "Delete routes", notes="Delete all routes")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully deleted routes"),@ApiResponse(code=204,message="Successful but no vehicles found")})
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
