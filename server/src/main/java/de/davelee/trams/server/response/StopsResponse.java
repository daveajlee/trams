package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched stops according to specified criteria. As well as containing details about the stops in form of
 * an array of <code>StopResponse</code> objects, the object also contains a simple count of the stops.
 * @author Dave Lee
 */
public class StopsResponse {

    //a count of the number of stops which were found by the server.
    private Long count;

    //an array of all stops found by the server.
    private StopResponse[] stopResponses;

    public StopsResponse() {
    }

    public StopsResponse(Long count, StopResponse[] stopResponses) {
        this.count = count;
        this.stopResponses = stopResponses;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public StopResponse[] getStopResponses() {
        return stopResponses;
    }

    public void setStopResponses(StopResponse[] stopResponses) {
        this.stopResponses = stopResponses;
    }
}
