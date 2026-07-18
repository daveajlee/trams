package de.davelee.trams.server.request;

import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add the following feedback to the server
 * containing customer (via email address &amp; company), message and extraInfos (key/value pair).
 * @author Dave Lee
 */
public class FeedbackRequest {

    /**
     * The email address of the customer e.g. max@mustermann.de
     */
    private String emailAddress;

    /**
     * The company that the customer has registered with to give feedback.
     */
    private String company;

    /**
     * Message that the customer sent.
     */
    private String message;

    /**
     * Map of extra infos as key/value pair where additional information can be stored.
     */
    private Map<String, String> extraInfos;

    public FeedbackRequest() {
    }

    public FeedbackRequest(String emailAddress, String company, String message, Map<String, String> extraInfos) {
        this.emailAddress = emailAddress;
        this.company = company;
        this.message = message;
        this.extraInfos = extraInfos;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getExtraInfos() {
        return extraInfos;
    }

    public void setExtraInfos(Map<String, String> extraInfos) {
        this.extraInfos = extraInfos;
    }

    @Override
    public String toString() {
        return "FeedbackRequest{" +
                "emailAddress='" + emailAddress + '\'' +
                ", company='" + company + '\'' +
                ", message='" + message + '\'' +
                ", extraInfos=" + extraInfos +
                '}';
    }
}
