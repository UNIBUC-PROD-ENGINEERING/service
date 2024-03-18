package ro.unibuc.hello.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReadingRecordCreationRequestDto {
    private String bookId;
    private String readerId;
}
