package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Stop;
import de.davelee.trams.server.request.AddStopRequest;
import de.davelee.trams.server.service.StopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
