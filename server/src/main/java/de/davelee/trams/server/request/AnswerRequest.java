package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add an answer to a specific feedback
 * which already exists on the server.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerRequest {

    /**
     * Id of the feedback which the answer should be added to.
     */
    private String objectId;

    /**
     * Answer which should be added to the feedback.
     */
    private String answer;

    /**
     * Token to use to authenticate the user adding the answer.
     */
    private String token;

}
