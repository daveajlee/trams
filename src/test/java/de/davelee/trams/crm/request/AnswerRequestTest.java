package de.davelee.trams.crm.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the <class>AnswerRequest</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class AnswerRequestTest {

    /**
     * Test case: build a <code>AnswerRequest</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        AnswerRequest answerRequest = AnswerRequest.builder()
                .objectId("615824bd6d0c882034e85964")
                .answer("Thanks for the answer").build();
        assertEquals("615824bd6d0c882034e85964", answerRequest.getObjectId());
        assertEquals("Thanks for the answer", answerRequest.getAnswer());
        assertEquals("AnswerRequest(objectId=615824bd6d0c882034e85964, answer=Thanks for the answer)", answerRequest.toString());
    }

    /**
     * Test case: construct an empty <code>AnswerRequest</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        AnswerRequest answerRequest = new AnswerRequest();
        answerRequest.setObjectId("615824bd6d0c882034e85964");
        answerRequest.setAnswer("Thanks for the answer");
        assertEquals("615824bd6d0c882034e85964", answerRequest.getObjectId());
        assertEquals("Thanks for the answer", answerRequest.getAnswer());
    }

}
