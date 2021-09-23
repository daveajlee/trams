package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.request.CustomerRequest;
import de.davelee.trams.crm.services.CustomerService;
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
                || StringUtils.isBlank(customerRequest.getTitle()) || StringUtils.isBlank(customerRequest.getAddress())) {
            return ResponseEntity.badRequest().build();
        }
        //Now convert to customer object.
        Customer customer = CustomerUtils.convertCustomerRequestToCustomer(customerRequest);
        //Return 201 if saved successfully.
        return customerService.save(customer) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

}
