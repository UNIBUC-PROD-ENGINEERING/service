package ro.unibuc.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ro.unibuc.contact.data.UserEntity;
import ro.unibuc.contact.dto.CreateMessageDTO;
import ro.unibuc.contact.service.MessageService;
import ro.unibuc.contact.data.MessageEntity;
import ro.unibuc.contact.exception.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.contact.service.UserService;

import java.util.Optional;


@Controller
public class MessageController{

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<?> createMessage(@RequestBody CreateMessageDTO messageRequest) {
        UserEntity user;
        try {
            user = userService.findByUsername(messageRequest.getUsername());
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
        MessageEntity newMessage = new MessageEntity(messageRequest.getSubject(), messageRequest.getBody(), user.id);

        MessageEntity message;
        try {
            message = messageService.createMessage(newMessage);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable String messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

