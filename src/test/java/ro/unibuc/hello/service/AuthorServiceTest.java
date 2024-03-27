package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.AuthorDeleteRequestDto;
import ro.unibuc.hello.dto.AuthorUpdateRequestDto;
import ro.unibuc.hello.exception.DuplicateEntityException;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    AuthorService authorService = new AuthorService();

    @Mock
    BookService bookService = new BookService();

    @BeforeEach
    public void setup() {
        authorRepository.deleteAll();
    }

    @Test
    public void test_getAllAuthors() throws Exception {
        // Given
        var author = AuthorEntity.builder().authorId("1").name("Ion Creanga").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1889, 12, 31))
                .build();
        var authorList = List.of(author);
        when(authorRepository.findAll()).thenReturn(authorList);

        // When
        var response = authorService.getAllAuthors();

        // Then
        Assertions.assertEquals(authorList, response);
    }

    @Test
    public void test_saveAuthor_ok() throws Exception {
        // Given
        var author = AuthorEntity.builder().authorId("1").name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();
        var authorCreationRequestDto = AuthorCreationRequestDto.builder().name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();
        when(authorRepository.findByNameAndBirthDate(any(), any())).thenReturn(null);
        when(authorRepository.save(any())).thenReturn(author);

        // When
        var response = authorService.saveAuthor(authorCreationRequestDto);

        // Then
        Assertions.assertEquals(author, response);
    }

    @Test
    public void test_saveAuthor_duplicateAuthorException() throws Exception {
        // Given
        var author = AuthorEntity.builder().authorId("1").name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();
        var authorCreationRequestDto = AuthorCreationRequestDto.builder().name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();
        when(authorRepository.findByNameAndBirthDate(any(), any())).thenReturn(author);

        // When
        // Then
        var exception = Assertions.assertThrows(DuplicateEntityException.class,
                () -> authorService.saveAuthor(authorCreationRequestDto));
        Assertions.assertEquals("An author already exists for the given name and birthday.", exception.getMessage());
    }

    @Test
    public void test_updateAuthor_ok() throws Exception {
        // Given
        var author = AuthorEntity.builder().authorId("1").name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();
        var authorUpdateRequestDto = AuthorUpdateRequestDto.builder().deathDate(LocalDate.of(1986, 4, 27))
                .build();
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        when(authorRepository.save(any())).thenReturn(author);

        // When
        var response = authorService.updateAuthor("1", authorUpdateRequestDto);

        // Then
        Assertions.assertEquals(authorUpdateRequestDto.getDeathDate(), response.getDeathDate());
    }

    @Test
    public void test_updateAuthor_EntityNotFoundException() {
        // Given
        AuthorUpdateRequestDto authorUpdateRequestDto = AuthorUpdateRequestDto.builder()
                .deathDate(LocalDate.of(1986, 4, 27))
                .build();

        when(authorRepository.findById("1")).thenReturn(Optional.empty());

        // When
        // Then
        var exception = assertThrows(EntityNotFoundException.class,
                () -> authorService.updateAuthor("1", authorUpdateRequestDto));
        Assertions.assertEquals("Entity: 1 was not found", exception.getMessage());
    }

    @Test
    public void test_deleteAuthor_ok() throws Exception {
        // Given
        var author = AuthorEntity.builder().authorId("1").name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();
        var authorDeleteRequestDto = AuthorDeleteRequestDto.builder().birthDate(LocalDate.of(1837, 03, 1))
                .name("Mircea Eliade").build();
        when(authorRepository.findByNameAndBirthDate(any(), any())).thenReturn(author);
        when(bookService.getBooksByAuthor(author)).thenReturn(Collections.emptyList());

        // When
        // Then
        assertDoesNotThrow(() -> {
            authorService.deleteAuthor(authorDeleteRequestDto);
        });
    }

    @Test
    public void test_deleteAuthor_RuntimeException() throws Exception {
        // Given
        var author = AuthorEntity.builder().authorId("1").name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();
        var authorDeleteRequestDto = AuthorDeleteRequestDto.builder().birthDate(LocalDate.of(1837, 03, 1))
                .name("Mircea Eliade").build();
        when(authorRepository.findByNameAndBirthDate(any(), any())).thenReturn(author);
        when(bookService.getBooksByAuthor(author)).thenReturn(Collections.singletonList(new BookEntity()));

        // When
        // Then
        var exception = assertThrows(RuntimeException.class,
                () -> authorService.deleteAuthor(authorDeleteRequestDto));
        Assertions.assertEquals("Cannot delete author", exception.getMessage());
    }
}
