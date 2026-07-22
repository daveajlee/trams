package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing the company
 * and its current balance.
 * @author Dave Lee
 */
public class BalanceResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The balance of the company.
     */
    private double balance;

    public BalanceResponse() {
    }

    public BalanceResponse(String company, double balance) {
        this.company = company;
        this.balance = balance;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceResponse{" +
                "company='" + company + '\'' +
                ", balance=" + balance +
                '}';
    }
}
