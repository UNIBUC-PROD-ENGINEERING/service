import com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.service.ReviewService;
import ro.unibuc.hello.repository.BookingRepository;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.controller.ReviewController;
import ro.unibuc.hello.service.ReviewService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import java.util.Set;
import java.util.HashSet;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReviewControllerIntegrationTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private BookingRepository bookingRepository;

    private MockMvc mockMvc;

    private ReviewEntity review;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();

        // Setup ReviewEntity correctly
        review = new ReviewEntity("1", "Great apartment!", 4, "apartment123", "user123");
        review.setLikes(new HashSet<>());
        review.setDislikes(new HashSet<>());
    }

    @Test
    public void testCreateReview_Success() throws Exception {
        // Mocks
        when(bookingRepository.findByApartmentIdAndUserId("apartment123", "user123"))
            .thenReturn(Arrays.asList(new BookingEntity(
                LocalDate.of(2025, 1, 1),  // Start date
                LocalDate.of(2025, 1, 7),  // End date
                "apartment123",            // Apartment ID
                "user123"                  // User ID
            )));  // Simulating a valid booking

        when(reviewService.createReview(any(ReviewEntity.class))).thenReturn(review);

        // Act & Assert: Send a POST request to create the review and check the response
        mockMvc.perform(post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(review)))
                .andExpect(status().isCreated())  // Expecting HTTP status 201 (Created)
                .andExpect(content().string("Review successfully created!"));  // Checking if the message matches

        // Verify that the createReview method was called once with the correct parameters
        verify(reviewService, times(1)).createReview(any(ReviewEntity.class));
    }

    @Test
    public void testCreateReview_UserMustHaveBooked() throws Exception {
        // Mocks: No booking for the user
        when(bookingRepository.findByApartmentIdAndUserId("apartment123", "user123"))
            .thenReturn(Arrays.asList());  // No booking found

        // Act & Assert: Send a POST request to create the review and check the response
        mockMvc.perform(post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(review)))
                .andExpect(status().isBadRequest())  // Expecting HTTP status 400 (Bad Request)
                .andExpect(content().string("User must have booked the apartment before leaving a review."));  // Error message for user who didn't book
    }

    @Test
    public void testGetAllReviews_Success() throws Exception {
        // Simulate reviews being available
        when(reviewService.getAllReviewsSortedByRating()).thenReturn(Arrays.asList(review));

        // Act & Assert: Send a GET request to fetch all reviews and check the response
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())  // Expecting HTTP status 200 (OK)
                .andExpect(jsonPath("$.length()").value(1))  // Only 1 review should be present
                .andExpect(jsonPath("$[0].content").value("Great apartment!"));  // Checking the content of the review
    }

    @Test
    public void testAddLike_AfterAddDislike_ShouldRemoveDislike() throws Exception {
        // Setup: Set initial dislike
        Set<String> dislikes = new HashSet<>();
        dislikes.add("user123");
        review.setDislikes(dislikes);

        // Mocks: Simulate that the review is present and user has added a dislike
        when(reviewService.addDislike("1", "user123")).thenReturn("Dislike added successfully!");
        when(reviewService.addLike("1", "user123")).thenReturn("Like added successfully!");

        // Add Dislike
        mockMvc.perform(post("/reviews/1/dislike")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dislike added successfully!"));

        // Verify that dislike was added
        assertTrue(dislikes.contains("user123"));
        
        // Add Like after Dislike
        mockMvc.perform(post("/reviews/1/like")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Like added successfully!"));

        // Verify that the dislike is removed and like is added
        assertTrue(review.getLikes().contains("user123"));
        assertFalse(review.getDislikes().contains("user123"));

        // Verify service methods were called
        verify(reviewService, times(1)).addDislike("1", "user123");
        verify(reviewService, times(1)).addLike("1", "user123");
    }
}
