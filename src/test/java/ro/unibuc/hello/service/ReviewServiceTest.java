import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.service.ReviewService;
import ro.unibuc.hello.data.BookingEntity; 
import ro.unibuc.hello.repository.ReviewRepository;
import ro.unibuc.hello.repository.BookingRepository;

import java.util.*;
import org.springframework.data.domain.Sort;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewEntity review;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        review = new ReviewEntity("1", 4, "apartment123", "user123");
        review.setLikes(new HashSet<>());
        review.setDislikes(new HashSet<>());
    }

    @Test
    public void testCreateReview_Success() {
        // Mocks
        when(bookingRepository.findByApartmentIdAndUserId(anyString(), anyString()))
            .thenReturn(Collections.singletonList(new BookingEntity()));  // Folosește BookingEntity în loc de Object

        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(review);
        ReviewEntity createdReview = reviewService.createReview(review);

        assertEquals(review.getId(), createdReview.getId());
        verify(reviewRepository, times(1)).save(any(ReviewEntity.class));
    }

    @Test
    public void testAddLike_Success() {
        Set<String> likes = new HashSet<>();
        review.setLikes(likes);  // Folosim HashSet<String> pentru likes
        when(reviewRepository.findById(anyString())).thenReturn(Optional.of(review));

        String response = reviewService.addLike("1", "userId123");
        
        assertEquals("Like added successfully!", response);
        assertTrue(likes.contains("userId123"));
    }

    @Test
    public void testAddDislike_Success() {
        Set<String> dislikes = new HashSet<>();
        review.setDislikes(dislikes);  // Folosim HashSet<String> pentru dislikes
        when(reviewRepository.findById(anyString())).thenReturn(Optional.of(review));

        String response = reviewService.addDislike("1", "userId123");
        
        assertEquals("Dislike added successfully!", response);
        assertTrue(dislikes.contains("userId123"));
    }

    @Test
    public void testGetAllReviews_Success() {
        List<ReviewEntity> reviews = Arrays.asList(
            new ReviewEntity("1", 4, "apartment123", "user123"),
            new ReviewEntity("2", 2, "apartment456", "user456")
        );

        when(reviewRepository.findAll()).thenReturn(reviews);

        List<ReviewEntity> result = reviewService.getAllReviews();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
public void testGetGoodReviews_Success() {
    List<ReviewEntity> reviews = Arrays.asList(
        new ReviewEntity("1", 4, "apartment123", "user123"),
        new ReviewEntity("2", 2, "apartment456", "user456")
    );

    // Mock findByRatingGreaterThan pentru rating > 3
    when(reviewRepository.findByRatingGreaterThan(3, Sort.by(Sort.Order.desc("rating"))))
        .thenReturn(Collections.singletonList(reviews.get(0))); // doar review cu rating 4

    List<ReviewEntity> goodReviews = reviewService.getGoodReviews();

    assertNotNull(goodReviews);
    assertEquals(1, goodReviews.size());
    assertEquals(4, goodReviews.get(0).getRating());  // Verifică rating-ul 4
}

@Test
public void testGetBadReviews_Success() {
    List<ReviewEntity> reviews = Arrays.asList(
        new ReviewEntity("1", 4, "apartment123", "user123"),
        new ReviewEntity("2", 2, "apartment456", "user456")
    );

    // Mock findByRatingLessThanEqual pentru rating <= 3
    when(reviewRepository.findByRatingLessThanEqual(3, Sort.by(Sort.Order.asc("rating"))))
        .thenReturn(Collections.singletonList(reviews.get(1))); // doar review cu rating 2

    List<ReviewEntity> badReviews = reviewService.getBadReviews();

    assertNotNull(badReviews);
    assertEquals(1, badReviews.size());
    assertEquals(2, badReviews.get(0).getRating());  // Verifică rating-ul 2
}
@Test
public void testCreateReview_InvalidRating() {
    // Crează un review cu rating invalid (mai mic de 1)
    ReviewEntity invalidReview = new ReviewEntity("1", 0, "apartment123", "user123");

    // Verifică dacă se aruncă o excepție pentru rating invalid
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        reviewService.createReview(invalidReview);
    });
    
    assertEquals("Rating must be between 1 and 5", exception.getMessage());
}
@Test
public void testCreateReview_NoRating() {
    // Crează un review fără rating
    ReviewEntity invalidReview = new ReviewEntity("1", null, "apartment123", "user123");

    // Verifică dacă se aruncă o excepție pentru rating null
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        reviewService.createReview(invalidReview);
    });

    assertEquals("Rating is required", exception.getMessage());
}
@Test
public void testCreateReview_UserNotBookedApartment() {
    // Crează un review valid pentru un utilizator care nu a făcut un booking
    ReviewEntity validReview = new ReviewEntity("1", 4, "apartment123", "user123");

    // Mocks
    when(bookingRepository.findByApartmentIdAndUserId(anyString(), anyString()))
            .thenReturn(Collections.emptyList()); // Utilizatorul nu a făcut niciun booking

    // Verifică dacă se aruncă excepția corespunzătoare
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        reviewService.createReview(validReview);
    });

    assertEquals("User must have booked the apartment before leaving a review.", exception.getMessage());
}
@Test
public void testAddLike_RemoveDislike() {
    Set<String> likes = new HashSet<>();
    Set<String> dislikes = new HashSet<>();
    review.setLikes(likes);
    review.setDislikes(dislikes);

    // Adaugă un dislike înainte de a da un like
    dislikes.add("user123");

    when(reviewRepository.findById(anyString())).thenReturn(Optional.of(review));

    // Dă un like la review
    String response = reviewService.addLike("1", "user123");

    assertEquals("Like added successfully!", response);
    assertTrue(likes.contains("user123"));
    assertFalse(dislikes.contains("user123"));  // Verifică că dislike-ul a fost eliminat
}
@Test
public void testAddDislike_RemoveLike() {
    Set<String> likes = new HashSet<>();
    Set<String> dislikes = new HashSet<>();
    review.setLikes(likes);
    review.setDislikes(dislikes);

    // Adaugă un like înainte de a da un dislike
    likes.add("user123");

    when(reviewRepository.findById(anyString())).thenReturn(Optional.of(review));

    // Dă un dislike la review
    String response = reviewService.addDislike("1", "user123");

    assertEquals("Dislike added successfully!", response);
    assertTrue(dislikes.contains("user123"));
    assertFalse(likes.contains("user123"));  // Verifică că like-ul a fost eliminat
}
@Test
public void testDeleteReview_Success() {
    // Mocks
    doNothing().when(reviewRepository).deleteById(anyString());

    reviewService.deleteReview("1");

    verify(reviewRepository, times(1)).deleteById("1");
}
@Test
public void testRemoveLike_Success() {
    Set<String> likes = new HashSet<>();
    review.setLikes(likes);

    likes.add("user123");

    when(reviewRepository.findById(anyString())).thenReturn(Optional.of(review));

    String response = reviewService.removeReaction("1", "user123");

    assertEquals("Like removed successfully!", response);
    assertFalse(likes.contains("user123"));
}

@Test
public void testRemoveDislike_Success() {
    Set<String> dislikes = new HashSet<>();
    review.setDislikes(dislikes);

    dislikes.add("user123");

    when(reviewRepository.findById(anyString())).thenReturn(Optional.of(review));

    String response = reviewService.removeReaction("1", "user123");

    assertEquals("Dislike removed successfully!", response);
    assertFalse(dislikes.contains("user123"));
}
@Test
public void testAddLike_ReviewNotFound() {
    when(reviewRepository.findById(anyString())).thenReturn(Optional.empty());

    String response = reviewService.addLike("nonexistent", "user123");

    assertEquals("Review not found!", response);
}

}
