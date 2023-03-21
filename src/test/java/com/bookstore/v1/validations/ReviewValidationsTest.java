package com.bookstore.v1.validations;

import com.bookstore.v1.dto.ReviewCreationDTO;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import org.junit.jupiter.api.*;

class ReviewValidationsTest {
    final String ID = "reviewId";
    final String TITLE = "reviewTitle";
    final String DESCRIPTION = "reviewDescription";
    final Double RATING = 5.0;
    final String USER_ID = "userId";
    final String BOOK_ID = "bookId";
    ReviewCreationDTO reviewCreationDTO;

    @BeforeEach
    void setUp() {
        reviewCreationDTO = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID, BOOK_ID);
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("Test validateReviewCreationDTO with validateId = false (for new review object)")
    class TestValidateReviewCreationDTOWithValidateIdSetToFalse {
        @Test
        @DisplayName("Test should pass all validations for valid reviewCreationDTO")
        void test_validateReviewCreationDTO_validateIdIsFalse_willPassForValidDTO() {
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            reviewCreationDTO.setId(null);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
        }

        @Test
        @DisplayName("Test should not throw EmptyFieldException for null id")
        void test_validateReviewCreationDTO_validateIdIsFalse_willNotThrowEmptyFieldExceptionForNullId() {
            reviewCreationDTO.setId(null);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null title")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForNullTitle() {
            reviewCreationDTO.setTitle(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: title is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for empty title")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForEmptyTitle() {
            reviewCreationDTO.setTitle("");
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: title is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null description")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForNullDescription() {
            reviewCreationDTO.setDescription(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: description is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for empty description")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForEmptyDescription() {
            reviewCreationDTO.setDescription("");
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: description is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null rating")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForNullRating() {
            reviewCreationDTO.setRating(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: rating is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw InvalidDoubleRange for rating less than 0")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForRatingLessThanZero() {
            reviewCreationDTO.setRating(-0.1);
            InvalidDoubleRange actualException = Assertions.assertThrows(InvalidDoubleRange.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: rating must be between 0.000000 and 5.000000",
                    actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw InvalidDoubleRange for rating greater than 5")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForRatingGreaterThanFive() {
            reviewCreationDTO.setRating(5.1);
            InvalidDoubleRange actualException = Assertions.assertThrows(InvalidDoubleRange.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: rating must be between 0.000000 and 5.000000",
                    actualException.getMessage());
        }

        @Test
        @DisplayName("Test should not throw InvalidDoubleRange for rating equal to 0")
        void test_validateReviewCreationDTO_validateIdIsFalse_willNotThrowInvalidDoubleRangeForRatingEqualToZero() {
            reviewCreationDTO.setRating(0.0);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
        }

        @Test
        @DisplayName("Test should not throw InvalidDoubleRange for rating equal to 5")
        void test_validateReviewCreationDTO_validateIdIsFalse_willNotThrowInvalidDoubleRangeForRatingEqualToFive() {
            reviewCreationDTO.setRating(5.0);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
        }

        @Test
        @DisplayName("Test should not throw InvalidDoubleRange for rating between 0 and 5")
        void test_validateReviewCreationDTO_validateIdIsFalse_willNotThrowInvalidDoubleRangeForRatingBetweenZeroAndFive() {
            reviewCreationDTO.setRating(2.5);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null userId")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForNullUserId() {
            reviewCreationDTO.setUserId(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: userId is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null bookId")
        void test_validateReviewCreationDTO_validateIdIsFalse_willThrowEmptyFieldExceptionForNullBookId() {
            reviewCreationDTO.setBookId(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false));
            Assertions.assertEquals("Field: bookId is empty", actualException.getMessage());
        }
    }

    @Nested
    @DisplayName("Test validateReviewCreationDTO with validateId = true (for existing review object)")
    class TestValidateReviewCreationDTOWithValidateIdSetToTrue {
        @Test
        @DisplayName("Test should pass all validations for valid reviewCreationDTO")
        void test_validateReviewCreationDTO_validateIdIsTrue_willPassForValidDTO() {
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            reviewCreationDTO.setUserId(null);
            reviewCreationDTO.setBookId(null);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null id")
        void test_validateReviewCreationDTO_validateIdIsTrue_willThrowEmptyFieldExceptionForNullId() {
            reviewCreationDTO.setId(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            Assertions.assertEquals("Field: id is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null title")
        void test_validateReviewCreationDTO_validateIdIsTrue_willThrowEmptyFieldExceptionForNullTitle() {
            reviewCreationDTO.setTitle(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            Assertions.assertEquals("Field: title is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for empty title")
        void test_validateReviewCreationDTO_validateIdIsTrue_willThrowEmptyFieldExceptionForEmptyTitle() {
            reviewCreationDTO.setTitle("");
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            Assertions.assertEquals("Field: title is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null description")
        void test_validateReviewCreationDTO_validateIdIsTrue_willThrowEmptyFieldExceptionForNullDescription() {
            reviewCreationDTO.setDescription(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            Assertions.assertEquals("Field: description is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for empty description")
        void test_validateReviewCreationDTO_validateIdIsTrue_willThrowEmptyFieldExceptionForEmptyDescription() {
            reviewCreationDTO.setDescription("");
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            Assertions.assertEquals("Field: description is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw EmptyFieldException for null rating")
        void test_validateReviewCreationDTO_validateIdIsTrue_willThrowEmptyFieldExceptionForNullRating() {
            reviewCreationDTO.setRating(null);
            EmptyFieldException actualException = Assertions.assertThrows(EmptyFieldException.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            Assertions.assertEquals("Field: rating is empty", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw InvalidDoubleRange for rating less than 0")
        void test_validateReviewCreationDTO_validateIdIsTrue_willThrowEmptyFieldExceptionForRatingLessThanZero() {
            reviewCreationDTO.setRating(-0.1);
            InvalidDoubleRange actualException = Assertions.assertThrows(InvalidDoubleRange.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            Assertions.assertEquals("Field: rating must be between 0.000000 and 5.000000",
                    actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw InvalidDoubleRange for rating greater than 5")
        void test_validateReviewCreationDTO_validateIdIsTrue_willThrowEmptyFieldExceptionForRatingGreaterThanFive() {
            reviewCreationDTO.setRating(5.1);
            InvalidDoubleRange actualException = Assertions.assertThrows(InvalidDoubleRange.class,
                    () -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            Assertions.assertEquals("Field: rating must be between 0.000000 and 5.000000",
                    actualException.getMessage());
        }

        @Test
        @DisplayName("Test should not throw InvalidDoubleRange for rating equal to 0")
        void test_validateReviewCreationDTO_validateIdIsTrue_willNotThrowInvalidDoubleRangeForRatingEqualToZero() {
            reviewCreationDTO.setRating(0.0);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
        }

        @Test
        @DisplayName("Test should not throw InvalidDoubleRange for rating equal to 5")
        void test_validateReviewCreationDTO_validateIdIsTrue_willNotThrowInvalidDoubleRangeForRatingEqualToFive() {
            reviewCreationDTO.setRating(5.0);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
        }

        @Test
        @DisplayName("Test should not throw InvalidDoubleRange for rating between 0 and 5")
        void test_validateReviewCreationDTO_validateIdIsTrue_willNotThrowInvalidDoubleRangeForRatingBetweenZeroAndFive() {
            reviewCreationDTO.setRating(2.5);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
        }

        @Test
        @DisplayName("Test should not throw EmptyFieldException for null userId")
        void test_validateReviewCreationDTO_validateIdIsTrue_willNotThrowEmptyFieldExceptionForNullUserId() {
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            reviewCreationDTO.setUserId(null);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
        }

        @Test
        @DisplayName("Test should not throw EmptyFieldException for null bookId")
        void test_validateReviewCreationDTO_validateIdIsTrue_willNotThrowEmptyFieldExceptionForNullBookId() {
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
            reviewCreationDTO.setBookId(null);
            Assertions.assertDoesNotThrow(() -> ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, true));
        }
    }
}