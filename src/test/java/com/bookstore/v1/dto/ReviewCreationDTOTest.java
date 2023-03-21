package com.bookstore.v1.dto;

import com.bookstore.v1.data.Review;
import org.junit.jupiter.api.*;

class ReviewCreationDTOTest {
    // initial and updated values for the class fields
    final String ID = "reviewId";
    final String TITLE = "reviewTitle";
    final String DESCRIPTION = "reviewDescription";
    final Double RATING = 5.0;
    final String USER_ID = "userId";
    final String BOOK_ID = "bookId";
    // updated values
    final String NEW_ID = "newReviewId";
    final String NEW_TITLE = "newReviewTitle";
    final String NEW_DESCRIPTION = "newReviewDescription";
    final Double NEW_RATING = 1.0;
    final String NEW_USER_ID = "newUserId";
    final String NEW_BOOK_ID = "newBookId";

    // class objects to use in tests
    ReviewCreationDTO emptyReviewCreationDTO;
    ReviewCreationDTO reviewCreationDTOWithoutId;
    ReviewCreationDTO reviewCreationDTOWithId;

    @BeforeEach
    void setUp() {
        emptyReviewCreationDTO = new ReviewCreationDTO();
        reviewCreationDTOWithoutId = new ReviewCreationDTO(TITLE, DESCRIPTION, RATING, USER_ID, BOOK_ID);
        reviewCreationDTOWithId = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID, BOOK_ID);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test toReview method with empty ReviewCreationDTO")
    void test_toReview_whenReviewCreationDTOIsEmpty() {
        Review review = emptyReviewCreationDTO.toReview();
        Assertions.assertNull(review.getId());
        Assertions.assertNull(review.getTitle());
        Assertions.assertNull(review.getDescription());
        Assertions.assertNull(review.getRating());
    }

    @Test
    @DisplayName("Test toReview method with ReviewCreationDTO without id")
    void test_toReview_dtoWithoutId() {
        String expectedTitle = "reviewTitle";
        String expectedDescription = "reviewDescription";
        Double expectedRating = 5.0;

        // method without params & object with id set to null
        Review review = reviewCreationDTOWithoutId.toReview();
        Assertions.assertNull(review.getId());
        Assertions.assertSame(expectedTitle, review.getTitle());
        Assertions.assertSame(expectedDescription, review.getDescription());
        Assertions.assertEquals(expectedRating, review.getRating());

        // method with param set to true
        review = reviewCreationDTOWithId.toReview(true);
        Assertions.assertNull(review.getId());
        Assertions.assertSame(expectedTitle, review.getTitle());
        Assertions.assertSame(expectedDescription, review.getDescription());
        Assertions.assertEquals(expectedRating, review.getRating());
    }

    @Test
    @DisplayName("Test toReview method with ReviewCreationDTO with id")
    void test_toReview_dtoWithId() {
        String expectedId = "reviewId";
        String expectedTitle = "reviewTitle";
        String expectedDescription = "reviewDescription";
        Double expectedRating = 5.0;

        // method without params
        Review review = reviewCreationDTOWithId.toReview();
        Assertions.assertSame(expectedId, review.getId());
        Assertions.assertSame(expectedTitle, review.getTitle());
        Assertions.assertSame(expectedDescription, review.getDescription());
        Assertions.assertEquals(expectedRating, review.getRating());

        // method with param withoutId set to false
        review = reviewCreationDTOWithId.toReview(false);
        Assertions.assertSame(expectedId, review.getId());
        Assertions.assertSame(expectedTitle, review.getTitle());
        Assertions.assertSame(expectedDescription, review.getDescription());
        Assertions.assertEquals(expectedRating, review.getRating());
    }

    @Test
    @DisplayName("Test getId method")
    void test_getId() {
        Assertions.assertNull(emptyReviewCreationDTO.getId());
        Assertions.assertNull(reviewCreationDTOWithoutId.getId());
        Assertions.assertSame(ID, reviewCreationDTOWithId.getId());
    }

