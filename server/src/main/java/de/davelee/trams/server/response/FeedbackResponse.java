package de.davelee.trams.server.response;

import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing
 * a single feedback returned from the server containing customer and feedback information.
 * @author Dave Lee
 */
public class FeedbackResponse {

    /**
     * Unique id for this feedback which can be used to add answers etc.
     */
    private String id;

    /**
     * Information about the customer that submitted the feedback.
     */
    private CustomerResponse customerResponse;

    /**
     * Message that the customer sent.
     */
    private String message;

    /**
     * Map of extra infos as key/value pair where additional information can be stored.
     */
    private Map<String, String> extraInfos;

    public FeedbackResponse() {
    }

    public FeedbackResponse(String id, CustomerResponse customerResponse, String message, Map<String, String> extraInfos) {
        this.id = id;
        this.customerResponse = customerResponse;
        this.message = message;
        this.extraInfos = extraInfos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerResponse getCustomerResponse() {
        return customerResponse;
    }

    public void setCustomerResponse(CustomerResponse customerResponse) {
        this.customerResponse = customerResponse;
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
        return "FeedbackResponse{" +
                "id='" + id + '\'' +
                ", customerResponse=" + customerResponse +
                ", message='" + message + '\'' +
                ", extraInfos=" + extraInfos +
                '}';
    }
}
