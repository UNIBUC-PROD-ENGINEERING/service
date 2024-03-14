package ro.unibuc.contact.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ro.unibuc.contact.data.UserEntity;
import ro.unibuc.contact.dto.CreateMessageDTO;
import ro.unibuc.contact.dto.UserAuthDTO;
import ro.unibuc.contact.exception.EntityNotFoundException;
import ro.unibuc.contact.service.MessageService;
import ro.unibuc.contact.data.MessageEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.contact.service.UserService;


@Controller
public class MessageController{

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    private Optional<String> checkAuth(UserAuthDTO userAuth){
        Optional<UserEntity> user = userService.findByUsername(userAuth.getUsername());
        if (!user.isPresent()) {
            return Optional.empty();
        }
        if(!user.get().password.equals(userAuth.getPassword())){
            return Optional.empty();
        }
        return Optional.of(user.get().id);
    }

    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<?> createMessage(@RequestBody CreateMessageDTO messageRequest) {
        Optional<UserEntity> user = userService.findByUsername(messageRequest.getUsername());
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }
       
        MessageEntity newMessage = new MessageEntity(messageRequest.getSubject(), messageRequest.getBody(), user.get().id);

        MessageEntity message;
        try {
            message = messageService.createMessage(newMessage);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/messages")    
    @ResponseBody
    public ResponseEntity<?> getMessagesForUser(@RequestBody UserAuthDTO userAuth){
        Optional<String> userId = checkAuth(userAuth);
        if (!userId.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(messageService.getMessagesForUser(userId.get()));
    }

    @GetMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<?> getMessage(@RequestBody UserAuthDTO userAuth, @PathVariable String messageId){
        Optional<String> userId = checkAuth(userAuth);
        if (!userId.isPresent()) {
            return ResponseEntity.badRequest().body("Unaothorized");
        }
        return ResponseEntity.ok(messageService.findById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@RequestBody UserAuthDTO userAuth, @PathVariable String messageId) {
        Optional<String> userId = checkAuth(userAuth);
        if (!userId.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
         try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
     }

}

