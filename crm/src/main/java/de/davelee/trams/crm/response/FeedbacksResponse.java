package de.davelee.trams.crm.response;

import lombok.*;

/**
 * This class is part of the TraMS CRM REST API. It represents a response from the server containing details
 * of all matched feedbacks according to specified criteria. As well as containing details about the feedbacks in form of
 * an array of <code>FeedbackResponse</code> objects, the object also contains a simple count of the feedbacks.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbacksResponse {

    //a count of the number of feedbacks which were found by the server.
    private Long count;

    //an array of all feedbacks found by the server.
    private FeedbackResponse[] feedbackResponses;

}
