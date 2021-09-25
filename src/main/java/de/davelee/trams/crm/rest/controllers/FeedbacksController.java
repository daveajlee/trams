package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.response.FeedbackResponse;
import de.davelee.trams.crm.response.FeedbacksResponse;
import de.davelee.trams.crm.services.CustomerService;
import de.davelee.trams.crm.services.FeedbackService;
import de.davelee.trams.crm.utils.CustomerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class defines the endpoints for the REST API which manipulate feedbacks and delegates the actions to the FeedbackService class.
 * @author Dave Lee
 */
@RestController
@Api(value="/trams-crm/feedbacks")
@RequestMapping(value="/trams-crm/feedbacks")
public class FeedbacksController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FeedbackService feedbackService;

    /**
     * Find all feedbacks for a specific company and customer (via email address).
     * @param company a <code>String</code> containing the name of the company.
     * @param emailAddress a <code>String</code> containing the email address of the customer.
     * @return a <code>ResponseEntity</code> containing the feedbacks for this company and customer.
     */
    @ApiOperation(value = "Find all feedbacks for a company and customer", notes = "Find all feedbacks for a company and customer to the system.")
    @GetMapping(value = "/")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully found feedback(s)"), @ApiResponse(code = 204, message = "Successful but no feedbacks found")})
    public ResponseEntity<FeedbacksResponse> getFeedbacks(@RequestParam("company") final String company, @RequestParam("emailAddress") final String emailAddress) {
        //First of all, check if the company field and/or email address field are empty or null, then return bad request.
        if (StringUtils.isBlank(company) || StringUtils.isBlank(emailAddress)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Now retrieve the feedback data.
        List<Feedback> feedbacks = feedbackService.findByCompanyAndCustomer(company, emailAddress);
        //Convert to FeedbackResponse object and return 200.
        FeedbackResponse[] feedbackResponses = new FeedbackResponse[feedbacks.size()];;
        for (int i = 0; i < feedbacks.size(); i++) {
            feedbackResponses[i] = FeedbackResponse.builder()
                    .customerResponse(CustomerUtils.convertCustomerToCustomerResponse(feedbacks.get(i).getCustomer()))
                    .extraInfos(feedbacks.get(i).getExtraInfos())
                    .message(feedbacks.get(i).getMessage())
                    .build();
        }
        return ResponseEntity.ok(FeedbacksResponse.builder()
                .count((long) feedbackResponses.length)
                .feedbackResponses(feedbackResponses)
                .build());
    }

}
