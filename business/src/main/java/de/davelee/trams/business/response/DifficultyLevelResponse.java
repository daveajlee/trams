package de.davelee.trams.business.response;

import lombok.*;

/**
 * This class is part of the TraMS Business REST API. It represents a response containing the company
 * and the current difficulty level.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DifficultyLevelResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The difficulty level of the company (can be EASY, MEDIUM or HARD).
     */
    private String difficultyLevel;

}