package de.davelee.trams.server.model;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

}
