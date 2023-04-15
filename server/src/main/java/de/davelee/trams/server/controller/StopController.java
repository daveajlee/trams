package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Stop;
import de.davelee.trams.server.request.AddStopRequest;
import de.davelee.trams.server.service.StopService;
import de.davelee.trams.server.service.SuggestStopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * This class provides REST endpoints which provide operations associated with a single stop in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/stop")
@RequestMapping(value="/api/stop")
public class StopController {

    @Autowired
    private StopService stopService;

    @Autowired
    private SuggestStopService suggestStopService;

    /**
     * Add a stop to the database for a particular company.
     * @param stopRequest a <code>StopRequest</code> object containing the information about this stop.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @PostMapping("/")
    @CrossOrigin
    @Operation(summary = "Add stop", description="Add a stop for this company")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully add stop")})
    public ResponseEntity<Void> addStop ( @RequestBody final AddStopRequest stopRequest ) {
        //Check that the request is valid, otherwise bad request.
        if (StringUtils.isBlank(stopRequest.getCompany()) || StringUtils.isBlank(stopRequest.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Convert the StopRequest to the Stop object.
        Stop stop = Stop.builder()
                .company(stopRequest.getCompany())
                .name(stopRequest.getName())
                .waitingTime(stopRequest.getWaitingTime())
                .distances(stopRequest.getDistances())
                .latitude(stopRequest.getLatitude())
                .longitude(stopRequest.getLongitude())
                .build();
        //Attempt to add the stop to the database.
        return stopService.addStop(stop) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

    /**
     * Return a suggestion for the nearest stop for a particular address.
     * @param operator a <code>String</code> containing the operator to return the suggestion for.
     * @param address a <code>String</code> based on which the nearest stop should be found.
     * @return a <code>String</code> containing the name of the nearest stop.
     */
    @GetMapping("/suggest")
    @CrossOrigin
    @Operation(summary = "Suggest Stop", description="Suggest a nearby stop for a particular address")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully suggested stop")})
    public @ResponseBody String suggestStop ( final String operator, final String address) {
        return suggestStopService.suggestNearestStop(operator, address);
    }

    /**
     * Return a set of information including picture, address, attractions and name.
     * @param operator a <code>String</code> containing the operator to return the stop information for.
     * @param stopName a <code>String</code> containing the name of the stop to retrieve information for.
     * @return a <code>StopModel</code> object containing the stop information.
     */
    @GetMapping("/information")
    @CrossOrigin
    @Operation(summary = "Get the stop information for a particular stop", description="Return stop information")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully retrieved stop information")})
    public @ResponseBody Stop getStop ( final String operator, final String stopName) {
        return stopService.getStop(operator, stopName);
    }

}
