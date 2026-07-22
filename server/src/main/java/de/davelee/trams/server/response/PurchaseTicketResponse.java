package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents the response to a purchase ticket request with either a qr code
 * if purchase was successful or an error message if purchase was not successful.
 * @author Dave Lee
 */
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

    public PurchaseTicketResponse() {
    }

    public PurchaseTicketResponse(boolean success, String qrCode, String errorMessage) {
        this.success = success;
        this.qrCode = qrCode;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
