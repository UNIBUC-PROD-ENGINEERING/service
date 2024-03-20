package ro.unibuc.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.AuthorDeleteRequestDto;
import ro.unibuc.hello.dto.UpdateAuthorRequestDto;
import ro.unibuc.hello.exception.DuplicateEntityException;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookService bookService;

    public AuthorEntity saveAuthor(AuthorCreationRequestDto authorCreationRequestDto) throws DuplicateEntityException {
        log.debug("Attempting to save a new author with name '{}'", authorCreationRequestDto.getName());
        var authorDuplicateEntity = authorRepository.findByNameAndBirthDate(authorCreationRequestDto.getName(), authorCreationRequestDto.getBirthDate());
        if (authorDuplicateEntity != null) {
            log.debug("An author with that name and birthdate already exists");
            throw new DuplicateEntityException("An author already exists for the given name and birthday.");
        }

        log.debug("Creating a new author '{}'", authorCreationRequestDto.getName());
        var authorEntity = mapToAuthorEntity(authorCreationRequestDto);
        return authorRepository.save(authorEntity);
    }

    public AuthorEntity updateAuthor(String id, UpdateAuthorRequestDto updateAuthorRequestDto) {
        log.debug("Updating the author with id '{}', setting death date '{}'", id,
                updateAuthorRequestDto.getDeathDate());
        var author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        author.setDeathDate(updateAuthorRequestDto.getDeathDate());
        return authorRepository.save(author);
    }

    public void deleteAuthor(AuthorDeleteRequestDto authorDeleteRequestDto) throws RuntimeException {
        var name = authorDeleteRequestDto.getName();
        var birthDate = authorDeleteRequestDto.getBirthDate();

        log.debug("Trying to delete author '{}' with birthDate '{}' from system", name, birthDate);
        var author = authorRepository.findByNameAndBirthDate(name, birthDate);
        if (authorHasAnyWrittenBooks(author)) {
            log.debug("Cannot delete author '{}' because he has books registered in the system", name);
            throw new RuntimeException("Cannot delete author");
        } else {
            log.debug("Deleting author '{}' ", name);
            authorRepository.delete(author);
        }
    }

    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    private AuthorEntity mapToAuthorEntity(AuthorCreationRequestDto dto) {
        log.debug("Map authorCreationRequestDto to authorEntity");
        return AuthorEntity.builder()
                .name(dto.getName())
                .nationality(dto.getNationality())
                .birthDate(dto.getBirthDate())
                .deathDate(dto.getDeathDate())
                .build();
    }

    private boolean authorHasAnyWrittenBooks(AuthorEntity author) {
        log.debug("Checking if there are any books with author '{}'", author.getName());
        var books = bookService.getBooksByAuthor(author);
        if (books.size() != 0)
            return true;
        return false;
    }
}
