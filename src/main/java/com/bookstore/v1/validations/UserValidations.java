package com.bookstore.v1.validations;

import com.bookstore.v1.dto.UserDTO;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;

public class UserValidations {
    public static void validateUserDTO(UserDTO userDTO, Boolean validateId) throws EmptyFieldException {
        if (validateId) {
            if (userDTO.getId() == null) {
                throw new EmptyFieldException("id");
            }
        }
        if (userDTO.getUserName() == null || userDTO.getUserName().isEmpty()) {
            throw new EmptyFieldException("userName");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new EmptyFieldException("email");
        }
        if (userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().isEmpty()) {
            throw new EmptyFieldException("phoneNumber");
        }
    }
}
