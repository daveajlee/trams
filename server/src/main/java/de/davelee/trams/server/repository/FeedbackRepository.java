package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.Feedback;
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
     * @param company a <code>String</code> with the name of the company that the feedback belongs to.
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
     * @param objectId a <code>ObjectId</code> object with the id to search for.
     * @return a <code>Feedback</code> object containing the object matching the supplied id which can be null if no matching feedback was found.
     */
    Feedback findById ( @Param("id") final ObjectId objectId);

}
