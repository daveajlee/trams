package de.davelee.trams.business.request;

import lombok.*;

/**
 * This class is part of the TraMS Business REST API. It represents a request to adjust the difficulty level
 * for a particular company.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdjustDifficultyLevelRequest {

    /**
     * The name of the company to adjust the difficulty level for.
     */
    private String company;

    /**
     * The new difficulty level which should be used for this company (can be EASY, MEDIUM or HARD).
     */
    private String difficultyLevel;

}
