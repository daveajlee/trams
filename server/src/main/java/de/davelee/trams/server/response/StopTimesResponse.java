package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched stop times according to specified criteria. As well as containing details about the stop times in form of
 * an array of <code>StopTimeResponse</code> objects, the object also contains a simple count of the stop times.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StopTimesResponse {

    //a count of the number of stop times which were found by the server.
    private Long count;

    //an array of all stop times found by the server.
    private StopTimeResponse[] stopTimeResponses;

}
