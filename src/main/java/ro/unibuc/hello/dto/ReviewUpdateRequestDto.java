package ro.unibuc.hello.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewUpdateRequestDto {
    private int rating;
    private String reviewBody;
}
