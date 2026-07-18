package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Timetable;
import de.davelee.trams.server.response.TimetableResponse;
import de.davelee.trams.server.response.TimetablesResponse;
import de.davelee.trams.server.service.TimetableService;
import de.davelee.trams.server.utils.DateUtils;
import de.davelee.trams.server.utils.FrequencyPatternUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class provides REST endpoints which provide operations associated with timetables in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/timetables")
@RequestMapping(value="/api/timetables")
public class TimetablesController {

    @Autowired
    private TimetableService timetableService;

    /**
     * Delete all timetables currently stored in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete timetables", description="Delete all timetables")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted timetables")})
    public ResponseEntity<Void> deleteTimetables (final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (company.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete all timetables for this company.
        timetableService.deleteTimetables(company);
        //Return ok.
        return ResponseEntity.ok().build();
    }

    /**
     * Return all timetables currently stored in the database for a particular company and route number
     * @param company a <code>String</code> containing the name of the company to search for.
     * @param routeNumber a <code>String</code> containing the route number to search for.
     * @return a <code>List</code> of <code>Timetable</code> objects which may be null if there are no timetables in the database.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "Get timetables", description="Return all timetables for the company with the specified route number")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned timetables")})
    public ResponseEntity<TimetablesResponse> getTimetables (final String company, final String routeNumber ) {
        //First of all, check if the company and/or route number field is empty or null, then return bad request.
        if (company.isBlank() || routeNumber.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Retrieve the timetables for this company.
        List<Timetable> timetables = timetableService.retrieveTimetablesByCompanyAndRouteNumber(company, routeNumber);
        //If timetables is null or empty then return 204.
        if ( timetables == null || timetables.isEmpty() ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise convert to timetables response and return.
        TimetableResponse[] timetableResponses = new TimetableResponse[timetables.size()];
        for ( int i = 0; i < timetableResponses.length; i++ ) {
            timetableResponses[i] = new TimetableResponse();
            timetableResponses[i].setCompany(timetables.get(i).getCompany());
            timetableResponses[i].setName(timetables.get(i).getName());
            timetableResponses[i].setRouteNumber(timetables.get(i).getRouteNumber());
            timetableResponses[i].setValidFromDate(DateUtils.convertLocalDateTimeToDate(timetables.get(i).getValidFromDate()));
            timetableResponses[i].setValidToDate(DateUtils.convertLocalDateTimeToDate(timetables.get(i).getValidToDate()));
            timetableResponses[i].setFrequencyPatterns(FrequencyPatternUtils.convertFrequencyPatternsToFrequencyPatternResponses(timetables.get(i).getFrequencyPatterns()));
        }
        TimetablesResponse timetablesResponse = new TimetablesResponse();
        timetablesResponse.setCount((long) timetableResponses.length);
        timetablesResponse.setTimetableResponses(timetableResponses);
        return ResponseEntity.ok(timetablesResponse);
    }

}
