package ro.unibuc.hello.data.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    
    @Id private String id;
    private String userId;
    private String movieId;
    private int rating; // 0 to 5
    private String comment;
    private Instant createdAt;

    // Getters, setters, and constructors
}

