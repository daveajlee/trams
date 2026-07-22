package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to adjust the satisfaction rate
 * by either increasing or decreasing the satisfaction rate for a particular company.
 * @author Dave Lee
 */
public class AdjustSatisfactionRequest {

    /**
     * The name of the company to adjust the satisfaction rate.
     */
    private String company;

    /**
     * The value to either subtract (minus) or add (plus) to the satisfaction rate.
     */
    private double satisfactionRate;

    public AdjustSatisfactionRequest() {
    }

    public AdjustSatisfactionRequest(String company, double satisfactionRate) {
        this.company = company;
        this.satisfactionRate = satisfactionRate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getSatisfactionRate() {
        return satisfactionRate;
    }

    public void setSatisfactionRate(double satisfactionRate) {
        this.satisfactionRate = satisfactionRate;
    }

    @Override
    public String toString() {
        return "AdjustSatisfactionRequest{" +
                "company='" + company + '\'' +
                ", satisfactionRate=" + satisfactionRate +
                '}';
    }
}