    @Test
    @DisplayName("Test setId method")
    void test_setId() {
        Assertions.assertNull(emptyReviewCreationDTO.getId());
        emptyReviewCreationDTO.setId(ID);
        Assertions.assertSame(ID, emptyReviewCreationDTO.getId());
        emptyReviewCreationDTO.setId(NEW_ID);
        Assertions.assertSame(NEW_ID, emptyReviewCreationDTO.getId());
    }

    @Test
    @DisplayName("Test getTitle method")
    void test_getTitle() {
        Assertions.assertNull(emptyReviewCreationDTO.getTitle());
        Assertions.assertSame(TITLE, reviewCreationDTOWithoutId.getTitle());
        Assertions.assertSame(TITLE, reviewCreationDTOWithId.getTitle());
    }

    @Test
    @DisplayName("Test setTitle method")
    void test_setTitle() {
        Assertions.assertNull(emptyReviewCreationDTO.getTitle());
        emptyReviewCreationDTO.setTitle(TITLE);
        Assertions.assertSame(TITLE, emptyReviewCreationDTO.getTitle());
        emptyReviewCreationDTO.setTitle(NEW_TITLE);
        Assertions.assertSame(NEW_TITLE, emptyReviewCreationDTO.getTitle());
    }

    @Test
    @DisplayName("Test getDescription method")
    void test_getDescription() {
        Assertions.assertNull(emptyReviewCreationDTO.getDescription());
        Assertions.assertSame(DESCRIPTION, reviewCreationDTOWithoutId.getDescription());
        Assertions.assertSame(DESCRIPTION, reviewCreationDTOWithId.getDescription());
    }

    @Test
    @DisplayName("Test setDescription method")
    void test_setDescription() {
        Assertions.assertNull(emptyReviewCreationDTO.getDescription());
        emptyReviewCreationDTO.setDescription(DESCRIPTION);
        Assertions.assertSame(DESCRIPTION, emptyReviewCreationDTO.getDescription());
        emptyReviewCreationDTO.setDescription(NEW_DESCRIPTION);
        Assertions.assertSame(NEW_DESCRIPTION, emptyReviewCreationDTO.getDescription());
    }

    @Test
    @DisplayName("Test getRating method")
    void test_getRating() {
        Assertions.assertNull(emptyReviewCreationDTO.getRating());
        Assertions.assertEquals(RATING, reviewCreationDTOWithoutId.getRating());
        Assertions.assertEquals(RATING, reviewCreationDTOWithId.getRating());
    }

    @Test
    @DisplayName("Test setRating method")
    void test_setRating() {
        Assertions.assertNull(emptyReviewCreationDTO.getRating());
        emptyReviewCreationDTO.setRating(RATING);
        Assertions.assertEquals(RATING, emptyReviewCreationDTO.getRating());
        emptyReviewCreationDTO.setRating(NEW_RATING);
        Assertions.assertEquals(NEW_RATING, emptyReviewCreationDTO.getRating());
    }

    @Test
    @DisplayName("Test getUserId method")
    void test_getUserId() {
        Assertions.assertNull(emptyReviewCreationDTO.getUserId());
        Assertions.assertSame(USER_ID, reviewCreationDTOWithoutId.getUserId());
        Assertions.assertSame(USER_ID, reviewCreationDTOWithId.getUserId());
    }

    @Test
    @DisplayName("Test setUserId method")
    void test_setUserId() {
        Assertions.assertNull(emptyReviewCreationDTO.getUserId());
        emptyReviewCreationDTO.setUserId(USER_ID);
        Assertions.assertSame(USER_ID, emptyReviewCreationDTO.getUserId());
        emptyReviewCreationDTO.setUserId(NEW_USER_ID);
        Assertions.assertSame(NEW_USER_ID, emptyReviewCreationDTO.getUserId());
    }

    @Test
    @DisplayName("Test getBookId method")
    void test_getBookId() {
        Assertions.assertNull(emptyReviewCreationDTO.getBookId());
        Assertions.assertSame(BOOK_ID, reviewCreationDTOWithoutId.getBookId());
        Assertions.assertSame(BOOK_ID, reviewCreationDTOWithId.getBookId());
    }

