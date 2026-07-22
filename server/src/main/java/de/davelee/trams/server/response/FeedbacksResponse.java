package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched feedbacks according to specified criteria. As well as containing details about the feedbacks in form of
 * an array of <code>FeedbackResponse</code> objects, the object also contains a simple count of the feedbacks.
 * @author Dave Lee
 */
public class FeedbacksResponse {

    //a count of the number of feedbacks which were found by the server.
    private Long count;

    //an array of all feedbacks found by the server.
    private FeedbackResponse[] feedbackResponses;

    public FeedbacksResponse() {
    }

    public FeedbacksResponse(Long count, FeedbackResponse[] feedbackResponses) {
        this.count = count;
        this.feedbackResponses = feedbackResponses;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public FeedbackResponse[] getFeedbackResponses() {
        return feedbackResponses;
    }

    public void setFeedbackResponses(FeedbackResponse[] feedbackResponses) {
        this.feedbackResponses = feedbackResponses;
    }
}
