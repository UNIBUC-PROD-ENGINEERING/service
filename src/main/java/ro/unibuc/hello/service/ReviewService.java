package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.entity.Movie;
import ro.unibuc.hello.data.entity.Review;
import ro.unibuc.hello.data.repository.MovieRepository;
import ro.unibuc.hello.data.repository.ReviewRepository;
import ro.unibuc.hello.dto.tmdb.ReviewDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<Review> getReviewsByMovieId(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + movieId));
        return reviewRepository.findByMovieId(movie.getId());
    }

    public Review addReview(String movieId, ReviewDto reviewDto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + movieId));

        Review review = new Review();
        review.setMovieId(movie.getId());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        return reviewRepository.save(review);
    }
}
