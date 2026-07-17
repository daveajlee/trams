package de.davelee.trams.server.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class represents a company. A company can contain a balance, a customer satisfaction rate, a player name and a
 * time.
 * @author Dave Lee
 */
@Document
public class Company {

    /**
     * A unique id for this company.
     */
    @Id
    private ObjectId id;

    /**
     * The name of this company.
     */
    private String name;

    /**
     * The balance of this company.
     */
    private BigDecimal balance;

    /**
     * The player name for the company.
     */
    private String playerName;

    /**
     * The satisfaction rate for this company.
     */
    private BigDecimal satisfactionRate;

    /**
     * The current simulated time for this company.
     */
    private LocalDateTime time;

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

    public Company() {
    }

    public Company(ObjectId id, String name, BigDecimal balance, String playerName, BigDecimal satisfactionRate, LocalDateTime time, String scenarioName, String difficultyLevel, int simulationInterval) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.playerName = playerName;
        this.satisfactionRate = satisfactionRate;
        this.time = time;
        this.scenarioName = scenarioName;
        this.difficultyLevel = difficultyLevel;
        this.simulationInterval = simulationInterval;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public BigDecimal getSatisfactionRate() {
        return satisfactionRate;
    }

    public void setSatisfactionRate(BigDecimal satisfactionRate) {
        this.satisfactionRate = satisfactionRate;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
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

    public int getSimulationInterval() {
        return simulationInterval;
    }

    public void setSimulationInterval(int simulationInterval) {
        this.simulationInterval = simulationInterval;
    }
}
