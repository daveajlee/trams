package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to purchase a ticket.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PurchaseTicketRequest {

    /**
     * Company which the ticket is valid for
     */
    private String company;

    /**
     * The type of the ticket e.g. single that the person wants to buy
     */
    private String ticketType;

    /**
     * The target group of the ticket e.g. adult that the person wants to buy
     */
    private String ticketTargetGroup;

    /**
     * The number of tickets that the person wants to buy
     */
    private int quantity;

    /**
     * The price that the user is prepared to pay
     */
    private double price;

    /**
     * The type of the credit card e.g. Visa that the user wishes to use for payment
     */
    private String creditCardType;

    /**
     * The number of the credit card that the user wishes to use for payment
     */
    private String creditCardNumber;

    /**
     * The expiry month and year of the credit card that the user wishes to use for payment in the format mm/YYYY
     */
    private String creditCardExpiryDate;

    /**
     * The security code of the credit card that the user wishes to use for payment
     */
    private String creditCardSecurityCode;

}
