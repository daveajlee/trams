package de.davelee.trams.business.response;

import lombok.*;

/**
 * This class is part of the TraMS Business REST API. It represents a response containing all company information.
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

}
