package ro.unibuc.hello.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReviewCreationRequestDto {
    private int rating;
    private String reviewBody;
    private String readingRecordId;
}
