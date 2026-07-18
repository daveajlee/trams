package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to reset services at the start of
 * a new day.
 * @author Dave Lee
 */
public class ResetServiceRequest {

    /**
     * Company which services should be reset for
     */
    private String company;

    public ResetServiceRequest() {
    }

    public ResetServiceRequest(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
