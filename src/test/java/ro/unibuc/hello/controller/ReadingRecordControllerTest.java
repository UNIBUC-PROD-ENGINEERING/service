package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.dto.ReadingRecordCreationRequestDto;
import ro.unibuc.hello.service.ReadingRecordService;
import static org.mockito.ArgumentMatchers.eq;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
public class ReadingRecordControllerTest {

        @Mock
        private ReadingRecordService readingRecordService;

        @InjectMocks
        private ReadingRecordController readingRecordController;

        private MockMvc mockMvc;

        private ObjectMapper objectMapper;

        private ReadingRecordEntity readingRecord;

        private ReaderEntity reader;

        private BookEntity book;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(readingRecordController).build();
                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());

                reader = ReaderEntity.builder()
                                .readerId("65fbeeb6748071114eae8d5c")
                                .name("John Doe")
                                .nationality("American")
                                .phoneNumber("1234567890")
                                .birthDate(LocalDate.of(1980, 1, 1))
                                .registrationDate(LocalDate.now())
                                .build();

                book = BookEntity.builder()
                                .bookId("65fbeeb5748071114eae8d59")
                                .title("Example Book")
                                .genre("Fiction")
                                .publicationDate(LocalDate.of(2020, 1, 1))
                                .publisher("Example Publisher")
                                .build();

                readingRecord = ReadingRecordEntity.builder()
                                .readingRecordId("65fbeeb5748071114fff8d59")
                                .book(book)
                                .reader(reader)
                                .dateStarted(LocalDate.now())
                                .build();

        }

        @Test
        void test_PostReadingRecord() throws Exception {
                // Given
                var creationRequestDto = ReadingRecordCreationRequestDto.builder()
                                .bookId("65fbeeb5748071114eae8d59")
                                .readerId("65fbeeb5748071114fff8d59")
                                .build();

                // when(readingRecordService.saveReadingRecord(creationRequestDto)).thenReturn(record);
                when(readingRecordService.saveReadingRecord(any())).thenReturn(readingRecord);

                // When
                var result = mockMvc.perform(post("/readingrecords")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creationRequestDto)))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals(objectMapper.writeValueAsString(readingRecord),
                                result.getResponse().getContentAsString());
        }

        @Test
        void test_GetReadingRecords() throws Exception {
                // Given
                var readingRecordsList = List.of(readingRecord);
                when(readingRecordService.getAllReadingRecords()).thenReturn(readingRecordsList);

                // When
                var result = mockMvc.perform(get("/readingrecords"))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals(objectMapper.writeValueAsString(readingRecordsList),
                                result.getResponse().getContentAsString());
        }
}
