package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.request.CustomerRequest;
import de.davelee.trams.server.response.CustomerResponse;
import de.davelee.trams.server.service.CustomerService;
import de.davelee.trams.server.utils.CustomerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * This class defines the endpoints for the REST API which manipulate customers and delegates the actions to the CustomerService class.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/customer")
@RequestMapping(value="/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Add a customer to the system.
     * @param customerRequest a <code>CustomerRequest</code> object representing the customer to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @Operation(summary = "Add a customer", description="Add a customer to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="201",description="Successfully created customer")})
    public ResponseEntity<Void> addCustomer (@RequestBody final CustomerRequest customerRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(customerRequest.getFirstName()) || StringUtils.isBlank(customerRequest.getLastName())
                || StringUtils.isBlank(customerRequest.getEmailAddress()) || StringUtils.isBlank(customerRequest.getTelephoneNumber())
                || StringUtils.isBlank(customerRequest.getTitle()) || StringUtils.isBlank(customerRequest.getAddress())
                || StringUtils.isBlank(customerRequest.getCompany()) || !Pattern.matches(".*@.*[.]?.*$", customerRequest.getEmailAddress()) ) {
            return ResponseEntity.badRequest().build();
        }
        //Now convert to customer object.
        Customer customer = CustomerUtils.convertCustomerRequestToCustomer(customerRequest);
        //Return 201 if saved successfully.
        return customerService.save(customer) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

    /**
     * Find a customer based on their email address and company.
     * @param company a <code>String</code> containing the name of the company.
     * @param emailAddress a <code>String</code> containing the email address.
     * @return a <code>ResponseEntity</code> containing the customer found.
     */
    @Operation(summary = "Find a customer", description="Find a customer in the system.")
    @GetMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully found customer"), @ApiResponse(responseCode="204",description="Successful but no customer found")})
    public ResponseEntity<CustomerResponse> getCustomer (@RequestParam("company") final String company, @RequestParam("emailAddress") final String emailAddress) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(company) || StringUtils.isBlank(emailAddress)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Now retrieve the customer based on company and email.
        Customer customer = customerService.findByCompanyAndEmailAddress(company, emailAddress);
        //If customer is null then return 204.
        if ( customer == null ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Convert to CustomerResponse object and return 200.
        return ResponseEntity.ok(CustomerUtils.convertCustomerToCustomerResponse(customer));
    }

    /**
     * Delete a specific customer from the database based on their email address and company.
     * @param company a <code>String</code> containing the name of the company.
     * @param emailAddress a <code>String</code> containing the email address.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Delete a customer", description="Delete a customer from the system.")
    @DeleteMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted customer"), @ApiResponse(responseCode="204",description="Successful but no customer found")})
    public ResponseEntity<Void> deleteCustomer (@RequestParam("company") final String company, @RequestParam("emailAddress") final String emailAddress) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(company) || StringUtils.isBlank(emailAddress)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Now retrieve the customer based on the email address.
        Customer customer = customerService.findByCompanyAndEmailAddress(company, emailAddress);
        //If customer is null then return 204.
        if ( customer == null ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Now delete the customer based on the customer found.
        customerService.delete(customer);
        //Return 200.
        return ResponseEntity.status(200).build();
    }

}
