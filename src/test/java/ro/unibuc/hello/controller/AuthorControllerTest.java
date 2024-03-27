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

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.AuthorDeleteRequestDto;
import ro.unibuc.hello.dto.AuthorUpdateRequestDto;
import ro.unibuc.hello.service.AuthorService;

@ExtendWith(SpringExtension.class)
public class AuthorControllerTest {

        @Mock
        private AuthorService authorService;

        @InjectMocks
        private AuthorController authorController;

        private MockMvc mockMvc;

        private ObjectMapper objectMapper;

        private AuthorEntity author;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                author = AuthorEntity.builder().authorId("1").name("Ion Creanga").nationality("romanian")
                                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1889, 12, 31))
                                .build();
        }

        @Test
        void test_GetAuthors() throws Exception {
                // Given
                var authorList = List.of(author);
                when(authorService.getAllAuthors()).thenReturn(authorList);

                // When
                var result = mockMvc.perform(get("/authors"))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals(objectMapper.writeValueAsString(authorList),
                                result.getResponse().getContentAsString());

        }

        @Test
        void test_PostAuthor() throws Exception {
                // Given
                var authorCreationRequestDto = AuthorCreationRequestDto.builder().name("Ion Creanga")
                                .nationality("romanian")
                                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1889, 12, 31))
                                .build();
                when(authorService.saveAuthor(any())).thenReturn(author);

                // When
                var result = mockMvc.perform(post("/authors")
                                .content(objectMapper.writeValueAsString(authorCreationRequestDto))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals(objectMapper.writeValueAsString(author),
                                result.getResponse().getContentAsString());
        }

        @Test
        void test_PatchAuthor() throws Exception {
                // Given
                var authorId = "1";
                var authorUpdateRequestDto = AuthorUpdateRequestDto.builder().deathDate(LocalDate.of(1899, 12, 31))
                                .build();
                author.setDeathDate(LocalDate.of(1899, 12, 31));
                when(authorService.updateAuthor(any(), any())).thenReturn(author);

                // When
                var result = mockMvc.perform(patch("/authors/{id}", authorId)
                                .content(objectMapper.writeValueAsString(authorUpdateRequestDto))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals(objectMapper.writeValueAsString(author),
                                result.getResponse().getContentAsString());
        }

        @Test
        void test_DeleteAuthor() throws Exception {
                // Given
                var authorDeleteRequestDto = AuthorDeleteRequestDto.builder().birthDate(LocalDate.of(1837, 03, 1))
                                .name("Ion Creanga").build();
                doNothing().when(authorService).deleteAuthor(any());

                // When
                var result = mockMvc.perform(delete("/authors")
                                .content(objectMapper.writeValueAsString(authorDeleteRequestDto))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals("Author deleted successfully",
                                result.getResponse().getContentAsString());
        }

}
