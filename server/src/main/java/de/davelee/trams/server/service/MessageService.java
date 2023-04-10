package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Message;
import de.davelee.trams.server.repository.MessageRepository;
import de.davelee.trams.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class to provide service operations for messages in TraMS Server.
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
     * @param folder a <code>String</code> containing the folder to search for (optional).
     * @param sender a <code>String</code> containing the sender to search for (optional).
     * @param date a <code>String</code> containing the date to search for (optional).
     * @return a <code>List</code> of <code>Message</code> objects which may be null if there are no messages in the database.
     */
    public List<Message> getMessagesByCompany (final String company, final Optional<String> folder, final Optional<String> sender, final Optional<String> date ) {
        if (folder.isPresent() && sender.isPresent() && date.isPresent() ) {
            return messageRepository.findByCompanyAndFolderAndSenderAndDateTime(company, folder.get(), sender.get(), DateUtils.convertDateToLocalDateTime(date.get()));
        } else if (folder.isPresent() && sender.isPresent()) {
            return messageRepository.findByCompanyAndFolderAndSender(company, folder.get(), sender.get());
        } else if (folder.isPresent() && date.isPresent()) {
            return messageRepository.findByCompanyAndFolderAndDateTime(company, folder.get(), DateUtils.convertDateToLocalDateTime(date.get()));
        } else if ( folder.isPresent() ) {
            return messageRepository.findByCompanyAndFolder(company, folder.get());
        } else if ( sender.isPresent() && date.isPresent() ) {
            return messageRepository.findByCompanyAndSenderAndDateTime(company, sender.get(), DateUtils.convertDateToLocalDateTime(date.get()));
        } else if ( sender.isPresent() ) {
            return messageRepository.findByCompanyAndSender(company, sender.get());
        } else if ( date.isPresent() ) {
            return messageRepository.findByCompanyAndDateTime(company, DateUtils.convertDateToLocalDateTime(date.get()));
        } else {
            return messageRepository.findByCompany(company);
        }
    }

    /**
     * Delete a single message from the database.
     * @param message a <code>Message</code> object which should be deleted from the database.
     */
    public void deleteMessage ( final Message message ) {
        messageRepository.delete(message);
    }

}
