package ro.unibuc.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.contact.data.UserEntity;
import ro.unibuc.contact.data.UserRepository;
import ro.unibuc.contact.exception.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(UserEntity user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteUser(String userId) {
       if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        try {
            log.info("Deleting user with id: {}", userId);
            userRepository.deleteById(userId);
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage(), e);
            throw e;
        }
    }

}

