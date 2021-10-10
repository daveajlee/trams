package de.davelee.trams.crm.response;

import lombok.*;

/**
 * This class is part of the TraMS CRM REST API. It represents the response to a purchase ticket request with either a qr code
 * if purchase was successful or an error message if purchase was not successful.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseTicketResponse {

    /**
     * Was the purchase successful?
     */
    private boolean success;

    /**
     * The qr code that the user can show as proof of ticket if the purchase was successful.
     */
    private String qrCode;

    /**
     * The error message if purchase was not successful.
     */
    private String errorMessage;

}
