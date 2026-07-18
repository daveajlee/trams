package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to purchase a ticket.
 * @author Dave Lee
 */
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

    public PurchaseTicketRequest() {
    }

    public PurchaseTicketRequest(String company, String ticketType, String ticketTargetGroup, int quantity, double price, String creditCardType, String creditCardNumber, String creditCardExpiryDate, String creditCardSecurityCode) {
        this.company = company;
        this.ticketType = ticketType;
        this.ticketTargetGroup = ticketTargetGroup;
        this.quantity = quantity;
        this.price = price;
        this.creditCardType = creditCardType;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiryDate = creditCardExpiryDate;
        this.creditCardSecurityCode = creditCardSecurityCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardExpiryDate() {
        return creditCardExpiryDate;
    }

    public void setCreditCardExpiryDate(String creditCardExpiryDate) {
        this.creditCardExpiryDate = creditCardExpiryDate;
    }

    public String getCreditCardSecurityCode() {
        return creditCardSecurityCode;
    }

    public void setCreditCardSecurityCode(String creditCardSecurityCode) {
        this.creditCardSecurityCode = creditCardSecurityCode;
    }
}
