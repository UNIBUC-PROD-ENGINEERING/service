package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.UserDTO;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.validations.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO addUser(UserDTO userDTO) throws EmptyFieldException{

        UserValidations.validateUserDTO(userDTO, false);

        User user = userDTO.toUser(true);

        userRepository.save(user);

        return new UserDTO(user);
    }

    public UserDTO updateUser(UserDTO userUpdateDTO) throws EmptyFieldException, EntityNotFoundException {

        UserValidations.validateUserDTO(userUpdateDTO, true);

        Optional<User> oldUserOpt = userRepository.findById(userUpdateDTO.getId());
        if (oldUserOpt.isEmpty()) {
            throw new EntityNotFoundException("user");
        }

        User newUser = oldUserOpt.get();
        newUser.setUserName(userUpdateDTO.getUserName());
        newUser.setEmail(userUpdateDTO.getEmail());
        newUser.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        userRepository.save(newUser);

        return new UserDTO(newUser);
    }

    public void deleteUserById(String userId) throws EntityNotFoundException {
        Optional<User> userToDelete = userRepository.findById(userId);
        if (userToDelete.isEmpty()) {
            throw new EntityNotFoundException("user");
        }
        userRepository.delete(userToDelete.get());
    }

    public UserDTO getUserById(String userId) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("user");
        }
        return new UserDTO(user.get());
    }

    public List<UserDTO> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());
    }
}