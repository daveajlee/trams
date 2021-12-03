package de.davelee.trams.business.response;

import lombok.*;

/**
 * This class is part of the TraMS Business REST API. It represents a response containing the company
 * and its current time in the format dd-MM-yyyy HH:mm.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimeResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The time of the company in the format dd-MM-yyyy HH:mm.
     */
    private String time;

}
