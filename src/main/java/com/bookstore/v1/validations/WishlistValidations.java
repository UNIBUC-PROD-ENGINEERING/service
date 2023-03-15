package com.bookstore.v1.validations;

import com.bookstore.v1.dto.WishlistCreationDTO;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.InvalidDoubleRange;

public class WishlistValidations {
    public static void validateWishlistCreationDTO(WishlistCreationDTO wishlistCreationDTO, Boolean validateId) throws
            EmptyFieldException, InvalidDoubleRange {
        if (validateId) {
            if (wishlistCreationDTO.getId() == null) {
                throw new EmptyFieldException("id");
            }
        }
        if (wishlistCreationDTO.getTitle() == null || wishlistCreationDTO.getTitle().isEmpty()) {
            throw new EmptyFieldException("title");
        }

        // if we validate the main id, we won't need the other relationship ids
        if (!validateId) {
            if (wishlistCreationDTO.getUserId() == null) {
                throw new EmptyFieldException("userId");
            }
        }
    }
}
