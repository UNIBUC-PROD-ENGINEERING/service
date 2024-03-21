package ro.unibuc.hello.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReviewEntity {
    @Id
    private String reviewId;

    private int rating;
    private LocalDate datePosted;
    private String reviewBody;

    @DBRef
    private ReadingRecordEntity readingRecord;
}