    @Test
    @DisplayName("Test setBookId method")
    void test_setBookId() {
        Assertions.assertNull(emptyReviewCreationDTO.getBookId());
        emptyReviewCreationDTO.setBookId(BOOK_ID);
        Assertions.assertSame(BOOK_ID, emptyReviewCreationDTO.getBookId());
        emptyReviewCreationDTO.setBookId(NEW_BOOK_ID);
        Assertions.assertSame(NEW_BOOK_ID, emptyReviewCreationDTO.getBookId());
    }

    @Test
    @DisplayName("Test equals method is reflexive")
    void test_equals_isReflexive() {
        Assertions.assertEquals(emptyReviewCreationDTO, emptyReviewCreationDTO);
        Assertions.assertEquals(reviewCreationDTOWithoutId, reviewCreationDTOWithoutId);
        Assertions.assertEquals(reviewCreationDTOWithId, reviewCreationDTOWithId);
    }

    @Test
    @DisplayName("Test equals method is symmetric")
    void test_equals_isSymmetric() {
        ReviewCreationDTO otherEmptyReviewCreationDTO = new ReviewCreationDTO();
        Assertions.assertEquals(emptyReviewCreationDTO, otherEmptyReviewCreationDTO);
        Assertions.assertEquals(otherEmptyReviewCreationDTO, emptyReviewCreationDTO);

        ReviewCreationDTO otherReviewCreationDTOWithoutId = new ReviewCreationDTO(TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithoutId, otherReviewCreationDTOWithoutId);
        Assertions.assertEquals(otherReviewCreationDTOWithoutId, reviewCreationDTOWithoutId);

        ReviewCreationDTO otherReviewCreationDTOWithId = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithId, otherReviewCreationDTOWithId);
        Assertions.assertEquals(otherReviewCreationDTOWithId, reviewCreationDTOWithId);
    }

    @Test
    @DisplayName("Test equals method is transitive")
    void test_equals_isTransitive() {
        ReviewCreationDTO emptyReviewCreationDTO2 = new ReviewCreationDTO();
        ReviewCreationDTO emptyReviewCreationDTO3 = new ReviewCreationDTO();
        Assertions.assertEquals(emptyReviewCreationDTO, emptyReviewCreationDTO2);
        Assertions.assertEquals(emptyReviewCreationDTO2, emptyReviewCreationDTO3);
        Assertions.assertEquals(emptyReviewCreationDTO, emptyReviewCreationDTO3);

        ReviewCreationDTO reviewCreationDTOWithoutId2 = new ReviewCreationDTO(TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        ReviewCreationDTO reviewCreationDTOWithoutId3 = new ReviewCreationDTO(TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithoutId, reviewCreationDTOWithoutId2);
        Assertions.assertEquals(reviewCreationDTOWithoutId2, reviewCreationDTOWithoutId3);
        Assertions.assertEquals(reviewCreationDTOWithoutId, reviewCreationDTOWithoutId3);

        ReviewCreationDTO reviewCreationDTOWithId2 = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        ReviewCreationDTO reviewCreationDTOWithId3 = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithId, reviewCreationDTOWithId2);
        Assertions.assertEquals(reviewCreationDTOWithId2, reviewCreationDTOWithId3);
        Assertions.assertEquals(reviewCreationDTOWithId, reviewCreationDTOWithId3);
    }

    @Test
    @DisplayName("Test equals method is consistent when called multiple times on the same instances")
    void test_equals_isConsistent() {
        ReviewCreationDTO otherEmptyReviewCreationDTO = new ReviewCreationDTO();
        Assertions.assertEquals(emptyReviewCreationDTO, otherEmptyReviewCreationDTO);
        Assertions.assertEquals(emptyReviewCreationDTO, otherEmptyReviewCreationDTO);

        ReviewCreationDTO otherReviewCreationDTOWithoutId = new ReviewCreationDTO(TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithoutId, otherReviewCreationDTOWithoutId);
        Assertions.assertEquals(reviewCreationDTOWithoutId, otherReviewCreationDTOWithoutId);

        ReviewCreationDTO otherReviewCreationDTOWithId = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithId, otherReviewCreationDTOWithId);
        Assertions.assertEquals(reviewCreationDTOWithId, otherReviewCreationDTOWithId);
    }

    @Test
    @DisplayName("Test equals method is null safe")
    void test_equals_isNullSafe() {
        Assertions.assertNotEquals(null, emptyReviewCreationDTO);
        Assertions.assertNotEquals(null, reviewCreationDTOWithoutId);
        Assertions.assertNotEquals(null, reviewCreationDTOWithId);
    }

    @Test
    @DisplayName("Test equals method is false for instances of different classes")
    void test_equals_isFalseForInstancesOfDifferentClasses() {
        Assertions.assertNotEquals(emptyReviewCreationDTO, new Object());
        Assertions.assertNotEquals(reviewCreationDTOWithoutId, new Object());
        Assertions.assertNotEquals(reviewCreationDTOWithId, new Object());
    }

    @Test
    @DisplayName("Test hashCode method is consistent when called multiple times on the same instances")
    void test_hashCode_isConsistent() {
        ReviewCreationDTO otherEmptyReviewCreationDTO = new ReviewCreationDTO();
        Assertions.assertEquals(emptyReviewCreationDTO.hashCode(), otherEmptyReviewCreationDTO.hashCode());
        Assertions.assertEquals(emptyReviewCreationDTO.hashCode(), otherEmptyReviewCreationDTO.hashCode());

        ReviewCreationDTO otherReviewCreationDTOWithoutId = new ReviewCreationDTO(TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithoutId.hashCode(), otherReviewCreationDTOWithoutId.hashCode());
        Assertions.assertEquals(reviewCreationDTOWithoutId.hashCode(), otherReviewCreationDTOWithoutId.hashCode());

        ReviewCreationDTO otherReviewCreationDTOWithId = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithId.hashCode(), otherReviewCreationDTOWithId.hashCode());
        Assertions.assertEquals(reviewCreationDTOWithId.hashCode(), otherReviewCreationDTOWithId.hashCode());
    }

    @Test
    @DisplayName("Test hashCode method is equal for equal instances")
    void test_hashCode_isEqualForEqualInstances() {
        ReviewCreationDTO otherEmptyReviewCreationDTO = new ReviewCreationDTO();
        Assertions.assertEquals(emptyReviewCreationDTO.hashCode(), otherEmptyReviewCreationDTO.hashCode());

        ReviewCreationDTO otherReviewCreationDTOWithoutId = new ReviewCreationDTO(TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithoutId.hashCode(), otherReviewCreationDTOWithoutId.hashCode());

        ReviewCreationDTO otherReviewCreationDTOWithId = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        Assertions.assertEquals(reviewCreationDTOWithId.hashCode(), otherReviewCreationDTOWithId.hashCode());
    }

    @Test
    @DisplayName("Test hashCode method is not equal for not equal instances")
    void test_hashCode_isNotEqualForNotEqualInstances() {
        ReviewCreationDTO otherEmptyReviewCreationDTO = new ReviewCreationDTO();
        otherEmptyReviewCreationDTO.setId(ID);
        Assertions.assertNotEquals(emptyReviewCreationDTO.hashCode(), otherEmptyReviewCreationDTO.hashCode());

        ReviewCreationDTO otherReviewCreationDTOWithoutId = new ReviewCreationDTO(TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        otherReviewCreationDTOWithoutId.setId(ID);
        Assertions.assertNotEquals(reviewCreationDTOWithoutId.hashCode(), otherReviewCreationDTOWithoutId.hashCode());

        ReviewCreationDTO otherReviewCreationDTOWithId = new ReviewCreationDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID,
                BOOK_ID);
        otherReviewCreationDTOWithId.setId(ID + 1);
        Assertions.assertNotEquals(reviewCreationDTOWithId.hashCode(), otherReviewCreationDTOWithId.hashCode());
    }
}