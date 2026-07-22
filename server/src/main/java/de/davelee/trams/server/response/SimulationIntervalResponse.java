package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing the company
 * and its current simulation interval in minutes.
 * @author Dave Lee
 */
public class SimulationIntervalResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The simulation interval in minutes.
     */
    private int simulationInterval;

    public SimulationIntervalResponse() {
    }

    public SimulationIntervalResponse(String company, int simulationInterval) {
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
        return "SimulationIntervalResponse{" +
                "company='" + company + '\'' +
                ", simulationInterval=" + simulationInterval +
                '}';
    }
}
