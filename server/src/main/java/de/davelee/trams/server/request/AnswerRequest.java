package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add an answer to a specific feedback
 * which already exists on the server.
 * @author Dave Lee
 */
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

    public AnswerRequest() {
    }

    public AnswerRequest(String objectId, String answer, String token) {
        this.objectId = objectId;
        this.answer = answer;
        this.token = token;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AnswerRequest{" +
                "objectId='" + objectId + '\'' +
                ", answer='" + answer + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
