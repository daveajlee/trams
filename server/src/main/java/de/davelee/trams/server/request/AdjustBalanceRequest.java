package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to adjust the balance
 * by either crediting or withdrawing money for a particular company.
 * @author Dave Lee
 */
public class AdjustBalanceRequest {

    /**
     * The name of the company to adjust the balance.
     */
    private String company;

    /**
     * The value to either subtract (minus) or credit (plus) to the balance.
     */
    private double value;

    public AdjustBalanceRequest() {
    }

    public AdjustBalanceRequest(String company, double value) {
        this.company = company;
        this.value = value;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AdjustBalanceRequest{" +
                "company='" + company + '\'' +
                ", value=" + value +
                '}';
    }
}
