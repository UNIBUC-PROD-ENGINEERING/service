package ro.unibuc.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.ReviewDTO;

@RestController
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/review/getAll")
    @ResponseBody
    public List<ReviewDTO> getReviews() {
        ArrayList<ReviewDTO> reviewDTOs = new ArrayList<>();

        reviewRepository.findAll().forEach(reviewEntity -> reviewDTOs.add(new ReviewDTO(reviewEntity)));
        return reviewDTOs;
    }

    @GetMapping("/review/get")
    @ResponseBody
    public ReviewDTO getReview(@RequestParam (name="id") String id) {
        ReviewEntity review = reviewRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);

        if(review != null)
            return new ReviewDTO(review);
        else
            return null;
    }

    @PostMapping("/review/insert")
    @ResponseBody
    public ReviewDTO insertReview(@RequestParam(name="comment") String comment,
                                     @RequestParam(name="score") Integer score,
                                     @RequestParam(name="movieId") String movieId,
                                     @RequestParam(name="userId") String userId) {
        ReviewEntity review =new ReviewEntity(comment,score);

        if(movieId != null) {
            MovieEntity movie = movieRepository.findById(String.valueOf(new ObjectId(movieId))).orElse(null);

            review.setMovie(movie);
        }
        else
            return null;

        if(userId != null) {
            UserEntity user = userRepository.findById(String.valueOf(new ObjectId(userId))).orElse(null);

            review.setUser(user);
        }
        else
            return null;
        return new ReviewDTO(reviewRepository.save(review));
    }

    @PutMapping("/review/update")
    @ResponseBody
    public ReviewDTO updateReview(@RequestParam(name="id") String id,
                                  @RequestParam(name="comment",required = false) String comment,
                                  @RequestParam(name="score",required = false) Integer score,
                                  @RequestParam(name="movieId",required = false) String movieId,
                                  @RequestParam(name="userId", required = false) String userId)
    {
        ReviewEntity review=reviewRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);

        if(review!=null) {
            if (comment != null)
                review.setComment(comment);
            if (score != null)
                review.setScore(score);
            if(movieId != null) {
                MovieEntity movie = movieRepository.findById(String.valueOf(new ObjectId(movieId))).orElse(null);

                if(movie != null)
                    review.setMovie(movie);
            }

            if(userId != null) {
                UserEntity user= userRepository.findById(String.valueOf(new ObjectId(userId))).orElse(null);

                if(user != null)
                    review.setUser(user);
            }
            return new ReviewDTO(reviewRepository.save(review));
        }
        else
            return null;
    }

    @DeleteMapping("/review/delete")
    @ResponseBody
    public void deleteReview(@RequestParam(name="id") String id){
        reviewRepository.deleteById(String.valueOf(new ObjectId(id)));
    }
}
