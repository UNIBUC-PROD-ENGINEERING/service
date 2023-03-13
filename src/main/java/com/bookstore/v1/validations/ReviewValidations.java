package com.bookstore.v1.validations;

import com.bookstore.v1.dto.ReviewCreationDTO;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;

public class ReviewValidations {
    public static void validateReviewCreationDTO(ReviewCreationDTO reviewCreationDTO, Boolean validateId) throws
            EmptyFieldException, InvalidDoubleRange {
        if (validateId) {
            if (reviewCreationDTO.getId() == null) {
                throw new EmptyFieldException("id");
            }
        }
        if (reviewCreationDTO.getTitle() == null || reviewCreationDTO.getTitle().isEmpty()) {
            throw new EmptyFieldException("title");
        }
        if (reviewCreationDTO.getDescription() == null || reviewCreationDTO.getDescription().isEmpty()) {
            throw new EmptyFieldException("description");
        }
        if (reviewCreationDTO.getRating() == null) {
            throw new EmptyFieldException("rating");
        }
        if (reviewCreationDTO.getRating() < 0 || reviewCreationDTO.getRating() > 5) {
            throw new InvalidDoubleRange("rating", 0.0, 5.0);
        }

        // if we validate the main id, we won't need the other relationship ids
        if (!validateId) {
            if (reviewCreationDTO.getUserId() == null) {
                throw new EmptyFieldException("userId");
            }
            if (reviewCreationDTO.getBookId() == null) {
                throw new EmptyFieldException("bookId");
            }
        }
    }
}
