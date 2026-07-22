package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched timetables according to specified criteria. As well as containing details about the timetables in form of
 * an array of <code>TimetableResponse</code> objects, the object also contains a simple count of the timetables.
 * @author Dave Lee
 */
public class TimetablesResponse {

    //a count of the number of timetables which were found by the server.
    private Long count;

    //an array of all timetables found by the server.
    private TimetableResponse[] timetableResponses;

    public TimetablesResponse() {
    }

    public TimetablesResponse(Long count, TimetableResponse[] timetableResponses) {
        this.count = count;
        this.timetableResponses = timetableResponses;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public TimetableResponse[] getTimetableResponses() {
        return timetableResponses;
    }

    public void setTimetableResponses(TimetableResponse[] timetableResponses) {
        this.timetableResponses = timetableResponses;
    }
}
