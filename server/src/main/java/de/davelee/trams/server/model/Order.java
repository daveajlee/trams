package de.davelee.trams.server.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class to represent orders made by customers for tickets for particular companies in TraMS Server.
 * @author Dave Lee
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Order {

    /**
     * A unique id for this order.
     */
    @Id
    private ObjectId id;

    /**
     * The type of the ticket e.g. single that was bought
     */
    private String ticketType;

    /**
     * The target group of the ticket e.g. adult that was bought
     */
    private String ticketTargetGroup;

    /**
     * The number of tickets that the person bought
     */
    private int quantity;

    /**
     * The type of payment that the person used (the actual data is not stored)
     */
    private String paymentType;

    /**
     * The confirmation id from the payment type provider
     */
    private String confirmationId;

    /**
     * The text with the qr code that the user was provided with
     */
    private String qrCodeText;


}
