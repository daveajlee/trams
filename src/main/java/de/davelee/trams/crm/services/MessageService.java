package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Message;
import de.davelee.trams.crm.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class to provide service operations for messages in TraMS CRM.
 * @author Dave Lee
 */
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Save the specified message object in the database.
     * @param message a <code>Message</code> object to save in the database.
     * @return a <code>boolean</code> which is true iff the message has been saved successfully.
     */
    public boolean save(final Message message) {
        return messageRepository.save(message) != null;
    }

    /**
     * Return all messages for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>List</code> of <code>Message</code> objects which may be null if there are no messages in the database.
     */
    public List<Message> getMessagesByCompany (final String company ) {
        return messageRepository.findByCompany(company);
    }

    /**
     * Delete a single message from the database.
     * @param message a <code>Message</code> object which should be deleted from the database.
     */
    public void deleteMessage ( final Message message ) {
        messageRepository.delete(message);
    }

}
