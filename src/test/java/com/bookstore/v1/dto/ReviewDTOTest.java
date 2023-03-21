package com.bookstore.v1.dto;

import com.bookstore.v1.data.Book;
import com.bookstore.v1.data.Review;
import com.bookstore.v1.data.User;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

class ReviewDTOTest {
    // initial and updated values for the class fields
    final String ID = "reviewId";
    final String TITLE = "reviewTitle";
    final String DESCRIPTION = "reviewDescription";
    final Double RATING = 5.0;
    final String USER_ID = "userId";
    final User USER = new User(USER_ID, "userName", "userEmail", "userPhoneNumber");
    final UserDTO USER_DTO = new UserDTO(USER);
    final String BOOK_ID = "bookId";
    final Book BOOK = new Book(BOOK_ID, "bookTitle", "bookAuthor", "bookPublisher", "bookISBN", LocalDate.now());
    final BookDTO BOOK_DTO = new BookDTO(BOOK);
    // updated values
    final String NEW_ID = "newReviewId";
    final String NEW_TITLE = "newReviewTitle";
    final String NEW_DESCRIPTION = "newReviewDescription";
    final Double NEW_RATING = 1.0;
    final String NEW_USER_ID = "newUserId";
    final User NEW_USER = new User(NEW_USER_ID, "newUserName", "newUserEmail", "newUserPhoneNumber");
    final UserDTO NEW_USER_DTO = new UserDTO(NEW_USER);
    final String NEW_BOOK_ID = "newBookId";
    final Book NEW_BOOK = new Book(NEW_BOOK_ID, "newBookTitle", "newBookAuthor", "newBookPublisher", "newBookISBN",
            LocalDate.EPOCH);
    final BookDTO NEW_BOOK_DTO = new BookDTO(NEW_BOOK);

    // objects to use in tests, we use more to make sure that the only difference is the one described in the name
    Review review;
    ReviewDTO emptyReviewDTO;
    ReviewDTO reviewDTOFromSimpleConstructor;
    ReviewDTO reviewDTO;
    ReviewDTO reviewDTOWithUser;
    ReviewDTO reviewDTOWithBook;
    ReviewDTO reviewDTOWithUserAndBook;

