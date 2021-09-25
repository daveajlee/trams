package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class to provide service operations for feedbacks in TraMS CRM.
 * @author Dave Lee
 */
@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    /**
     * Save the specified feedback object in the database.
     *
     * @param feedback a <code>Feedback</code> object to save in the database.
     * @return a <code>boolean</code> which is true iff the feedback has been saved successfully.
     */
    public boolean save(final Feedback feedback) {
        return feedbackRepository.save(feedback) != null;
    }

}

