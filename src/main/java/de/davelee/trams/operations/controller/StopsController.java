package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.Stop;
import de.davelee.trams.operations.response.StopResponse;
import de.davelee.trams.operations.response.StopsResponse;
import de.davelee.trams.operations.service.StopService;
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
 * This class provides REST endpoints which provide operations associated with stops in the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Api(value="/trams-operations/stops")
@RequestMapping(value="/trams-operations/stops")
public class StopsController {

    @Autowired
    private StopService stopService;

    /**
     * Return all stops currently stored in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>List</code> of <code>Stop</code> objects which may be null if there are no stops in the database.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @ApiOperation(value = "Get stops", notes="Return all stops")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully returned stops")})
    public ResponseEntity<StopsResponse> getStops (final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Retrieve the stops for this company.
        List<Stop> stops = stopService.getStopsByCompany(company);
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
                    .name(stops.get(i).getName())
                    .build();
        }
        return ResponseEntity.ok(StopsResponse.builder()
                .count((long) stopResponses.length)
                .stopResponses(stopResponses).build());
    }

}
