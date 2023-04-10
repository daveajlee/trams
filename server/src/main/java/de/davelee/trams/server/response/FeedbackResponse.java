package de.davelee.trams.server.response;

import lombok.*;

import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing
 * a single feedback returned from the server containing customer and feedback information.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackResponse {

    /**
     * Unique id for this feedback which can be used to add answers etc.
     */
    private String id;

    /**
     * Information about the customer that submitted the feedback.
     */
    private CustomerResponse customerResponse;

    /**
     * Message that the customer sent.
     */
    private String message;

    /**
     * Map of extra infos as key/value pair where additional information can be stored.
     */
    private Map<String, String> extraInfos;

}
