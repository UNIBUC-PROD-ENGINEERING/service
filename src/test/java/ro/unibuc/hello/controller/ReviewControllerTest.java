import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.controller.ReviewController;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.service.ReviewService;
import ro.unibuc.hello.repository.BookingRepository; 
import ro.unibuc.hello.data.BookingEntity; 

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class ReviewControllerTest {

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
    public void testGetReviewById_Success() throws Exception {
        // Arrange
        when(reviewService.getReviewById("1")).thenReturn(Optional.of(review));

        // Act & Assert
        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("Great apartment!"))
                .andExpect(jsonPath("$.apartmentId").value("apartment123"))
                .andExpect(jsonPath("$.userId").value("user123"));
    }

    @Test
    void testAddLike_Success() throws Exception {
        Set<String> likes = new HashSet<>();
        review.setLikes(likes);
        when(reviewService.addLike("1", "user123")).thenReturn("Like added successfully!");

        mockMvc.perform(post("/reviews/1/like")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Like added successfully!"));

        verify(reviewService, times(1)).addLike("1", "user123");
    }

    @Test
    void testAddDislike_Success() throws Exception {
        Set<String> dislikes = new HashSet<>();
        review.setDislikes(dislikes);
        when(reviewService.addDislike("1", "user123")).thenReturn("Dislike added successfully!");

        mockMvc.perform(post("/reviews/1/dislike")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dislike added successfully!"));

        verify(reviewService, times(1)).addDislike("1", "user123");
    }

    @Test
    void testGetGoodReviews_Success() throws Exception {
        List<ReviewEntity> reviews = Arrays.asList(new ReviewEntity("1", 4, "apartment123", "user123"));
        when(reviewService.getGoodReviews()).thenReturn(reviews);

        mockMvc.perform(get("/reviews/good"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(4));

        verify(reviewService, times(1)).getGoodReviews();
    }

    @Test
    void testGetBadReviews_Success() throws Exception {
        List<ReviewEntity> reviews = Arrays.asList(new ReviewEntity("1", 2, "apartment123", "user123"));
        when(reviewService.getBadReviews()).thenReturn(reviews);

        mockMvc.perform(get("/reviews/bad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(2));

        verify(reviewService, times(1)).getBadReviews();
    }

@Test
void testDeleteReview_Success() throws Exception {
    doNothing().when(reviewService).deleteReview("1");

    mockMvc.perform(delete("/reviews/1"))
            .andExpect(status().isOk());

    verify(reviewService, times(1)).deleteReview("1");
}
@Test
void testGetAllReviewsSortedByRating_Success() throws Exception {
    List<ReviewEntity> reviews = Arrays.asList(
            new ReviewEntity("1", "Nice place", 5, "apartment123", "user123"),
            new ReviewEntity("2", "Good apartment", 3, "apartment124", "user123")
    );
    when(reviewService.getAllReviewsSortedByRating()).thenReturn(reviews);

    mockMvc.perform(get("/reviews"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].rating").value(5))  // Ensure highest rating is first
            .andExpect(jsonPath("$[1].rating").value(3));

    verify(reviewService, times(1)).getAllReviewsSortedByRating();
}
@Test
void testRemoveReaction_Success() throws Exception {
    when(reviewService.removeReaction("1", "user123")).thenReturn("Like removed successfully!");

    mockMvc.perform(post("/reviews/1/removeReaction")
            .param("userId", "user123"))
            .andExpect(status().isOk())
            .andExpect(content().string("Like removed successfully!"));

    verify(reviewService, times(1)).removeReaction("1", "user123");
}


}
