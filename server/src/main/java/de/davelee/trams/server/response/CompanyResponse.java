package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing all company information.
 * @author Dave Lee
 */
public class CompanyResponse {

    /**
     * The name of the company to request.
     */
    private String name;

    /**
     * The balance of this company.
     */
    private double balance;

    /**
     * The player name for the company.
     */
    private String playerName;

    /**
     * The current time for this company.
     */
    private String time;

    /**
     * The satisfaction rate for this company.
     */
    private double satisfactionRate;

    /**
     * The scenario which this company was generated for (can be empty).
     */
    private String scenarioName;

    /**
     * The difficulty level which this company should be run at (can be EASY, MEDIUM or HARD)
     */
    private String difficultyLevel;

    /**
     * The interval in minutes which should be used when incrementing the time in simulation mode.
     */
    private int simulationInterval;

    public CompanyResponse() {
    }

    public CompanyResponse(String name, double balance, String playerName, String time, double satisfactionRate, String scenarioName, String difficultyLevel, int simulationInterval) {
        this.name = name;
        this.balance = balance;
        this.playerName = playerName;
        this.time = time;
        this.satisfactionRate = satisfactionRate;
        this.scenarioName = scenarioName;
        this.difficultyLevel = difficultyLevel;
        this.simulationInterval = simulationInterval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getSatisfactionRate() {
        return satisfactionRate;
    }

    public void setSatisfactionRate(double satisfactionRate) {
        this.satisfactionRate = satisfactionRate;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getSimulationInterval() {
        return simulationInterval;
    }

    public void setSimulationInterval(int simulationInterval) {
        this.simulationInterval = simulationInterval;
    }

    @Override
    public String toString() {
        return "CompanyResponse{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", playerName='" + playerName + '\'' +
                ", time='" + time + '\'' +
                ", satisfactionRate=" + satisfactionRate +
                ", scenarioName='" + scenarioName + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", simulationInterval=" + simulationInterval +
                '}';
    }
}
