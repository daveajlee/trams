package de.davelee.trams.server.controller;

import de.davelee.trams.server.request.JourneyRequest;
import de.davelee.trams.server.response.JourneyResponse;
import de.davelee.trams.server.service.CalculateJourneyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST endpoints which provide operations associated with journey planning in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/journeyPlanner")
@RequestMapping(value="/api/journeyPlanner")
public class JourneyPlannerController {

    @Autowired
    private CalculateJourneyService calculateJourneyService;

    /**
     * Calculate the journey based on the supplied journey request.
     * @param journeyRequest a <code>JourneyRequest</code> object containing the journey that should be calculated.
     * @return a <code>JourneyResponse</code> object containing either the journey instructions or an error message if
     * no journey could be calculated.
     */
    @PostMapping("/determineJourney")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "Suggest a journey based on the request journey parameters", description="Return proposed journey")
    public JourneyResponse determineJourney (@RequestBody final JourneyRequest journeyRequest) {
        //Create a journeyResponse object.
        JourneyResponse journeyResponse = new JourneyResponse();
        //If the start equals the destination then return an error message.
        if ( journeyRequest.getFrom().contentEquals(journeyRequest.getTo()) ) {
            journeyResponse.setErrorMessage("Cannot suggest route as start and end points are identical!");
        } else {
            //Otherwise, calculate journey and generate a list of instructions.
            journeyResponse.setJourneyInstructionList(calculateJourneyService.calculateJourney(journeyRequest));
            //If the instructions is empty then produce an error message.
            if ( journeyResponse.getJourneyInstructionList().size() == 0 ) {
                journeyResponse.setErrorMessage("No journeys found!");
            }
        }
        //Generate journey response.
        return journeyResponse;
    }

}
