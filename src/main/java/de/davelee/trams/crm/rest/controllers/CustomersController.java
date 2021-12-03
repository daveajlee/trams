package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.response.CustomerResponse;
import de.davelee.trams.crm.response.CustomersResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class defines the endpoints for the REST API which manipulate customers and delegates the actions to the CustomerService class.
 * @author Dave Lee
 */
@RestController
@Api(value="/api/customers")
@RequestMapping(value="/api/customers")
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    /**
     * Find all customers for a specific company.
     * @param company a <code>String</code> containing the name of the company.
     * @return a <code>ResponseEntity</code> containing the customers for this company.
     */
    @ApiOperation(value = "Find all customers for a company", notes = "Find all customers for a company to the system.")
    @GetMapping(value = "/")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully found customer(s)"), @ApiResponse(code = 204, message = "Successful but no customers found")})
    public ResponseEntity<CustomersResponse> getCustomers(@RequestParam("company") final String company) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Now retrieve the customers based on the company.
        List<Customer> customers = customerService.findByCompany(company);
        //If customers is empty then return 204.
        if (customers.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Convert to CustomerResponse object and return 200.
        CustomerResponse[] customerResponses = new CustomerResponse[customers.size()];
        for (int i = 0; i < customers.size(); i++) {
            customerResponses[i] = CustomerUtils.convertCustomerToCustomerResponse(customers.get(i));
        }
        return ResponseEntity.ok(CustomersResponse.builder()
                .count((long) customerResponses.length)
                .customerResponses(customerResponses)
                .build());
    }
}
