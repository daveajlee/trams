package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to employ a driver
 * and contains the costs of employing the driver.
 * @author Dave Lee
 */
public class EmployDriverResponse {

    /**
     * Could the driver be employed successfully?
     */
    private boolean employed;

    /**
     * The cost of employing the driver which may be 0 if the driver could not be employed successfully.
     */
    private double employmentCost;

    public EmployDriverResponse() {
    }

    public EmployDriverResponse(boolean employed, double employmentCost) {
        this.employed = employed;
        this.employmentCost = employmentCost;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    public double getEmploymentCost() {
        return employmentCost;
    }

    public void setEmploymentCost(double employmentCost) {
        this.employmentCost = employmentCost;
    }
}

