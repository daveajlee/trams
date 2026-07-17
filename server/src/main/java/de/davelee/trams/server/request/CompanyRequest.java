package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add the following company to the server
 * containing name, playerName and starting balance and time.
 * @author Dave Lee
 */
public class CompanyRequest {

    /**
     * The name of the company to request.
     */
    private String name;

    /**
     * The starting balance of this company.
     */
    private double startingBalance;

    /**
     * The player name for the company.
     */
    private String playerName;

    /**
     * The starting time for this company.
     */
    private String startingTime;

    /**
     * The scenario which this company was generated for (can be empty).
     */
    private String scenarioName;

    /**
     * The difficulty level which this company should be run at (can be EASY, MEDIUM or HARD)
     */
    private String difficultyLevel;

    public CompanyRequest() {
    }

    public CompanyRequest(String name, double startingBalance, String playerName, String startingTime, String scenarioName, String difficultyLevel) {
        this.name = name;
        this.startingBalance = startingBalance;
        this.playerName = playerName;
        this.startingTime = startingTime;
        this.scenarioName = scenarioName;
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
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

    @Override
    public String toString() {
        return "CompanyRequest{" +
                "name='" + name + '\'' +
                ", startingBalance=" + startingBalance +
                ", playerName='" + playerName + '\'' +
                ", startingTime='" + startingTime + '\'' +
                ", scenarioName='" + scenarioName + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                '}';
    }
}
