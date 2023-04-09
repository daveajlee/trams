package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Stop;
import de.davelee.trams.operations.response.StopResponse;
import de.davelee.trams.operations.response.StopsResponse;
import de.davelee.trams.operations.service.StopService;
import de.davelee.trams.operations.service.StopTimeService;
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
import java.util.Optional;

/**
 * This class provides REST endpoints which provide operations associated with stops in the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/stops")
@RequestMapping(value="/api/stops")
public class StopsController {

    @Autowired
    private StopService stopService;

    @Autowired
    private StopTimeService stopTimeService;

    /**
     * Return all stops currently stored in the database for a particular company or if a route number
     * is supplied all stops for that company and route number.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @param routeNumber a <code>String</code> containing the route number to search for which is optional.
     * @return a <code>List</code> of <code>Stop</code> objects which may be null if there are no stops in the database.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "Get stops", description="Return all stops for the company with the specified route number (optional)")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned stops")})
    public ResponseEntity<StopsResponse> getStops (final String company, final Optional<String> routeNumber ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Retrieve the stops for this company.
        List<Stop> stops = routeNumber.isPresent() ? getStopsForRouteNumber(company, routeNumber.get()) : stopService.getStopsByCompany(company);
        //If stops is null or empty then return 204.
        if ( stops == null || stops.size() == 0 ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise convert to stops response and return.
        StopResponse[] stopResponses = new StopResponse[stops.size()];
        for ( int i = 0; i < stopResponses.length; i++ ) {
            stopResponses[i] = StopResponse.builder()
                    .company(stops.get(i).getCompany())
                    .latitude(stops.get(i).getLatitude())
                    .longitude(stops.get(i).getLongitude())
                    .waitingTime(stops.get(i).getWaitingTime())
                    .distances(stops.get(i).getDistances())
                    .name(stops.get(i).getName())
                    .build();
        }
        return ResponseEntity.ok(StopsResponse.builder()
                .count((long) stopResponses.length)
                .stopResponses(stopResponses).build());
    }

    /**
     * Delete all stops currently stored in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete stops", description="Delete all stops")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted stops")})
    public ResponseEntity<Void> deleteStops (final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete all stops for this company.
        stopService.deleteStops(company);
        //Return ok.
        return ResponseEntity.ok().build();
    }

    /**
     * Private helper method to retrieve the stops served by a particular route number.
     * @param company a <code>String</code> with the name of the company to search for.
     * @param routeNumber a <code>String</code> with the route number to search for.
     * @return a <code>List</code> of <code>Stop</code> objects which may be null if there are no stops in the database.
     */
    private List<Stop> getStopsForRouteNumber ( final String company, final String routeNumber ) {
        List<Stop> allStops = stopService.getStopsByCompany(company);
        List<Stop> servedStops = new ArrayList<>();
        for ( Stop stop : allStops ) {
            if ( stopTimeService.countStopTimes(company, stop.getName(), routeNumber) > 0 ) {
                servedStops.add(stop);
            }
        }
        return servedStops;
    }

}
