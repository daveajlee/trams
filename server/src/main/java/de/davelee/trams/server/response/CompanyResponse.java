package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing all company information.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

}
