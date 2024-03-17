package ro.unibuc.hello.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BookCreationRequestDto {
    private String title;
    private String genre;
    private LocalDate publicationDate;
    private String publisher;
    private String authorId;
}
