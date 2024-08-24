package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched companies according to specified criteria. As well as containing details about the companies in form of
 * an array of <code>CompanyResponse</code> objects, the object also contains a simple count of the companies.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompaniesResponse {

    //a count of the number of companies which were found by the server.
    private Long count;

    //an array of all companies found by the server.
    private CompanyResponse[] companyResponseList;

}
