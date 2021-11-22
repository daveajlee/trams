package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Route;
import de.davelee.trams.operations.request.AddRouteRequest;
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

/**
 * This class provides REST endpoints which provide operations associated with a single route in the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Api(value="/trams-operations/route")
@RequestMapping(value="/trams-operations/route")
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
    @ApiOperation(value = "Add route", notes="Add a route for this company")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully add route")})
    public ResponseEntity<Void> addRoute (@RequestBody final AddRouteRequest routeRequest ) {
        //Check that the request is valid, otherwise bad request.
        if (StringUtils.isBlank(routeRequest.getCompany()) || StringUtils.isBlank(routeRequest.getRouteNumber())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Convert the RouteRequest to the Route object.
        Route route = Route.builder()
                .company(routeRequest.getCompany())
                .routeNumber(routeRequest.getRouteNumber())
                .build();
        //Attempt to add the route to the database.
        return routeService.addRoute(route) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

}
