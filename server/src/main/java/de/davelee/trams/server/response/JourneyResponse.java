package de.davelee.trams.server.response;

import de.davelee.trams.server.model.JourneyInstruction;

import java.util.List;

/**
 * Represent a response to calculate a journey with the trams timetabler api with a list of instructions.
 * @author Dave Lee
 */
public class JourneyResponse {

    private String errorMessage;

    private List<JourneyInstruction> journeyInstructionList;

    /**
     * Return the error message produced when calculating the journey. This can be null if the journey calculation
     * is successful.
     * @return a <code>String</code> containing the error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the error message produced when calculating the journey.
     * @param errorMessage a <code>String</code> containing the error message.
     */
    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Return the list of instructions for this journey to follow.
     * @return a <code>List</code> of <code>JourneyInstruction</code> with the instructions for this journey.
     */
    public List<JourneyInstruction> getJourneyInstructionList() {
        return journeyInstructionList;
    }

    /**
     * Set the list of instructions for this journey to follow.
     * @param journeyInstructionList a <code>List</code> of <code>JourneyInstruction</code> with the instructions for this journey.
     */
    public void setJourneyInstructionList(final List<JourneyInstruction> journeyInstructionList) {
        this.journeyInstructionList = journeyInstructionList;
    }

}
