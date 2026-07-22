package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to adjust the simulation interval
 * by either increasing or decreasing the simulation interval for a particular company.
 * @author Dave Lee
 */
public class AdjustSimulationIntervalRequest {

    /**
     * The name of the company to adjust the satisfaction rate.
     */
    private String company;

    /**
     * The new value of the simulation interval in minutes.
     */
    private int simulationInterval;

    public AdjustSimulationIntervalRequest() {
    }

    public AdjustSimulationIntervalRequest(String company, int simulationInterval) {
        this.company = company;
        this.simulationInterval = simulationInterval;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getSimulationInterval() {
        return simulationInterval;
    }

    public void setSimulationInterval(int simulationInterval) {
        this.simulationInterval = simulationInterval;
    }

    @Override
    public String toString() {
        return "AdjustSimulationIntervalRequest{" +
                "company='" + company + '\'' +
                ", simulationInterval=" + simulationInterval +
                '}';
    }
}
