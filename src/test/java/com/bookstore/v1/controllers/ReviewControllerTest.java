package com.bookstore.v1.controllers;

import com.bookstore.v1.data.Book;
import com.bookstore.v1.data.User;
import com.bookstore.v1.dto.BookDTO;
import com.bookstore.v1.dto.ReviewCreationDTO;
import com.bookstore.v1.dto.ReviewDTO;
import com.bookstore.v1.dto.UserDTO;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
class ReviewControllerTest {
    @Mock
    private ReviewService mockReviewService;
    @InjectMocks
    private ReviewController reviewControllerUnderTest;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewControllerUnderTest).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("Test add review endpoint")
    class TestAddReviewEndpoint {
        @Test
        @DisplayName("Test should create a review for valid request and return it; status code 200")
        void test_addReview_willCreateReviewForValidRequestAndReturnIt_withStatusCode200() throws Exception {
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 5.0, "userId",
                    "bookId");
            ReviewDTO expectedReviewDTO = new ReviewDTO("reviewId", "title", "description", 5.0, "userId", "bookId");
            expectedReviewDTO.setUser(new UserDTO(new User("userId", "username", "email", "phoneNumber")));
            expectedReviewDTO.setBook(
                    new BookDTO(new Book("bookId", "title", "author", "publisher", "isbn", LocalDate.now())));

            when(mockReviewService.addReview(reviewCreationDTO)).thenReturn(expectedReviewDTO);

            MvcResult actualResult = mockMvc
                    .perform(post("/review/add-review")
                            .content(objectMapper.writeValueAsString(reviewCreationDTO))
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(expectedReviewDTO),
                    actualResult.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Test should return empty field exception for missing title; status code 400")
        void test_addReview_willReturnEmptyFieldExceptionForNullTitle_withStatusCode400() throws Exception {
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO(null, "description", 5.0, "userId", "bookId");

            when(mockReviewService.addReview(reviewCreationDTO)).thenThrow(new EmptyFieldException("title"));

            mockMvc
                    .perform(post("/review/add-review")
                            .content(objectMapper.writeValueAsString(reviewCreationDTO))
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EmptyFieldException))
                    .andExpect(result -> Assertions.assertEquals("Field: title is empty",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }

        @Test
        @DisplayName("Test should return invalid double range exception for invalid rating; status code 400")
        void test_addReview_willReturnInvalidDoubleRangeExceptionForInvalidRating_withStatusCode400() throws Exception {
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 6.0, "userId",
                    "bookId");

            when(mockReviewService.addReview(reviewCreationDTO)).thenThrow(new InvalidDoubleRange("rating", 0.0, 5.0));

            mockMvc
                    .perform(post("/review/add-review")
                            .content(objectMapper.writeValueAsString(reviewCreationDTO))
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof InvalidDoubleRange))
                    .andExpect(result -> Assertions.assertEquals("Field: rating must be between 0.000000 and 5.000000",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));

        }

        @Test
        @DisplayName("Test should return entity not found exception for nonexistent user; status code 400")
        void test_addReview_willThrowEntityNotFoundExceptionForNonexistentUser_withStatusCode400() throws Exception {
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 5.0, "userId",
                    "bookId");

            when(mockReviewService.addReview(reviewCreationDTO)).thenThrow(new EntityNotFoundException("user"));

            mockMvc
                    .perform(post("/review/add-review")
                            .content(objectMapper.writeValueAsString(reviewCreationDTO))
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EntityNotFoundException))
                    .andExpect(result -> Assertions.assertEquals("Entity: user was not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }

        @Test
        @DisplayName("Test should return entity not found exception for nonexistent book; status code 400")
        void test_addReview_willThrowEntityNotFoundExceptionForNonexistentBook_withStatusCode400() throws Exception {
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 5.0, "userId",
                    "bookId");

            when(mockReviewService.addReview(reviewCreationDTO)).thenThrow(new EntityNotFoundException("book"));

            mockMvc
                    .perform(post("/review/add-review")
                            .content(objectMapper.writeValueAsString(reviewCreationDTO))
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EntityNotFoundException))
                    .andExpect(result -> Assertions.assertEquals("Entity: book was not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }

        @Test
        @DisplayName("Test should return duplicate object exception for existing review by user for book; status code 400")
        void test_addReview_willThrowDuplicateObjectExceptionForExistingReviewByUserForBook_withStatusCode400() throws
                Exception {
            ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO("title", "description", 5.0, "userId",
                    "bookId");

            when(mockReviewService.addReview(reviewCreationDTO)).thenThrow(new DuplicateObjectException("review"));

            mockMvc
                    .perform(post("/review/add-review")
                            .content(objectMapper.writeValueAsString(reviewCreationDTO))
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof DuplicateObjectException))
                    .andExpect(result -> Assertions.assertEquals("Object: review already exists",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }
    }

    @Nested
    @DisplayName("Test update review endpoint")
    class TestUpdateReviewEndpoint {
        @Test
        @DisplayName("Test should update a review for valid request and return it; status code 200")
        void test_updateReview_willUpdateReviewForValidRequestAndReturnIt_withStatusCode200() throws Exception {
            ReviewCreationDTO reviewUpdateDTO = new ReviewCreationDTO("reviewId", "title", "description", 5.0, null,
                    null);
            ReviewDTO expectedReviewDTO = new ReviewDTO("reviewId", "title", "description", 5.0, "userId", "bookId");
            expectedReviewDTO.setUser(new UserDTO(new User("userId", "username", "email", "phoneNumber")));
            expectedReviewDTO.setBook(
                    new BookDTO(new Book("bookId", "title", "author", "publisher", "isbn", LocalDate.now())));

            when(mockReviewService.updateReview(reviewUpdateDTO)).thenReturn(expectedReviewDTO);

            MvcResult actualResult = mockMvc
                    .perform(put("/review/update-review")
                            .content(objectMapper.writeValueAsString(reviewUpdateDTO))
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(expectedReviewDTO),
                    actualResult.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Test should return empty field exception for missing review id; status code 400")
        void test_updateReview_willThrowEmptyFieldExceptionForNullReviewId_withStatusCode400() throws Exception {
            ReviewCreationDTO reviewUpdateDTO = new ReviewCreationDTO(null, "title", "description", 5.0, null, null);

            when(mockReviewService.updateReview(reviewUpdateDTO)).thenThrow(new EmptyFieldException("reviewId"));

            mockMvc
                    .perform(put("/review/update-review")
                            .content(objectMapper.writeValueAsString(reviewUpdateDTO))
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EmptyFieldException))
                    .andExpect(result -> Assertions.assertEquals("Field: reviewId is empty",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }

        @Test
        @DisplayName("Test should return invalid double range for invalid rating; status code 400")
        void test_updateReview_willThrowInvalidDoubleRangeExceptionForInvalidRating_withStatusCode400() throws
                Exception {
            ReviewCreationDTO reviewUpdateDTO = new ReviewCreationDTO("reviewId", "title", "description", 6.0, null,
                    null);

            when(mockReviewService.updateReview(reviewUpdateDTO)).thenThrow(new InvalidDoubleRange("rating", 0.0, 5.0));

            mockMvc
                    .perform(put("/review/update-review")
                            .content(objectMapper.writeValueAsString(reviewUpdateDTO))
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof InvalidDoubleRange))
                    .andExpect(result -> Assertions.assertEquals("Field: rating must be between 0.000000 and 5.000000",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }

        @Test
        @DisplayName("Test should return entity not found exception for nonexistent review; status code 400")
        void test_updateReview_willThrowEntityNotFoundExceptionForNonexistentReview_withStatusCode400() throws
                Exception {
            ReviewCreationDTO reviewUpdateDTO = new ReviewCreationDTO("reviewId", "title", "description", 5.0, null,
                    null);

            when(mockReviewService.updateReview(reviewUpdateDTO)).thenThrow(new EntityNotFoundException("review"));

            mockMvc
                    .perform(put("/review/update-review")
                            .content(objectMapper.writeValueAsString(reviewUpdateDTO))
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EntityNotFoundException))
                    .andExpect(result -> Assertions.assertEquals("Entity: review was not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }
    }

    @Nested
    @DisplayName("Test delete review endpoint")
    class TestDeleteReviewEndpoint {
        @Test
        @DisplayName("Test should delete a review for valid request; status code 200")
        void test_deleteReview_willDeleteReviewForValidRequest_withStatusCode200() throws Exception {
            String reviewId = "reviewId";

            doNothing().when(mockReviewService).deleteReviewById(reviewId);

            mockMvc
                    .perform(delete("/review/delete-review/{reviewId}", reviewId))
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        @DisplayName("Test should return entity not found exception for nonexistent review; status code 400")
        void test_deleteReview_willThrowEntityNotFoundExceptionForNonexistentReview_withStatusCode400() throws
                Exception {
            String reviewId = "reviewId";

            doThrow(new EntityNotFoundException("review")).when(mockReviewService).deleteReviewById(reviewId);

            mockMvc
                    .perform(delete("/review/delete-review/{reviewId}", reviewId))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EntityNotFoundException))
                    .andExpect(result -> Assertions.assertEquals("Entity: review was not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }
    }

    @Nested
    @DisplayName("Test get reviews by book id endpoint")
    class TestGetReviewByIdEndpoint {
        @Test
        @DisplayName("Test should return the review for existing review; status code 200")
        void test_getReviewById_willReturnReviewForExistingReview_withStatusCode200() throws Exception {
            String reviewId = "reviewId";
            ReviewDTO expectedReviewDTO = new ReviewDTO("reviewId", "title", "description", 5.0, "userId", "bookId");
            expectedReviewDTO.setUser(new UserDTO(new User("userId", "username", "email", "phoneNumber")));
            expectedReviewDTO.setBook(
                    new BookDTO(new Book("bookId", "title", "author", "publisher", "isbn", LocalDate.now())));

            when(mockReviewService.getReviewById(reviewId)).thenReturn(expectedReviewDTO);

            MvcResult actualResult = mockMvc
                    .perform(get("/review/get-review/{reviewId}", reviewId))
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(expectedReviewDTO),
                    actualResult.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Test should return entity not found exception for nonexistent review; status code 400")
        void test_getReviewById_willThrowEntityNotFoundExceptionForNonexistentReview_withStatusCode400() throws
                Exception {
            String reviewId = "reviewId";

            when(mockReviewService.getReviewById(reviewId)).thenThrow(new EntityNotFoundException("review"));

            mockMvc
                    .perform(get("/review/get-review/{reviewId}", reviewId))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EntityNotFoundException))
                    .andExpect(result -> Assertions.assertEquals("Entity: review was not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }
    }

    @Nested
    @DisplayName("Test get reviews endpoint")
    class TestGetReviewsEndpoint {
        @Test
        @DisplayName("Test should return empty list of reviews for nonexistent reviews; status code 200")
        void test_getReviews_willReturnEmptyListForNonexistentReviews_withStatusCode200() throws Exception {
            when(mockReviewService.getReviews()).thenReturn(Collections.emptyList());

            MvcResult actualResult = mockMvc.perform(get("/review/get-reviews")).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(Collections.emptyList()),
                    actualResult.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Test should return list of reviews for existing reviews; status code 200")
        void test_getReviews_willReturnListOfReviewsForExistingReviews_withStatusCode200() throws Exception {
            List<ReviewDTO> expectedReviewDTOList = new ArrayList<>();
            ReviewDTO reviewDTO1 = new ReviewDTO("reviewId", "title", "description", 5.0, "userId", "bookId");
            reviewDTO1.setUser(new UserDTO(new User("userId", "username", "email", "phoneNumber")));
            reviewDTO1.setBook(
                    new BookDTO(new Book("bookId", "title", "author", "publisher", "isbn", LocalDate.now())));
            expectedReviewDTOList.add(reviewDTO1);
            ReviewDTO reviewDTO2 = new ReviewDTO("reviewId2", "title2", "description2", 4.0, "userId2", "bookId2");
            reviewDTO2.setUser(new UserDTO(new User("userId2", "username2", "email2", "phoneNumber2")));
            reviewDTO2.setBook(
                    new BookDTO(new Book("bookId2", "title2", "author2", "publisher2", "isbn2", LocalDate.now())));
            expectedReviewDTOList.add(reviewDTO2);

            when(mockReviewService.getReviews()).thenReturn(expectedReviewDTOList);

            MvcResult actualResult = mockMvc.perform(get("/review/get-reviews")).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(expectedReviewDTOList),
                    actualResult.getResponse().getContentAsString());
        }
    }

    @Nested
    @DisplayName("Test get book reviews endpoint")
    class TestGetBookReviewsEndpoint {
        @Test
        @DisplayName("Test should return empty list of reviews for existent book without reviews; status code 200")
        void test_getBookReviews_willReturnEmptyListForExistentBookWithoutReviews_withStatusCode200() throws Exception {
            String bookId = "bookId";

            when(mockReviewService.getBookReviews(bookId)).thenReturn(Collections.emptyList());

            MvcResult actualResult = mockMvc
                    .perform(get("/review/get-book-reviews/{bookId}", bookId))
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(Collections.emptyList()),
                    actualResult.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Test should return list of reviews for existent book with reviews; status code 200")
        void test_getBookReviews_willReturnListOfReviewsForExistentBookWithReviews_withStatusCode200() throws
                Exception {
            String bookId = "bookId";
            List<ReviewDTO> expectedReviewDTOList = new ArrayList<>();
            ReviewDTO reviewDTO1 = new ReviewDTO("reviewId", "title", "description", 5.0, "userId", "bookId");
            reviewDTO1.setUser(new UserDTO(new User("userId", "username", "email", "phoneNumber")));
            expectedReviewDTOList.add(reviewDTO1);
            ReviewDTO reviewDTO2 = new ReviewDTO("reviewId2", "title2", "description2", 4.0, "userId2", "bookId2");
            reviewDTO2.setUser(new UserDTO(new User("userId2", "username2", "email2", "phoneNumber2")));
            expectedReviewDTOList.add(reviewDTO2);

            when(mockReviewService.getBookReviews(bookId)).thenReturn(expectedReviewDTOList);

            MvcResult actualResult = mockMvc
                    .perform(get("/review/get-book-reviews/{bookId}", bookId))
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(expectedReviewDTOList),
                    actualResult.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Test should return entity not found exception for nonexistent book; status code 400")
        void test_getBookReviews_willThrowEntityNotFoundExceptionForNonexistentBook_withStatusCode400() throws
                Exception {
            String bookId = "bookId";

            when(mockReviewService.getBookReviews(bookId)).thenThrow(new EntityNotFoundException("book"));

            mockMvc
                    .perform(get("/review/get-book-reviews/{bookId}", bookId))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EntityNotFoundException))
                    .andExpect(result -> Assertions.assertEquals("Entity: book was not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }
    }

    @Nested
    @DisplayName("Test get user reviews endpoint")
    class TestGetUserReviewsEndpoint {
        @Test
        @DisplayName("Test should return empty list of reviews for existent user without reviews; status code 200")
        void test_getUserReviews_willReturnEmptyListForExistentUserWithoutReviews_withStatusCode200() throws Exception {
            String userId = "userId";

            when(mockReviewService.getUserReviews(userId)).thenReturn(Collections.emptyList());

            MvcResult actualResult = mockMvc
                    .perform(get("/review/get-user-reviews/{userId}", userId))
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(Collections.emptyList()),
                    actualResult.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Test should return list of reviews for existent user with reviews; status code 200")
        void test_getUserReviews_willReturnListOfReviewsForExistentUserWithReviews_withStatusCode200() throws
                Exception {
            String userId = "userId";
            List<ReviewDTO> expectedReviewDTOList = new ArrayList<>();
            ReviewDTO reviewDTO1 = new ReviewDTO("reviewId", "title", "description", 5.0, "userId", "bookId");
            reviewDTO1.setBook(
                    new BookDTO(new Book("bookId", "title", "author", "publisher", "isbn", LocalDate.now())));
            expectedReviewDTOList.add(reviewDTO1);
            ReviewDTO reviewDTO2 = new ReviewDTO("reviewId2", "title2", "description2", 4.0, "userId2", "bookId2");
            reviewDTO2.setBook(
                    new BookDTO(new Book("bookId2", "title2", "author2", "publisher2", "isbn2", LocalDate.now())));
            expectedReviewDTOList.add(reviewDTO2);

            when(mockReviewService.getUserReviews(userId)).thenReturn(expectedReviewDTOList);

            MvcResult actualResult = mockMvc
                    .perform(get("/review/get-user-reviews/{userId}", userId))
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(expectedReviewDTOList),
                    actualResult.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Test should return entity not found exception for nonexistent user; status code 400")
        void test_getUserReviews_willThrowEntityNotFoundExceptionForNonexistentUser_withStatusCode400() throws
                Exception {
            String userId = "userId";

            when(mockReviewService.getUserReviews(userId)).thenThrow(new EntityNotFoundException("user"));

            mockMvc
                    .perform(get("/review/get-user-reviews/{userId}", userId))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(
                            result.getResolvedException() instanceof EntityNotFoundException))
                    .andExpect(result -> Assertions.assertEquals("Entity: user was not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        }
    }
}