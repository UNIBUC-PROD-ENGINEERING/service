package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.BookDTO;
import com.bookstore.v1.dto.ReviewCreationDTO;
import com.bookstore.v1.dto.ReviewDTO;
import com.bookstore.v1.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Tag("IT")
@DisplayName("Review Service Integration Test")
public class ReviewServiceTestIT {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewService reviewService;

    User user;
    Book book;
    String reviewId;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("username", "email", "phoneNumber"));
        book = bookRepository.save(new Book("title", "author", "publisher", "isbn", LocalDate.now()));
    }

    @AfterEach
    void tearDown() {
        if (reviewId != null) {
            reviewRepository.deleteById(reviewId);
        }
        bookRepository.delete(book);
        userRepository.delete(user);
    }

    @Test
    @DisplayName("Test should add review for valid review creation dto")
    void test_addReview_shouldAddReviewForValidReviewCreationDto() {
        ReviewCreationDTO reviewCreationDto = new ReviewCreationDTO("title", "description", 5.0, user.getId(),
                book.getId());

        ReviewDTO actualReviewDTO = reviewService.addReview(reviewCreationDto);

        Assertions.assertNotNull(actualReviewDTO.getId());
        Assertions.assertEquals(reviewCreationDto.getTitle(), actualReviewDTO.getTitle());
        Assertions.assertEquals(reviewCreationDto.getDescription(), actualReviewDTO.getDescription());
        Assertions.assertEquals(reviewCreationDto.getRating(), actualReviewDTO.getRating());
        Assertions.assertEquals(reviewCreationDto.getUserId(), actualReviewDTO.getUserId());
        Assertions.assertEquals(reviewCreationDto.getBookId(), actualReviewDTO.getBookId());
        Assertions.assertEquals(new UserDTO(user), actualReviewDTO.getUser());
        Assertions.assertEquals(new BookDTO(book), actualReviewDTO.getBook());

        reviewId = actualReviewDTO.getId();
    }

    @Test
    @DisplayName("Test should uodate review for valid review creation dto")
    void test_updateReview_shouldUpdateReviewForValidReviewCreationDto() {
        ReviewCreationDTO reviewCreationDto = new ReviewCreationDTO("title", "description", 5.0, user.getId(),
                book.getId());
        ReviewDTO createdReviewDTO = reviewService.addReview(reviewCreationDto);
        reviewCreationDto.setId(createdReviewDTO.getId());
        reviewCreationDto.setTitle("new title");
        reviewCreationDto.setDescription("new description");
        reviewCreationDto.setRating(4.0);

        ReviewDTO updatedReviewDTO = reviewService.updateReview(reviewCreationDto);

        Assertions.assertEquals(reviewCreationDto.getId(), updatedReviewDTO.getId());
        Assertions.assertEquals(reviewCreationDto.getTitle(), updatedReviewDTO.getTitle());
        Assertions.assertEquals(reviewCreationDto.getDescription(), updatedReviewDTO.getDescription());
        Assertions.assertEquals(reviewCreationDto.getRating(), updatedReviewDTO.getRating());
        Assertions.assertEquals(reviewCreationDto.getUserId(), updatedReviewDTO.getUserId());
        Assertions.assertEquals(reviewCreationDto.getBookId(), updatedReviewDTO.getBookId());
        Assertions.assertEquals(new UserDTO(user), updatedReviewDTO.getUser());
        Assertions.assertEquals(new BookDTO(book), updatedReviewDTO.getBook());

        reviewId = updatedReviewDTO.getId();
    }

    @Test
    @DisplayName("Test should delete review for valid review id")
    void test_deleteReview_shouldDeleteReviewForValidReviewId() {
        ReviewCreationDTO reviewCreationDto = new ReviewCreationDTO("title", "description", 5.0, user.getId(),
                book.getId());
        ReviewDTO createdReviewDTO = reviewService.addReview(reviewCreationDto);
        String reviewId = createdReviewDTO.getId();

        reviewService.deleteReviewById(reviewId);

        Assertions.assertFalse(reviewRepository.existsById(reviewId));
    }

    @Test
    @DisplayName("Test should get review for valid review id")
    void test_getReview_shouldGetReviewForValidReviewId() {
        ReviewCreationDTO reviewCreationDto = new ReviewCreationDTO("title", "description", 5.0, user.getId(),
                book.getId());
        ReviewDTO createdReviewDTO = reviewService.addReview(reviewCreationDto);

        ReviewDTO actualReviewDTO = reviewService.getReviewById(createdReviewDTO.getId());

        Assertions.assertEquals(createdReviewDTO.getId(), actualReviewDTO.getId());
        Assertions.assertEquals(createdReviewDTO.getTitle(), actualReviewDTO.getTitle());
        Assertions.assertEquals(createdReviewDTO.getDescription(), actualReviewDTO.getDescription());
        Assertions.assertEquals(createdReviewDTO.getRating(), actualReviewDTO.getRating());
        Assertions.assertEquals(createdReviewDTO.getUserId(), actualReviewDTO.getUserId());
        Assertions.assertEquals(createdReviewDTO.getBookId(), actualReviewDTO.getBookId());
        Assertions.assertEquals(new UserDTO(user), actualReviewDTO.getUser());
        Assertions.assertEquals(new BookDTO(book), actualReviewDTO.getBook());

        reviewId = actualReviewDTO.getId();
    }

    @Test
    @DisplayName("Test should return list of reviews with books and users")
    void test_getReviews_shouldReturnListOfReviewsWithBooksAndUsers() {
        ReviewCreationDTO reviewCreationDto = new ReviewCreationDTO("title", "description", 5.0, user.getId(),
                book.getId());
        ReviewDTO createdReviewDTO = reviewService.addReview(reviewCreationDto);

        List<ReviewDTO> actualReviewDTOs = reviewService.getReviews();

        ReviewDTO actualReviewDTO = actualReviewDTOs
                .stream()
                .filter((reviewDTO) -> reviewDTO.getId().equals(createdReviewDTO.getId()))
                .collect(Collectors.toList())
                .get(0);

        Assertions.assertEquals(createdReviewDTO.getId(), actualReviewDTO.getId());
        Assertions.assertEquals(createdReviewDTO.getTitle(), actualReviewDTO.getTitle());
        Assertions.assertEquals(createdReviewDTO.getDescription(), actualReviewDTO.getDescription());
        Assertions.assertEquals(createdReviewDTO.getRating(), actualReviewDTO.getRating());
        Assertions.assertEquals(createdReviewDTO.getUserId(), actualReviewDTO.getUserId());
        Assertions.assertEquals(createdReviewDTO.getBookId(), actualReviewDTO.getBookId());
        Assertions.assertEquals(new UserDTO(user), actualReviewDTO.getUser());
        Assertions.assertEquals(new BookDTO(book), actualReviewDTO.getBook());

        reviewId = createdReviewDTO.getId();
    }


    @Test
    @DisplayName("Test should get book reviews for existent book")
    void test_getBookReviews_shouldGetBookReviewsForExistentBook() {
        ReviewCreationDTO reviewCreationDto = new ReviewCreationDTO("title", "description", 5.0, user.getId(),
                book.getId());
        ReviewDTO createdReviewDTO = reviewService.addReview(reviewCreationDto);

        List<ReviewDTO> actualReviewDTOs = reviewService.getBookReviews(book.getId());

        Assertions.assertEquals(1, actualReviewDTOs.size());
        Assertions.assertEquals(createdReviewDTO.getId(), actualReviewDTOs.get(0).getId());
        Assertions.assertEquals(createdReviewDTO.getTitle(), actualReviewDTOs.get(0).getTitle());
        Assertions.assertEquals(createdReviewDTO.getDescription(), actualReviewDTOs.get(0).getDescription());
        Assertions.assertEquals(createdReviewDTO.getRating(), actualReviewDTOs.get(0).getRating());
        Assertions.assertEquals(createdReviewDTO.getUserId(), actualReviewDTOs.get(0).getUserId());
        Assertions.assertEquals(createdReviewDTO.getBookId(), actualReviewDTOs.get(0).getBookId());
        Assertions.assertEquals(new UserDTO(user), actualReviewDTOs.get(0).getUser());
        Assertions.assertNull(actualReviewDTOs.get(0).getBook());

        reviewId = actualReviewDTOs.get(0).getId();
    }

    @Test
    @DisplayName("Test should get user reviews for existent user")
    void test_getUserReviews_shouldGetUserReviewsForExistentUser() {
        ReviewCreationDTO reviewCreationDto = new ReviewCreationDTO("title", "description", 5.0, user.getId(),
                book.getId());
        ReviewDTO createdReviewDTO = reviewService.addReview(reviewCreationDto);

        List<ReviewDTO> actualReviewDTOs = reviewService.getUserReviews(user.getId());

        Assertions.assertEquals(1, actualReviewDTOs.size());
        Assertions.assertEquals(createdReviewDTO.getId(), actualReviewDTOs.get(0).getId());
        Assertions.assertEquals(createdReviewDTO.getTitle(), actualReviewDTOs.get(0).getTitle());
        Assertions.assertEquals(createdReviewDTO.getDescription(), actualReviewDTOs.get(0).getDescription());
        Assertions.assertEquals(createdReviewDTO.getRating(), actualReviewDTOs.get(0).getRating());
        Assertions.assertEquals(createdReviewDTO.getUserId(), actualReviewDTOs.get(0).getUserId());
        Assertions.assertEquals(createdReviewDTO.getBookId(), actualReviewDTOs.get(0).getBookId());
        Assertions.assertEquals(new BookDTO(book), actualReviewDTOs.get(0).getBook());
        Assertions.assertNull(actualReviewDTOs.get(0).getUser());

        reviewId = actualReviewDTOs.get(0).getId();
    }
}
