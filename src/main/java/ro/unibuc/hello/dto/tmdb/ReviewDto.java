package ro.unibuc.hello.dto.tmdb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private String username;
    private String movieId;
    private int rating;
    private String comment;
    // No need for createdAt field since it will be set when saving the review.
}

