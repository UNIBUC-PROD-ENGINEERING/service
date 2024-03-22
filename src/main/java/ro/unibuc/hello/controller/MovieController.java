package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.unibuc.hello.data.entity.Movie;
import ro.unibuc.hello.data.entity.Review;
import ro.unibuc.hello.dto.tmdb.MovieApiDto;
import ro.unibuc.hello.dto.tmdb.ReviewDto;
import ro.unibuc.hello.service.MovieService;
import ro.unibuc.hello.service.ReviewService;


import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired private MovieService movieService;

    @Autowired
    private ReviewService reviewService; 

    @GetMapping("/search")
    @ResponseBody
    public List<MovieApiDto> searchMovies(@RequestParam(name="name", defaultValue="") String name) {
        return movieService.searchMovie(name);
    }

    @GetMapping
    @ResponseBody
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Movie getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    @PostMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addMovie(@PathVariable Long id) {
        return movieService.addMovie(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable String id) {

        movieService.deleteMovie(id);
    }


    // Get all reviews for a movie
    @GetMapping("/{id}/reviews")
    @ResponseBody
    public List<Review> getMovieReviews(@PathVariable String id) {
        return reviewService.getReviewsByMovieId(id);
    }

    // Add a review for a movie
    @PostMapping("/{movieId}/reviews/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReview(@PathVariable String movieId, @RequestBody ReviewDto reviewDto) {
        return reviewService.addReview(movieId, reviewDto);
    }

}
