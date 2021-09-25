package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}

