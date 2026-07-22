package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing the export of all company
 * information including routes, drivers, vehicles and messages.
 * @author Dave Lee
 */
public class ExportCompanyResponse {

    /**
     * The name of this company.
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
     * The satisfaction rate for this company.
     */
    private double satisfactionRate;

    /**
     * The current simulated time for this company.
     */
    private String time;

    /**
     * The scenario which this company was generated for (can be empty).
     */
    private String scenarioName;

    /**
     * The difficulty level which this company should be run at (can be EASY, MEDIUM or HARD)
     */
    private String difficultyLevel;

    /**
     * The route information that exist for this company.
     */
    private String routes;

    /**
     * The driver information that exist for this company.
     */
    private String drivers;

    /**
     * The vehicle information that exist for this company.
     */
    private String vehicles;

    /**
     * The message information that exist for this company.
     */
    private String messages;

    public ExportCompanyResponse() {
    }

    public ExportCompanyResponse(String name, double balance, String playerName, double satisfactionRate, String time, String scenarioName, String difficultyLevel, String routes, String drivers, String vehicles, String messages) {
        this.name = name;
        this.balance = balance;
        this.playerName = playerName;
        this.satisfactionRate = satisfactionRate;
        this.time = time;
        this.scenarioName = scenarioName;
        this.difficultyLevel = difficultyLevel;
        this.routes = routes;
        this.drivers = drivers;
        this.vehicles = vehicles;
        this.messages = messages;
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

    public double getSatisfactionRate() {
        return satisfactionRate;
    }

    public void setSatisfactionRate(double satisfactionRate) {
        this.satisfactionRate = satisfactionRate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public String getDrivers() {
        return drivers;
    }

    public void setDrivers(String drivers) {
        this.drivers = drivers;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ExportCompanyResponse{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", playerName='" + playerName + '\'' +
                ", satisfactionRate=" + satisfactionRate +
                ", time='" + time + '\'' +
                ", scenarioName='" + scenarioName + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", routes='" + routes + '\'' +
                ", drivers='" + drivers + '\'' +
                ", vehicles='" + vehicles + '\'' +
                ", messages='" + messages + '\'' +
                '}';
    }
}
