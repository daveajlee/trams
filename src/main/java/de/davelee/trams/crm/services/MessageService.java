package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Message;
import de.davelee.trams.crm.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
