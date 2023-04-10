package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched customers according to specified criteria. As well as containing details about the customers in form of
 * an array of <code>CustomerResponse</code> objects, the object also contains a simple count of the customers.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomersResponse {

    //a count of the number of customers which were found by the server.
    private Long count;

    //an array of all customers found by the server.
    private CustomerResponse[] customerResponses;

}

