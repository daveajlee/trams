package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to reset services at the start of
 * a new day.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ResetServiceRequest {

    /**
     * Company which services should be reset for
     */
    private String company;

}
