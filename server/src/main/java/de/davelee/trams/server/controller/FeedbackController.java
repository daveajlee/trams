package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.model.Feedback;
import de.davelee.trams.server.request.AnswerRequest;
import de.davelee.trams.server.request.FeedbackRequest;
import de.davelee.trams.server.service.CustomerService;
import de.davelee.trams.server.service.FeedbackService;
import de.davelee.trams.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * This class defines the endpoints for the REST API which manipulate feedbacks and delegates the actions to the FeedbackService class.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/feedback")
@RequestMapping(value="/api/feedback")
public class FeedbackController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    /**
     * Add a feedback to the system.
     * @param feedbackRequest a <code>FeedbackRequest</code> object representing the feedback to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @Operation(summary = "Add a feedback", description="Add a feedback to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="201",description="Successfully created feedback")})
    public ResponseEntity<Void> addFeedback (@RequestBody final FeedbackRequest feedbackRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(feedbackRequest.getEmailAddress()) || StringUtils.isBlank(feedbackRequest.getCompany())
                || StringUtils.isBlank(feedbackRequest.getMessage()) || !Pattern.matches(".*@.*[.]?.*$", feedbackRequest.getEmailAddress())) {
            return ResponseEntity.badRequest().build();
        }
        //Retrieve the matching customer data. If not successful, then also bad request.
        Customer customer = customerService.findByCompanyAndEmailAddress(feedbackRequest.getCompany(), feedbackRequest.getEmailAddress());
        if ( customer == null ) {
            return ResponseEntity.badRequest().build();
        }
        //Now create feedback object and save to feedback service. Return 201 if saved successfully.
        return feedbackService.save(Feedback.builder()
                .customer(customer)
                .message(feedbackRequest.getMessage())
                .extraInfos(feedbackRequest.getExtraInfos())
                .emailAddress(customer.getEmailAddress())
                .company(feedbackRequest.getCompany())
                .build()) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

    /**
     * Add an answer to a feedback in the system.
     * @param answerRequest a <code>AnswerRequest</code> object representing the answer to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @Operation(summary = "Add an answer", description="Add an answer to a feedback in the system.")
    @PatchMapping(value="/answer")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully added answer")})
    public ResponseEntity<Void> addAnswer (@RequestBody final AnswerRequest answerRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(answerRequest.getObjectId()) || StringUtils.isBlank(answerRequest.getAnswer()) ||
            StringUtils.isBlank(answerRequest.getToken())) {
            return ResponseEntity.badRequest().build();
        }
        //Check that the user has logged in, otherwise forbidden.
        if ( !userService.checkAuthToken(answerRequest.getToken()) ) {
            return ResponseEntity.status(403).build();
        }
        //Return 200 if successful or 204 if unsuccessful.
        return feedbackService.addAnswerToFeedback(answerRequest.getAnswer(), answerRequest.getObjectId()) ?
            ResponseEntity.ok().build() : ResponseEntity.status(204).build();
    }

}
