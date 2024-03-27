package ro.unibuc.hello.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {
    @Id
    private String bookId;

    private String title;
    private String genre;
    private LocalDate publicationDate;
    private String publisher;

    @DBRef
    private AuthorEntity author;
}
