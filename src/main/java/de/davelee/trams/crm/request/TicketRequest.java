package de.davelee.trams.crm.request;

import lombok.*;

import java.util.Map;

/**
 * This class is part of the TraMS CRM REST API. It represents a request to add a ticket to the server
 * containing type, description, sorting order, price list and company.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketRequest {

    /**
     * The short id for this ticket - the shortId is used as the hash code as well.
     */
    private String shortId;

    /**
     * The type of this ticket - the type is also the name of the ticket.
     */
    private String type;

    /**
     * The description of this ticket - the description is a short info about how this ticket is valid.
     */
    private String description;

    /**
     * The sort order of this ticket - the sort order defines the order if this ticket is stored in a map.
     */
    private int sortOrder;

    /**
     * The price list of this ticket as a map consisting of descriptions and prices which are valid for this ticket.
     */
    private Map<String, Double> priceList;

    /**
     * The company that offers this ticket.
     */
    private String company;

    /**
     * The token of the user to verify that they are logged in
     */
    private String token;

}
