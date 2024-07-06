package de.davelee.trams.server.controller;

import de.davelee.trams.server.service.TimetableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete all timetables for this company.
        timetableService.deleteTimetables(company);
        //Return ok.
        return ResponseEntity.ok().build();
    }

}
