package ro.unibuc.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ReviewRepository;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.dto.ReviewDTO;

@RestController
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/review/getAll")
    @ResponseBody
    public List<ReviewDTO> getReviews(){

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
    public ReviewEntity insertReview(@RequestParam(name="comment")String comment,
                                     @RequestParam(name="score")Integer score){
        return reviewRepository.save(new ReviewEntity(comment,score));
    }

    @PutMapping("/review/update")
    @ResponseBody
    public ReviewDTO updateReview(@RequestParam(name="id")String id,
                                  @RequestParam(name="comment",required = false)String comment,
                                  @RequestParam(name="score",required = false)Integer score)
    {
        ReviewEntity review=reviewRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(review!=null) {
            if (comment != null)
                review.setComment(comment);
            if (score != null)
                review.setScore(score);
            return new ReviewDTO(reviewRepository.save(review));
        }
        else
            return null;
    }

    @DeleteMapping("/review/delete")
    @ResponseBody
    public void deleteReview(@RequestParam(name="id")String id){
        reviewRepository.deleteById(String.valueOf(new ObjectId(id)));
    }
}
