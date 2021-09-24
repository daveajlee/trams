package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.request.CustomerRequest;
import de.davelee.trams.crm.response.CustomerResponse;
import de.davelee.trams.crm.services.CustomerService;
import de.davelee.trams.crm.utils.CustomerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class defines the endpoints for the REST API which manipulate customers and delegates the actions to the CustomerService class.
 * @author Dave Lee
 */
@RestController
@Api(value="/trams-crm/customer")
@RequestMapping(value="/trams-crm/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Add a customer to the system.
     * @param customerRequest a <code>CustomerRequest</code> object representing the customer to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @ApiOperation(value = "Add a customer", notes="Add a customer to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=201,message="Successfully created customer")})
    public ResponseEntity<Void> addCustomer (@RequestBody final CustomerRequest customerRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(customerRequest.getFirstName()) || StringUtils.isBlank(customerRequest.getLastName())
                || StringUtils.isBlank(customerRequest.getEmailAddress()) || StringUtils.isBlank(customerRequest.getTelephoneNumber())
                || StringUtils.isBlank(customerRequest.getTitle()) || StringUtils.isBlank(customerRequest.getAddress())
                || StringUtils.isBlank(customerRequest.getCompany())) {
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
    @ApiOperation(value = "Find a customer", notes="Find a customer in the system.")
    @GetMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully found customer"), @ApiResponse(code=204,message="Successful but no customer found")})
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
    @ApiOperation(value = "Delete a customer", notes="Delete a customer from the system.")
    @DeleteMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully deleted customer"), @ApiResponse(code=204,message="Successful but no customer found")})
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
