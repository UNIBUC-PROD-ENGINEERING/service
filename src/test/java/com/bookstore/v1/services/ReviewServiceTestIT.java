package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.ReviewCreationDTO;
import com.bookstore.v1.dto.ReviewDTO;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Tag("IT")
class ReviewServiceTestIT {
    @Mock
    private ReviewRepository mockReviewRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private BookRepository mockBookRepository;
    @InjectMocks
    private ReviewService reviewServiceUnderTest;

    @Nested
    @DisplayName("Tests for addReview method")
    class TestAddReviewMethod {
        @Test
        @DisplayName("Test should add review for valid review creation dto")
        void test_addReview_willPassForValidRequest() {
            String userId = "userId";
            String bookId = "bookId";
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 5.0, userId, bookId);
            User user = new User(userId, "userName", "email", "phoneNumber");
            Book book = new Book(bookId, "title", "author", "publisher", "isbn", LocalDate.now());
            Review createdReview = new Review("reviewId", "title", "description", 5.0);
            createdReview.setUser(user);
            createdReview.setBook(book);
            ReviewDTO expectedReviewDTO = new ReviewDTO(createdReview, true, true);

            when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
            when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));
            when(mockReviewRepository.findByUserAndBook(user, book)).thenReturn(Optional.empty());
            when(mockReviewRepository.save(any(Review.class))).thenReturn(createdReview);

            ReviewDTO actualReviewDTO = reviewServiceUnderTest.addReview(reviewCreationDTO);

            Assertions.assertEquals(expectedReviewDTO, actualReviewDTO);
        }

        @Test
        @DisplayName("Test should throw entity not found exception for nonexistent user")
        void test_addReview_willThrowEntityNotFoundExceptionForNonexistentUser() {
            String userId = "userId";
            String bookId = "bookId";
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 5.0, userId, bookId);

            when(mockUserRepository.findById(userId)).thenReturn(Optional.empty());

            EntityNotFoundException actualException = Assertions.assertThrows(EntityNotFoundException.class,
                    () -> reviewServiceUnderTest.addReview(reviewCreationDTO));
            Assertions.assertEquals("Entity: user was not found", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw entity not found exception for nonexistent book")
        void test_addReview_willThrowEntityNotFoundExceptionForNonexistentBook() {
            String userId = "userId";
            String bookId = "bookId";
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 5.0, userId, bookId);
            User user = new User(userId, "userName", "email", "phoneNumber");

            when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
            when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

            EntityNotFoundException actualException = Assertions.assertThrows(EntityNotFoundException.class,
                    () -> reviewServiceUnderTest.addReview(reviewCreationDTO));
            Assertions.assertEquals("Entity: book was not found", actualException.getMessage());
        }

        @Test
        @DisplayName("Test should throw duplicate object exception for duplicate review by user for book")
        void test_addReview_willThrowDuplicateObjectExceptionForDuplicateReviewByUserForBook() {
            String userId = "userId";
            String bookId = "bookId";
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 5.0, userId, bookId);
            User user = new User(userId, "userName", "email", "phoneNumber");
            Book book = new Book(bookId, "title", "author", "publisher", "isbn", LocalDate.now());

            when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
            when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));
            when(mockReviewRepository.findByUserAndBook(user, book)).thenReturn(Optional.of(new Review()));

            DuplicateObjectException actualException = Assertions.assertThrows(DuplicateObjectException.class,
                    () -> reviewServiceUnderTest.addReview(reviewCreationDTO));
            Assertions.assertEquals("Object: review already exists", actualException.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests for updateReview method")
    class TestUpdateReviewMethod {
        @Test
        @DisplayName("Test should update review for valid review creation dto")
        void test_updateReview_willPassForValidRequest() {
            String reviewId = "reviewId";
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO(reviewId, "newTitle", "newDescription", 1.0,
                    null, null);
            User user = new User("userId", "userName", "email", "phoneNumber");
            Book book = new Book("bookId", "title", "author", "publisher", "isbn", LocalDate.now());
            Review existingReview = new Review(reviewId, "title", "description", 5.0);
            existingReview.setUser(user);
            existingReview.setBook(book);
            Review updatedReview = new Review(reviewId, "newTitle", "newDescription", 1.0);
            updatedReview.setUser(user);
            updatedReview.setBook(book);

            when(mockReviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
            when(mockReviewRepository.save(any(Review.class))).thenReturn(updatedReview);

            ReviewDTO actualReviewDTO = reviewServiceUnderTest.updateReview(reviewCreationDTO);

            Assertions.assertEquals(updatedReview.getTitle(), actualReviewDTO.getTitle());
        }

        @Test
        @DisplayName("Test should throw entity not found exception for nonexistent review")
        void test_updateReview_willThrowEntityNotFoundExceptionForNonexistentReview() {
            String reviewId = "reviewId";
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO(reviewId, "newTitle", "newDescription", 1.0,
                    null, null);

            when(mockReviewRepository.findById(reviewId)).thenReturn(Optional.empty());

            EntityNotFoundException actualException = Assertions.assertThrows(EntityNotFoundException.class,
                    () -> reviewServiceUnderTest.updateReview(reviewCreationDTO));
            Assertions.assertEquals("Entity: review was not found", actualException.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests for deleteReview method")
    class TestDeleteReviewMethod {
        @Test
        @DisplayName("Test should delete review for existent review")
        void test_deleteReview_willPassForExistentReview() {
            String reviewId = "reviewId";
            Review existentReview = new Review(reviewId, "title", "description", 5.0);

            when(mockReviewRepository.findById(reviewId)).thenReturn(Optional.of(existentReview));

            reviewServiceUnderTest.deleteReviewById(reviewId);
        }

        @Test
        @DisplayName("Test should throw entity not found exception for nonexistent review")
        void test_deleteReview_willThrowEntityNotFoundExceptionForNonexistentReview() {
            String reviewId = "reviewId";

            when(mockReviewRepository.findById(reviewId)).thenReturn(Optional.empty());

            EntityNotFoundException actualException = Assertions.assertThrows(EntityNotFoundException.class,
                    () -> reviewServiceUnderTest.deleteReviewById(reviewId));
            Assertions.assertEquals("Entity: review was not found", actualException.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests for getReviewById method")
    class TestGetReviewByIdMethod {
        @Test
        @DisplayName("Test should return review for existent review")
        void test_getReviewById_willReturnReviewForExistentReview() {
            String reviewId = "reviewId";
            User user = new User("userId", "userName", "email", "phoneNumber");
            Book book = new Book("bookId", "title", "author", "publisher", "isbn", LocalDate.now());
            Review existentReview = new Review(reviewId, "title", "description", 5.0);
            existentReview.setUser(user);
            existentReview.setBook(book);
            ReviewDTO expectedReviewDTO = new ReviewDTO(existentReview, true, true);

            when(mockReviewRepository.findById(reviewId)).thenReturn(Optional.of(existentReview));

            ReviewDTO actualReviewDTO = reviewServiceUnderTest.getReviewById(reviewId);
            Assertions.assertEquals(expectedReviewDTO, actualReviewDTO);
        }

        @Test
        @DisplayName("Test should throw entity not found exception for nonexistent review")
        void test_getReviewById_willThrowEntityNotFoundExceptionForNonexistentReview() {
            String reviewId = "reviewId";

            when(mockReviewRepository.findById(reviewId)).thenReturn(Optional.empty());

            EntityNotFoundException actualException = Assertions.assertThrows(EntityNotFoundException.class,
                    () -> reviewServiceUnderTest.getReviewById(reviewId));
            Assertions.assertEquals("Entity: review was not found", actualException.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests for getReviews method")
    class TestGetReviews {
        @Test
        @DisplayName("Test should return empty list of reviews")
        void test_getReviews_willReturnEmptyListOfReviews() {
            when(mockReviewRepository.findAll()).thenReturn(new ArrayList<>());

            List<ReviewDTO> actualReviewDTOs = reviewServiceUnderTest.getReviews();
            Assertions.assertTrue(actualReviewDTOs.isEmpty());
        }

        @Test
        @DisplayName("Test should return list of reviews")
        void test_getReviews_willReturnListOfReviews() {
            List<Review> existingReviews = new ArrayList<>();
            Review review1 = new Review("reviewId1", "title1", "description1", 5.0);
            review1.setUser(new User("userId1", "userName1", "email1", "phoneNumber1"));
            review1.setBook(new Book("bookId1", "title1", "author1", "publisher1", "isbn1", LocalDate.now()));
            Review review2 = new Review("reviewId2", "title2", "description2", 5.0);
            review2.setUser(new User("userId2", "userName2", "email2", "phoneNumber2"));
            review2.setBook(new Book("bookId2", "title2", "author2", "publisher2", "isbn2", LocalDate.now()));
            existingReviews.add(review1);
            existingReviews.add(review2);
            List<ReviewDTO> expectedReviewDTOs = new ArrayList<>();
            expectedReviewDTOs.add(new ReviewDTO(review1, true, true));
            expectedReviewDTOs.add(new ReviewDTO(review2, true, true));

            when(mockReviewRepository.findAll()).thenReturn(existingReviews);

            List<ReviewDTO> actualReviewDTOs = reviewServiceUnderTest.getReviews();
            Assertions.assertEquals(existingReviews.size(), actualReviewDTOs.size());
        }
    }

    @Nested
    @DisplayName("Tests for getBookReviews method")
    class TestGetBookReviewsMethod {
        @Test
        @DisplayName("Test should return empty list of reviews for existent book without reviews")
        void test_getBookReviews_willReturnEmptyListOfReviewsForExistentBookWithoutReviews() {
            String bookId = "bookId";
            Book book = new Book(bookId, "title", "author", "publisher", "isbn", LocalDate.now());

            when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));
            when(mockReviewRepository.findAllByBook(book)).thenReturn(new ArrayList<>());

            List<ReviewDTO> actualReviewDTOs = reviewServiceUnderTest.getBookReviews(bookId);
            Assertions.assertTrue(actualReviewDTOs.isEmpty());
        }

        @Test
        @DisplayName("Test should return list of reviews for existent book with reviews")
        void test_getBookReviews_willReturnListOfReviewsForExistentBookWithReviews() {
            String bookId = "bookId";
            Book book = new Book(bookId, "title", "author", "publisher", "isbn", LocalDate.now());
            List<Review> existentReviews = new ArrayList<>();
            Review review1 = new Review("reviewId1", "title1", "description1", 5.0);
            review1.setBook(book);
            review1.setUser(new User("userId1", "userName1", "email1", "phoneNumber1"));
            existentReviews.add(review1);
            Review review2 = new Review("reviewId2", "title2", "description2", 5.0);
            review2.setBook(book);
            review2.setUser(new User("userId2", "userName2", "email2", "phoneNumber2"));
            existentReviews.add(review2);
            List<ReviewDTO> expectedReviewDTOs = new ArrayList<>();
            expectedReviewDTOs.add(new ReviewDTO(review1, true, false));
            expectedReviewDTOs.add(new ReviewDTO(review2, true, false));

            when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));
            when(mockReviewRepository.findAllByBook(book)).thenReturn(existentReviews);

            List<ReviewDTO> actualReviewDTOs = reviewServiceUnderTest.getBookReviews(bookId);

            Assertions.assertEquals(expectedReviewDTOs, actualReviewDTOs);
        }

        @Test
        @DisplayName("Test should throw entity not found exception for nonexistent book")
        void test_getBookReviews_willThrowEntityNotFoundExceptionForNonexistentBook() {
            String bookId = "bookId";

            when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

            EntityNotFoundException actualException = Assertions.assertThrows(EntityNotFoundException.class,
                    () -> reviewServiceUnderTest.getBookReviews(bookId));
            Assertions.assertEquals("Entity: book was not found", actualException.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests for getUserReviews method")
    class TestGetUserReviewsMethod {
        @Test
        @DisplayName("Test should return empty list of reviews for existent user without reviews")
        void test_getUserReviews_willReturnEmptyListOfReviewsForExistentUserWithoutReviews() {
            String userId = "userId";
            User user = new User(userId, "userName", "email", "phoneNumber");

            when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
            when(mockReviewRepository.findAllByUser(user)).thenReturn(new ArrayList<>());

            List<ReviewDTO> actualReviewDTOs = reviewServiceUnderTest.getUserReviews(userId);

            Assertions.assertTrue(actualReviewDTOs.isEmpty());
        }

        @Test
        @DisplayName("Test should return list of reviews for existent user with reviews")
        void test_getUserReviews_willReturnListOfReviewsForExistentUserWithReviews() {
            String userId = "userId";
            User user = new User(userId, "userName", "email", "phoneNumber");
            List<Review> existentReviews = new ArrayList<>();
            Review review1 = new Review("reviewId1", "title1", "description1", 5.0);
            review1.setUser(user);
            review1.setBook(new Book("bookId1", "title1", "author1", "publisher1", "isbn1", LocalDate.now()));
            existentReviews.add(review1);
            Review review2 = new Review("reviewId2", "title2", "description2", 5.0);
            review2.setUser(user);
            review2.setBook(new Book("bookId2", "title2", "author2", "publisher2", "isbn2", LocalDate.now()));
            existentReviews.add(review2);
            List<ReviewDTO> expectedReviewDTOs = new ArrayList<>();
            expectedReviewDTOs.add(new ReviewDTO(review1, false, true));
            expectedReviewDTOs.add(new ReviewDTO(review2, false, true));

            when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
            when(mockReviewRepository.findAllByUser(user)).thenReturn(existentReviews);

            List<ReviewDTO> actualReviewDTOs = reviewServiceUnderTest.getUserReviews(userId);

            Assertions.assertEquals(expectedReviewDTOs, actualReviewDTOs);
        }

        @Test
        @DisplayName("Test should throw entity not found exception for nonexistent user")
        void test_getUserReviews_willThrowEntityNotFoundExceptionForNonexistentUser() {
            String userId = "userId";

            when(mockUserRepository.findById(userId)).thenReturn(Optional.empty());

            EntityNotFoundException actualException = Assertions.assertThrows(EntityNotFoundException.class,
                    () -> reviewServiceUnderTest.getUserReviews(userId));
            Assertions.assertEquals("Entity: user was not found", actualException.getMessage());
        }
    }
}