    @BeforeEach
    void setUp() {
        emptyReviewDTO = new ReviewDTO();
        reviewDTOFromSimpleConstructor = new ReviewDTO(ID, TITLE, DESCRIPTION, RATING, USER_ID, BOOK_ID);
        // configure review object to create the DTOs
        review = new Review(ID, TITLE, DESCRIPTION, RATING);
        review.setUser(USER);
        review.setBook(BOOK);
        reviewDTO = new ReviewDTO(review, false, false);
        reviewDTOWithUser = new ReviewDTO(review, true, false);
        reviewDTOWithBook = new ReviewDTO(review, false, true);
        reviewDTOWithUserAndBook = new ReviewDTO(review, true, true);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test that the simple constructor sets the same value as the conversion one without user and book")
    void test_simpleConstructor() {
        Assertions.assertEquals(reviewDTOFromSimpleConstructor, reviewDTO);
    }

    @Test
    @DisplayName("Test getId method")
    void test_getId() {
        Assertions.assertNull(emptyReviewDTO.getId());
        Assertions.assertSame(ID, reviewDTO.getId());
        Assertions.assertSame(ID, reviewDTOWithUser.getId());
        Assertions.assertSame(ID, reviewDTOWithBook.getId());
        Assertions.assertSame(ID, reviewDTOWithUserAndBook.getId());
    }

    @Test
    @DisplayName("Test setId method")
    void test_setId() {
        Assertions.assertNull(emptyReviewDTO.getId());
        emptyReviewDTO.setId(ID);
        Assertions.assertSame(ID, emptyReviewDTO.getId());
        emptyReviewDTO.setId(NEW_ID);
        Assertions.assertSame(NEW_ID, emptyReviewDTO.getId());
    }

    @Test
    @DisplayName("Test getTitle method")
    void test_getTitle() {
        Assertions.assertNull(emptyReviewDTO.getTitle());
        Assertions.assertSame(TITLE, reviewDTO.getTitle());
        Assertions.assertSame(TITLE, reviewDTOWithUser.getTitle());
        Assertions.assertSame(TITLE, reviewDTOWithBook.getTitle());
        Assertions.assertSame(TITLE, reviewDTOWithUserAndBook.getTitle());
    }

    @Test
    @DisplayName("Test setTitle method")
    void test_setTitle() {
        Assertions.assertNull(emptyReviewDTO.getTitle());
        emptyReviewDTO.setTitle(TITLE);
        Assertions.assertSame(TITLE, emptyReviewDTO.getTitle());
        emptyReviewDTO.setTitle(NEW_TITLE);
        Assertions.assertSame(NEW_TITLE, emptyReviewDTO.getTitle());
    }

    @Test
    @DisplayName("Test getDescription method")
    void test_getDescription() {
        Assertions.assertNull(emptyReviewDTO.getDescription());
        Assertions.assertSame(DESCRIPTION, reviewDTO.getDescription());
        Assertions.assertSame(DESCRIPTION, reviewDTOWithUser.getDescription());
        Assertions.assertSame(DESCRIPTION, reviewDTOWithBook.getDescription());
        Assertions.assertSame(DESCRIPTION, reviewDTOWithUserAndBook.getDescription());
    }

    @Test
    @DisplayName("Test setDescription method")
    void test_setDescription() {
        Assertions.assertNull(emptyReviewDTO.getDescription());
        emptyReviewDTO.setDescription(DESCRIPTION);
        Assertions.assertSame(DESCRIPTION, emptyReviewDTO.getDescription());
        emptyReviewDTO.setDescription(NEW_DESCRIPTION);
        Assertions.assertSame(NEW_DESCRIPTION, emptyReviewDTO.getDescription());
    }

    @Test
    @DisplayName("Test getRating method")
    void test_getRating() {
        Assertions.assertNull(emptyReviewDTO.getRating());
        Assertions.assertEquals(RATING, reviewDTO.getRating());
        Assertions.assertEquals(RATING, reviewDTOWithUser.getRating());
        Assertions.assertEquals(RATING, reviewDTOWithBook.getRating());
        Assertions.assertEquals(RATING, reviewDTOWithUserAndBook.getRating());
    }

    @Test
    @DisplayName("Test setRating method")
    void test_setRating() {
        Assertions.assertNull(emptyReviewDTO.getRating());
        emptyReviewDTO.setRating(RATING);
        Assertions.assertEquals(RATING, emptyReviewDTO.getRating());
        emptyReviewDTO.setRating(NEW_RATING);
        Assertions.assertEquals(NEW_RATING, emptyReviewDTO.getRating());
    }

    @Test
    @DisplayName("Test getUserId method")
    void test_getUserId() {
        Assertions.assertNull(emptyReviewDTO.getUserId());
        Assertions.assertSame(USER_ID, reviewDTO.getUserId());
        Assertions.assertSame(USER_ID, reviewDTOWithUser.getUserId());
        Assertions.assertSame(USER_ID, reviewDTOWithBook.getUserId());
        Assertions.assertSame(USER_ID, reviewDTOWithUserAndBook.getUserId());
    }

    @Test
    @DisplayName("Test setUserId method")
    void test_setUserId() {
        Assertions.assertNull(emptyReviewDTO.getUserId());
        emptyReviewDTO.setUserId(USER_ID);
        Assertions.assertSame(USER_ID, emptyReviewDTO.getUserId());
        emptyReviewDTO.setUserId(NEW_USER_ID);
        Assertions.assertSame(NEW_USER_ID, emptyReviewDTO.getUserId());
    }

    @Test
    @DisplayName("Test getBookId method")
    void test_getBookId() {
        Assertions.assertNull(emptyReviewDTO.getBookId());
        Assertions.assertSame(BOOK_ID, reviewDTO.getBookId());
        Assertions.assertSame(BOOK_ID, reviewDTOWithUser.getBookId());
        Assertions.assertSame(BOOK_ID, reviewDTOWithBook.getBookId());
        Assertions.assertSame(BOOK_ID, reviewDTOWithUserAndBook.getBookId());
    }

    @Test
    @DisplayName("Test setBookId method")
    void test_setBookId() {
        Assertions.assertNull(emptyReviewDTO.getBookId());
        emptyReviewDTO.setBookId(BOOK_ID);
        Assertions.assertSame(BOOK_ID, emptyReviewDTO.getBookId());
        emptyReviewDTO.setBookId(NEW_BOOK_ID);
        Assertions.assertSame(NEW_BOOK_ID, emptyReviewDTO.getBookId());
    }

    @Test
    @DisplayName("Test getUser method")
    void test_getUser() {
        // the following objects shouldn't contain the user
        Assertions.assertNull(emptyReviewDTO.getUser());
        Assertions.assertNull(reviewDTO.getUser());
        Assertions.assertNull(reviewDTOWithBook.getUser());
        // the following objects should contain the user
        Assertions.assertEquals(USER_DTO, reviewDTOWithUser.getUser());
        Assertions.assertEquals(USER_DTO, reviewDTOWithUserAndBook.getUser());
    }

    @Test
    @DisplayName("Test setUser method")
    void test_setUser() {
        Assertions.assertNull(emptyReviewDTO.getUser());
        emptyReviewDTO.setUser(USER_DTO);
        Assertions.assertEquals(USER_DTO, emptyReviewDTO.getUser());
        emptyReviewDTO.setUser(NEW_USER_DTO);
        Assertions.assertEquals(NEW_USER_DTO, emptyReviewDTO.getUser());
    }

    @Test
    @DisplayName("Test getBook method")
    void test_getBook() {
        // the following objects shouldn't contain the book
        Assertions.assertNull(emptyReviewDTO.getBook());
        Assertions.assertNull(reviewDTO.getBook());
        Assertions.assertNull(reviewDTOWithUser.getBook());
        // the following objects should contain the book
        Assertions.assertEquals(BOOK_DTO, reviewDTOWithBook.getBook());
        Assertions.assertEquals(BOOK_DTO, reviewDTOWithUserAndBook.getBook());
    }

    @Test
    @DisplayName("Test setBook method")
    void test_setBook() {
        Assertions.assertNull(emptyReviewDTO.getBook());
        emptyReviewDTO.setBook(BOOK_DTO);
        Assertions.assertEquals(BOOK_DTO, emptyReviewDTO.getBook());
        emptyReviewDTO.setBook(NEW_BOOK_DTO);
        Assertions.assertEquals(NEW_BOOK_DTO, emptyReviewDTO.getBook());
    }

    @Test
    @DisplayName("Test equals method is reflexive")
    void test_equals_isReflexive() {
        Assertions.assertEquals(emptyReviewDTO, emptyReviewDTO);
        Assertions.assertEquals(reviewDTO, reviewDTO);
        Assertions.assertEquals(reviewDTOWithUser, reviewDTOWithUser);
        Assertions.assertEquals(reviewDTOWithBook, reviewDTOWithBook);
        Assertions.assertEquals(reviewDTOWithUserAndBook, reviewDTOWithUserAndBook);
    }

    @Test
    @DisplayName("Test equals method is symmetric")
    void test_equals_isSymmetric() {
        ReviewDTO otherEmptyReviewDTO = new ReviewDTO();
        Assertions.assertEquals(emptyReviewDTO, otherEmptyReviewDTO);
        Assertions.assertEquals(otherEmptyReviewDTO, emptyReviewDTO);

        ReviewDTO otherReviewDTO = new ReviewDTO(review, false, false);
        Assertions.assertEquals(reviewDTO, otherReviewDTO);
        Assertions.assertEquals(otherReviewDTO, reviewDTO);

        ReviewDTO otherReviewDTOWithUser = new ReviewDTO(review, true, false);
        Assertions.assertEquals(reviewDTOWithUser, otherReviewDTOWithUser);
        Assertions.assertEquals(otherReviewDTOWithUser, reviewDTOWithUser);

        ReviewDTO otherReviewDTOWithBook = new ReviewDTO(review, false, true);
        Assertions.assertEquals(reviewDTOWithBook, otherReviewDTOWithBook);
        Assertions.assertEquals(otherReviewDTOWithBook, reviewDTOWithBook);

        ReviewDTO otherReviewDTOWithUserAndBook = new ReviewDTO(review, true, true);
        Assertions.assertEquals(reviewDTOWithUserAndBook, otherReviewDTOWithUserAndBook);
        Assertions.assertEquals(otherReviewDTOWithUserAndBook, reviewDTOWithUserAndBook);
    }

    @Test
    @DisplayName("Test equals method is transitive")
    void test_equals_isTransitive() {
        ReviewDTO emptyReviewDTO2 = new ReviewDTO();
        ReviewDTO emptyReviewDTO3 = new ReviewDTO();
        Assertions.assertEquals(emptyReviewDTO, emptyReviewDTO2);
        Assertions.assertEquals(emptyReviewDTO2, emptyReviewDTO3);
        Assertions.assertEquals(emptyReviewDTO, emptyReviewDTO3);

        ReviewDTO reviewDTO2 = new ReviewDTO(review, false, false);
        ReviewDTO reviewDTO3 = new ReviewDTO(review, false, false);
        Assertions.assertEquals(reviewDTO, reviewDTO2);
        Assertions.assertEquals(reviewDTO2, reviewDTO3);
        Assertions.assertEquals(reviewDTO, reviewDTO3);

        ReviewDTO reviewDTOWithUser2 = new ReviewDTO(review, true, false);
        ReviewDTO reviewDTOWithUser3 = new ReviewDTO(review, true, false);
        Assertions.assertEquals(reviewDTOWithUser, reviewDTOWithUser2);
        Assertions.assertEquals(reviewDTOWithUser2, reviewDTOWithUser3);
        Assertions.assertEquals(reviewDTOWithUser, reviewDTOWithUser3);

        ReviewDTO reviewDTOWithBook2 = new ReviewDTO(review, false, true);
        ReviewDTO reviewDTOWithBook3 = new ReviewDTO(review, false, true);
        Assertions.assertEquals(reviewDTOWithBook, reviewDTOWithBook2);
        Assertions.assertEquals(reviewDTOWithBook2, reviewDTOWithBook3);
        Assertions.assertEquals(reviewDTOWithBook, reviewDTOWithBook3);

        ReviewDTO reviewDTOWithUserAndBook2 = new ReviewDTO(review, true, true);
        ReviewDTO reviewDTOWithUserAndBook3 = new ReviewDTO(review, true, true);
        Assertions.assertEquals(reviewDTOWithUserAndBook, reviewDTOWithUserAndBook2);
        Assertions.assertEquals(reviewDTOWithUserAndBook2, reviewDTOWithUserAndBook3);
        Assertions.assertEquals(reviewDTOWithUserAndBook, reviewDTOWithUserAndBook3);
    }

    @Test
    @DisplayName("Test equals method is consistent when called multiple times on the same instances")
    void test_equals_isConsistent() {
        ReviewDTO otherEmptyReviewDTO = new ReviewDTO();
        Assertions.assertEquals(emptyReviewDTO, otherEmptyReviewDTO);
        Assertions.assertEquals(emptyReviewDTO, otherEmptyReviewDTO);

        ReviewDTO otherReviewDTO = new ReviewDTO(review, false, false);
        Assertions.assertEquals(reviewDTO, otherReviewDTO);
        Assertions.assertEquals(reviewDTO, otherReviewDTO);

        ReviewDTO otherReviewDTOWithUser = new ReviewDTO(review, true, false);
        Assertions.assertEquals(reviewDTOWithUser, otherReviewDTOWithUser);
        Assertions.assertEquals(reviewDTOWithUser, otherReviewDTOWithUser);

        ReviewDTO otherReviewDTOWithBook = new ReviewDTO(review, false, true);
        Assertions.assertEquals(reviewDTOWithBook, otherReviewDTOWithBook);
        Assertions.assertEquals(reviewDTOWithBook, otherReviewDTOWithBook);

        ReviewDTO otherReviewDTOWithUserAndBook = new ReviewDTO(review, true, true);
        Assertions.assertEquals(reviewDTOWithUserAndBook, otherReviewDTOWithUserAndBook);
        Assertions.assertEquals(reviewDTOWithUserAndBook, otherReviewDTOWithUserAndBook);
    }

    @Test
    @DisplayName("Test equals method is null safe")
    void test_equals_isNullSafe() {
        Assertions.assertNotNull(emptyReviewDTO);
        Assertions.assertNotEquals(null, reviewDTO);
        Assertions.assertNotEquals(null, reviewDTOWithUser);
        Assertions.assertNotEquals(null, reviewDTOWithBook);
        Assertions.assertNotEquals(null, reviewDTOWithUserAndBook);
    }

    @Test
    @DisplayName("Test equals method is false for different objects of the same class")
    void test_equals_isFalseForDifferentObjects() {
        Assertions.assertNotEquals(emptyReviewDTO, reviewDTO);
        Assertions.assertNotEquals(emptyReviewDTO, reviewDTOWithUser);
        Assertions.assertNotEquals(emptyReviewDTO, reviewDTOWithBook);
        Assertions.assertNotEquals(emptyReviewDTO, reviewDTOWithUserAndBook);
        Assertions.assertNotEquals(reviewDTO, reviewDTOWithUser);
        Assertions.assertNotEquals(reviewDTO, reviewDTOWithBook);
        Assertions.assertNotEquals(reviewDTO, reviewDTOWithUserAndBook);
        Assertions.assertNotEquals(reviewDTOWithUser, reviewDTOWithBook);
        Assertions.assertNotEquals(reviewDTOWithUser, reviewDTOWithUserAndBook);
        Assertions.assertNotEquals(reviewDTOWithBook, reviewDTOWithUserAndBook);
    }

    @Test
    @DisplayName("Test equals method is false for instances of different classes")
    void test_equals_isFalseForDifferentClasses() {
        Assertions.assertNotEquals(emptyReviewDTO, new Object());
        Assertions.assertNotEquals(reviewDTO, new Object());
        Assertions.assertNotEquals(reviewDTOWithUser, new Object());
        Assertions.assertNotEquals(reviewDTOWithBook, new Object());
        Assertions.assertNotEquals(reviewDTOWithUserAndBook, new Object());
    }

    @Test
    @DisplayName("Test hashCode method is consistent when called multiple times on the same instances")
    void test_hashCode_isConsistent() {
        ReviewDTO otherEmptyReviewDTO = new ReviewDTO();
        Assertions.assertEquals(emptyReviewDTO.hashCode(), otherEmptyReviewDTO.hashCode());
        Assertions.assertEquals(emptyReviewDTO.hashCode(), otherEmptyReviewDTO.hashCode());

        ReviewDTO otherReviewDTO = new ReviewDTO(review, false, false);
        Assertions.assertEquals(reviewDTO.hashCode(), otherReviewDTO.hashCode());
        Assertions.assertEquals(reviewDTO.hashCode(), otherReviewDTO.hashCode());

        ReviewDTO otherReviewDTOWithUser = new ReviewDTO(review, true, false);
        Assertions.assertEquals(reviewDTOWithUser.hashCode(), otherReviewDTOWithUser.hashCode());
        Assertions.assertEquals(reviewDTOWithUser.hashCode(), otherReviewDTOWithUser.hashCode());

        ReviewDTO otherReviewDTOWithBook = new ReviewDTO(review, false, true);
        Assertions.assertEquals(reviewDTOWithBook.hashCode(), otherReviewDTOWithBook.hashCode());
        Assertions.assertEquals(reviewDTOWithBook.hashCode(), otherReviewDTOWithBook.hashCode());

        ReviewDTO otherReviewDTOWithUserAndBook = new ReviewDTO(review, true, true);
        Assertions.assertEquals(reviewDTOWithUserAndBook.hashCode(), otherReviewDTOWithUserAndBook.hashCode());
        Assertions.assertEquals(reviewDTOWithUserAndBook.hashCode(), otherReviewDTOWithUserAndBook.hashCode());
    }

    @Test
    @DisplayName("Test hashCode is consistent with equals")
    void test_hashCode_isConsistentWithEquals() {
        ReviewDTO otherEmptyReviewDTO = new ReviewDTO();
        Assertions.assertEquals(emptyReviewDTO.equals(otherEmptyReviewDTO),
                emptyReviewDTO.hashCode() == otherEmptyReviewDTO.hashCode());

        ReviewDTO otherReviewDTO = new ReviewDTO(review, false, false);
        Assertions.assertEquals(reviewDTO.equals(otherReviewDTO), reviewDTO.hashCode() == otherReviewDTO.hashCode());

        ReviewDTO otherReviewDTOWithUser = new ReviewDTO(review, true, false);
        Assertions.assertEquals(reviewDTOWithUser.equals(otherReviewDTOWithUser),
                reviewDTOWithUser.hashCode() == otherReviewDTOWithUser.hashCode());

        ReviewDTO otherReviewDTOWithBook = new ReviewDTO(review, false, true);
        Assertions.assertEquals(reviewDTOWithBook.equals(otherReviewDTOWithBook),
                reviewDTOWithBook.hashCode() == otherReviewDTOWithBook.hashCode());

        ReviewDTO otherReviewDTOWithUserAndBook = new ReviewDTO(review, true, true);
        Assertions.assertEquals(reviewDTOWithUserAndBook.equals(otherReviewDTOWithUserAndBook),
                reviewDTOWithUserAndBook.hashCode() == otherReviewDTOWithUserAndBook.hashCode());
    }

    @Test
    @DisplayName("Test hasCode produces unequal hash codes for unequal instances")
    void test_hashCode_producesUnequalHashCodesForUnequalInstances() {
        Assertions.assertNotEquals(reviewDTO.hashCode(), reviewDTOWithUser.hashCode());
        Assertions.assertNotEquals(reviewDTO.hashCode(), reviewDTOWithBook.hashCode());
        Assertions.assertNotEquals(reviewDTO.hashCode(), reviewDTOWithUserAndBook.hashCode());
        Assertions.assertNotEquals(reviewDTOWithUser.hashCode(), reviewDTOWithBook.hashCode());
        Assertions.assertNotEquals(reviewDTOWithUser.hashCode(), reviewDTOWithUserAndBook.hashCode());
        Assertions.assertNotEquals(reviewDTOWithBook.hashCode(), reviewDTOWithUserAndBook.hashCode());
    }
}