package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface class for database operations on message - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface MessageRepository extends MongoRepository<Message, Long> {

    /**
     * Find all messages belonging to a company.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @return a <code>List</code> of <code>Message</code> objects representing the messages belonging to this company. Returns null if no matching messages.
     */
    List<Message> findByCompany (@Param("company") final String company );

    /**
     * Find all messages belonging to a company, folder, sender and dateTime.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @param folder a <code>String</code> containing the folder to search for.
     * @param sender a <code>String</code> containing the sender to search for.
     * @param dateTime a <code>LocalDateTime</code> object containing the date and time to search for (which must match exactly).
     * @return a <code>List</code> of <code>Message</code> objects representing the messages matching the supplied criteria. Returns null if no matching messages.
     */
    List<Message> findByCompanyAndFolderAndSenderAndDateTime (@Param("company") final String company, @Param("folder") final String folder,
                                                          @Param("sender") final String sender, @Param("dateTime") final LocalDateTime dateTime);

    /**
     * Find all messages belonging to a company, folder and sender.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @param folder a <code>String</code> containing the folder to search for.
     * @param sender a <code>String</code> containing the sender to search for.
     * @return a <code>List</code> of <code>Message</code> objects representing the messages matching the supplied criteria. Returns null if no matching messages.
     */
    List<Message> findByCompanyAndFolderAndSender (@Param("company") final String company, @Param("folder") final String folder,
                                                              @Param("sender") final String sender);

    /**
     * Find all messages belonging to a company, folder and dateTime.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @param folder a <code>String</code> containing the folder to search for.
     * @param dateTime a <code>LocalDateTime</code> object containing the date and time to search for (which must match exactly).
     * @return a <code>List</code> of <code>Message</code> objects representing the messages matching the supplied criteria. Returns null if no matching messages.
     */
    List<Message> findByCompanyAndFolderAndDateTime (@Param("company") final String company, @Param("folder") final String folder,
                                                   @Param("dateTime") final LocalDateTime dateTime);

    /**
     * Find all messages belonging to a company and folder.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @param folder a <code>String</code> containing the folder to search for.
     * @return a <code>List</code> of <code>Message</code> objects representing the messages matching the supplied criteria. Returns null if no matching messages.
     */
    List<Message> findByCompanyAndFolder (@Param("company") final String company, @Param("folder") final String folder);

    /**
     * Find all messages belonging to a company, sender and dateTime.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @param sender a <code>String</code> containing the sender to search for.
     * @param dateTime a <code>LocalDateTime</code> object containing the date and time to search for (which must match exactly).
     * @return a <code>List</code> of <code>Message</code> objects representing the messages matching the supplied criteria. Returns null if no matching messages.
     */
    List<Message> findByCompanyAndSenderAndDateTime (@Param("company") final String company, @Param("sender") final String sender,
                                          @Param("dateTime") final LocalDateTime dateTime);

    /**
     * Find all messages belonging to a company and sender.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @param sender a <code>String</code> containing the sender to search for.
     * @return a <code>List</code> of <code>Message</code> objects representing the messages matching the supplied criteria. Returns null if no matching messages.
     */
    List<Message> findByCompanyAndSender (@Param("company") final String company, @Param("sender") final String sender);

    /**
     * Find all messages belonging to a company and dateTime.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @param dateTime a <code>LocalDateTime</code> object containing the date and time to search for (which must match exactly).
     * @return a <code>List</code> of <code>Message</code> objects representing the messages matching the supplied criteria. Returns null if no matching messages.
     */
    List<Message> findByCompanyAndDateTime (@Param("company") final String company, @Param("dateTime") final LocalDateTime dateTime);

}
