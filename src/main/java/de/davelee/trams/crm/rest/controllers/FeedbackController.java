package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.request.AnswerRequest;
import de.davelee.trams.crm.request.FeedbackRequest;
import de.davelee.trams.crm.services.CustomerService;
import de.davelee.trams.crm.services.FeedbackService;
import de.davelee.trams.crm.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value="/trams-crm/feedback")
@RequestMapping(value="/trams-crm/feedback")
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
    @ApiOperation(value = "Add a feedback", notes="Add a feedback to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=201,message="Successfully created feedback")})
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
    @ApiOperation(value = "Add an answer", notes="Add an answer to a feedback in the system.")
    @PutMapping(value="/answer")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully added answer")})
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
