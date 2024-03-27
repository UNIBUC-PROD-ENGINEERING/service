package ro.unibuc.hello.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ReadingRecordEntity {
    @Id
    private String readingRecordId;

    @DBRef
    private BookEntity book;

    @DBRef
    private ReaderEntity reader;

    private LocalDate dateStarted;
    private LocalDate dateFinished;

}
