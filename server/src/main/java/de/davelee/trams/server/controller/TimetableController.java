package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Timetable;
import de.davelee.trams.server.request.CreateTimetableRequest;
import de.davelee.trams.server.response.CreateTimetableResponse;
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
import java.util.Optional;

/**
 * This class provides REST endpoints which provide operations associated with a single timetable in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/timetable")
@RequestMapping(value="/api/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    /**
     * Create a timetable based on the supplied timetable request.
     * @param createTimetableRequest a <code>CreateTimetableRequest</code> object containing the information about the timetable which should be created.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Create a timetable", description = "Create a timetable")
    @PostMapping(value = "/")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully created timetable"), @ApiResponse(responseCode = "409", description = "Timetable conflicted with a timetable that already exists")})
    public ResponseEntity<Void> createTimetable(@RequestBody CreateTimetableRequest createTimetableRequest) {
        //Check that the request is valid.
        if (createTimetableRequest.getCompany().isBlank() ) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this timetable does not already exist.
        List<Timetable> timetables = timetableService.retrieveTimetablesByCompanyAndRouteNumberAndName(createTimetableRequest.getCompany(), createTimetableRequest.getRouteNumber(), createTimetableRequest.getName());
        if (timetables != null && !timetables.isEmpty()) {
            return ResponseEntity.of(Optional.of(new CreateTimetableResponse())).status(409).build();
        }
        //Construct the timetable and add it to the database.
        Timetable timetable = new Timetable();
        timetable.setCompany(createTimetableRequest.getCompany());
        timetable.setName(createTimetableRequest.getName());
        timetable.setFrequencyPatterns(FrequencyPatternUtils.convertFrequencyPatternRequestsToFrequencyPatterns(createTimetableRequest.getFrequencyPatterns()));
        timetable.setRouteNumber(createTimetableRequest.getRouteNumber());
        timetable.setValidFromDate(DateUtils.convertDateToLocalDateTime(createTimetableRequest.getValidFromDate()));
        timetable.setValidToDate(DateUtils.convertDateToLocalDateTime(createTimetableRequest.getValidToDate()));
        System.out.println(timetable);
        if (timetableService.addTimetable(timetable)) {
            //Return ok if it was added successfully.
            return ResponseEntity.ok().build();
        }
        //Otherwise return an empty 500 response.
        return ResponseEntity.status(500).build();
    }

    /**
     * Delete a timetable matching the name and route number stored in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @param routeNumber a <code>String</code> containing the route number to search for.
     * @param name a <code>String</code> containing the name of the timetable to search for.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete a timetable", description="Delete a timetable")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted timetable")})
    public ResponseEntity<Void> deleteTimetable (final String company, final String routeNumber, final Optional<String> name ) {
        //First of all, check if the company field and/or name and/or route number fields are empty or null, then return bad request.
        if (company.isBlank() || routeNumber.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete timetable matching the specified criteria.
        timetableService.deleteTimetable(company, name, routeNumber);
        //Return ok.
        return ResponseEntity.ok().build();
    }

}
