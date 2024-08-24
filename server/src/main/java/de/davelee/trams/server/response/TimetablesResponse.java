package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched timetables according to specified criteria. As well as containing details about the timetables in form of
 * an array of <code>TimetableResponse</code> objects, the object also contains a simple count of the timetables.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimetablesResponse {

    //a count of the number of timetables which were found by the server.
    private Long count;

    //an array of all timetables found by the server.
    private TimetableResponse[] timetableResponses;

}
