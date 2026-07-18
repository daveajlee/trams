package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing the company
 * and its current satisfaction rate.
 * @author Dave Lee
 */
public class SatisfactionRateResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The satisfaction rate of the company.
     */
    private double satisfactionRate;

    public SatisfactionRateResponse() {
    }

    public SatisfactionRateResponse(String company, double satisfactionRate) {
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
        return "SatisfactionRateResponse{" +
                "company='" + company + '\'' +
                ", satisfactionRate=" + satisfactionRate +
                '}';
    }
}
