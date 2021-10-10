package de.davelee.trams.crm.repository;

import de.davelee.trams.crm.model.Feedback;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface class for database operations on feedback - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface FeedbackRepository extends MongoRepository<Feedback, Long> {

    /**
     * Find all feedbacks for a particular company and customer.
     * @Param company a <code>String</code> with the name of the company that the feedback belongs to.
     * @param emailAddress a <code>String</code> with the email address of the customer that the feedback belongs to.
     * @return a <code>Feedback</code> representing the feedbacks matching this company and customer. Returns null if no matching feedbacks.
     */
    List<Feedback> findByCompanyAndEmailAddress (@Param("company") final String company, @Param("emailAddress") final String emailAddress);

    /**
     * Find all feedbacks belonging to a company.
     * @param company a <code>String</code> with the company to retrieve feedbacks for.
     * @return a <code>List</code> of <code>Feedback</code> objects representing the feedbacks belonging to this company. Returns null if no matching feedbacks.
     */
    List<Feedback> findByCompany (@Param("company") final String company );

    /**
     * Find a feedback based on its object id.
     * @param objectId a <code>ObjectId</code> con
     */
    Feedback findById ( @Param("id") final ObjectId objectId);

}
