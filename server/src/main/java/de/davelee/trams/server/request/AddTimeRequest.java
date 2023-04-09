package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add the number of minutes
 * to the time for a particular company.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddTimeRequest {

    /**
     * The name of the company to add the time to.
     */
    private String company;

    /**
     * The number of minutes to add to the time.
     */
    private int minutes;

}
