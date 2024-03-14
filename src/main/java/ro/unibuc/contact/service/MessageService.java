package ro.unibuc.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.contact.data.MessageEntity;
import ro.unibuc.contact.data.MessageRepository;
import ro.unibuc.contact.data.UserRepository;
import ro.unibuc.contact.exception.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public MessageEntity createMessage(MessageEntity message) {
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            log.error("Error creating message: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteMessage(String messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new EntityNotFoundException("Message not found with ID: " + messageId);
        }
        try {
            log.info("Deleting message with id: {}", messageId);
            messageRepository.deleteById(messageId);
        } catch (Exception e) {
            log.error("Error deleting message: {}", e.getMessage(), e);
            throw e;
        }
    }

}

