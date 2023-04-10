package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Feedback;
import de.davelee.trams.server.repository.FeedbackRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class to provide service operations for feedbacks in TraMS Server.
 * @author Dave Lee
 */
@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    /**
     * Save the specified feedback object in the database.
     * @param feedback a <code>Feedback</code> object to save in the database.
     * @return a <code>boolean</code> which is true iff the feedback has been saved successfully.
     */
    public boolean save(final Feedback feedback) {
        return feedbackRepository.save(feedback) != null;
    }

    /**
     * Find feedbacks according to their email address and company.
     * @param company a <code>String</code> with the company to retrieve customer for.
     * @param emailAddress a <code>String</code> with the email address of the customer that the feedback belongs to.
     * @return a <code>List</code> of <code>Feedback</code> representing the feedbacks matching the criteria. Returns null if no matching feedback.
     */
    public List<Feedback> findByCompanyAndCustomer (final String company, final String emailAddress ) {
        return feedbackRepository.findByCompanyAndEmailAddress(company, emailAddress);
    }

    /**
     * Find feedbacks according to their company.
     * @param company a <code>String</code> with the company to retrieve customer for.
     * @return a <code>List</code> of <code>Feedback</code> representing the feedbacks matching the criteria. Returns null if no matching feedback.
     */
    public List<Feedback> findByCompany (final String company) {
        return feedbackRepository.findByCompany(company);
    }

    /**
     * Add an answer to a feedback based on it's object id.
     * @param answer a <code>String</code> with the answer to add the feedback.
     * @param objectId a <code>String</code> with the id of the feedback to attach the answer to.
     * @return a <code>boolean</code> which is true iff the feedback could be found and the answer could be added to the feedback successfully.
     */
    public boolean addAnswerToFeedback ( final String answer, final String objectId ) {
        Feedback feedback = feedbackRepository.findById(new ObjectId(objectId));
        if ( feedback != null ) {
            feedback.setAnswer(answer);
            feedbackRepository.save(feedback);
            return true;
        }
        return false;
    }

}

