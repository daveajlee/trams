package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.request.AddRouteRequest;
import de.davelee.trams.server.response.RouteResponse;
import de.davelee.trams.server.service.RouteService;
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
 * This class provides REST endpoints which provide operations associated with a single route in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/route")
@RequestMapping(value="/api/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    /**
     * Add a route to the database for a particular company.
     * @param routeRequest a <code>RouteRequest</code> object containing the information about this route.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @PostMapping("/")
    @CrossOrigin
    @Operation(summary = "Add route", description="Add a route for this company")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully add route")})
    public ResponseEntity<Void> addRoute (@RequestBody final AddRouteRequest routeRequest ) {
        //Check that the request is valid, otherwise bad request.
        if (StringUtils.isBlank(routeRequest.getCompany()) || StringUtils.isBlank(routeRequest.getRouteNumber())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Convert the RouteRequest to the Route object.
        Route route = Route.builder()
                .company(routeRequest.getCompany())
                .routeNumber(routeRequest.getRouteNumber())
                .startStop(routeRequest.getStartStop())
                .endStop(routeRequest.getEndStop())
                .stops(routeRequest.getStops())
                .nightRoute(routeRequest.isNightRoute())
                .build();
        //Attempt to add the route to the database.
        return routeService.addRoute(route) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

    /**
     * Get a route from the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to retrieve the route for.
     * @param routeNumber a <code>String</code> containing the route number to retrieve.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @GetMapping("/")
    @CrossOrigin
    @Operation(summary = "Get route", description="Get a route for this company")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned route")})
    public ResponseEntity<RouteResponse> getRoute (final String company, final String routeNumber) {
        //Check that the request is valid, otherwise bad request.
        if (StringUtils.isBlank(company) || StringUtils.isBlank(routeNumber)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Route> routes = routeService.getRoutesByCompanyAndRouteNumber(company, routeNumber);
        //More than 1 route indicates data inconsistency.
        if ( routes.size() != 1 ) {
            System.out.println("Number of routes for " + company + " and routeNumber " + routeNumber + " is " + routes.size());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //Translate route response and return.
        return ResponseEntity.ok(RouteResponse.builder()
                .routeNumber(routes.getFirst().getRouteNumber())
                .company(routes.getFirst().getCompany())
                .startStop(routes.getFirst().getStartStop())
                .stops(routes.getFirst().getStops())
                .endStop(routes.getFirst().getEndStop())
                .build());
    }

    /**
     * Delete a route from the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to delete the route for.
     * @param routeNumber a <code>String</code> containing the route number to delete.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete route", description="Delete a route for this company")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted route")})
    public ResponseEntity<Void> deleteRoute (final String company, final String routeNumber) {
        //Check that the request is valid, otherwise bad request.
        if (StringUtils.isBlank(company) || StringUtils.isBlank(routeNumber)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Route> routes = routeService.getRoutesByCompanyAndRouteNumber(company, routeNumber);
        //More than 1 route indicates data inconsistency.
        if ( routes.size() != 1 ) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //Delete route and return a positive answer.
        routeService.deleteRoute(routes.get(0));
        return ResponseEntity.ok().build();
    }

}
