package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.request.FeedbackRequest;
import de.davelee.trams.crm.services.CustomerService;
import de.davelee.trams.crm.services.FeedbackService;
import de.davelee.trams.crm.utils.CustomerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                .build()) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

}
