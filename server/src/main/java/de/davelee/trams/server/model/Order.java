package de.davelee.trams.server.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class to represent orders made by customers for tickets for particular companies in TraMS Server.
 * @author Dave Lee
 */
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

    public Order() {
    }

    public Order(ObjectId id, String ticketType, String ticketTargetGroup, int quantity, String paymentType, String confirmationId, String qrCodeText) {
        this.id = id;
        this.ticketType = ticketType;
        this.ticketTargetGroup = ticketTargetGroup;
        this.quantity = quantity;
        this.paymentType = paymentType;
        this.confirmationId = confirmationId;
        this.qrCodeText = qrCodeText;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketTargetGroup() {
        return ticketTargetGroup;
    }

    public void setTicketTargetGroup(String ticketTargetGroup) {
        this.ticketTargetGroup = ticketTargetGroup;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    public void setConfirmationId(String confirmationId) {
        this.confirmationId = confirmationId;
    }

    public String getQrCodeText() {
        return qrCodeText;
    }

    public void setQrCodeText(String qrCodeText) {
        this.qrCodeText = qrCodeText;
    }
}
