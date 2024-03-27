package ro.unibuc.hello.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.dto.ReaderCreationRequestDto;
import ro.unibuc.hello.dto.ReaderUpdateRequestDto;
import ro.unibuc.hello.service.ReaderService;

@ExtendWith(SpringExtension.class)
public class ReaderControllerTest {

    @Mock
    private ReaderService readerService;

    @InjectMocks
    private ReaderController readerController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ReaderEntity reader;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(readerController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        reader = ReaderEntity.builder().readerId("1").name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now())
                .build();
    }

    @Test
    void test_GetReaders() throws Exception {
        // Given
        var readersList = List.of(reader);
        when(readerService.getAllReaders()).thenReturn(readersList);

        // When
        var result = mockMvc.perform(get("/readers"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        Assertions.assertEquals(objectMapper.writeValueAsString(readersList),
                result.getResponse().getContentAsString());
    }

    @Test
    void test_PostReader() throws Exception {
        // Given
        var readerCreationRequestDto = ReaderCreationRequestDto.builder().name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now())
                .build();
        when(readerService.saveReader(any())).thenReturn(reader);

        // When
        var result = mockMvc.perform(post("/readers")
                .content(objectMapper.writeValueAsString(readerCreationRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        Assertions.assertEquals(objectMapper.writeValueAsString(reader),
                result.getResponse().getContentAsString());
    }

    @Test
    void test_PatchReader() throws Exception {
        // Given
        var readerId = "1";
        var readerUpdateRequestDto = ReaderUpdateRequestDto.builder().email("newemail@mail.com")
                .phoneNumber("0799895132").build();
        reader.setEmail("newemail@mail.com");
        reader.setPhoneNumber("0799895132");
        when(readerService.updateReader(any(), any())).thenReturn(reader);

        // When
        var result = mockMvc.perform(patch("/readers/{id}", readerId)
                .content(objectMapper.writeValueAsString(readerUpdateRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        Assertions.assertEquals(objectMapper.writeValueAsString(reader),
                result.getResponse().getContentAsString());
    }

    @Test
    void test_DeleteReader() throws Exception {
        // Given
        var readerId = "1";
        doNothing().when(readerService).deleteReaderAndReadingRecords(any());

        // When
        var result = mockMvc.perform(delete("/readers/records/{readerId}", readerId))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        Assertions.assertEquals("Reader and associated reading records deleted successfully",
                result.getResponse().getContentAsString());
    }

}
