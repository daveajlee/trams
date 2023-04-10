package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.response.CustomerResponse;
import de.davelee.trams.server.response.CustomersResponse;
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
@Tag(name="/api/customers")
@RequestMapping(value="/api/customers")
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    /**
     * Find all customers for a specific company.
     * @param company a <code>String</code> containing the name of the company.
     * @return a <code>ResponseEntity</code> containing the customers for this company.
     */
    @Operation(summary = "Find all customers for a company", description = "Find all customers for a company to the system.")
    @GetMapping(value = "/")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully found customer(s)"), @ApiResponse(responseCode = "204", description = "Successful but no customers found")})
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
