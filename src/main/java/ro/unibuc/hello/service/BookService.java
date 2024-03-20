package ro.unibuc.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.dto.BookCreationRequestDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public BookEntity saveBook(BookCreationRequestDto bookCreationRequestDto) {
        log.debug("Creating a new book with title {}", bookCreationRequestDto.getTitle());
        var bookEntity = mapToBookEntity(bookCreationRequestDto);
        return bookRepository.save(bookEntity);
    }

    public List<BookEntity> getBooksByAuthor(AuthorEntity authorEntity) {
        return bookRepository.findByAuthor(authorEntity);
    }

    private BookEntity mapToBookEntity(BookCreationRequestDto dto) {
        log.debug("Map bookCreationRequestDto to bookEntity");
        var bookEntity = BookEntity.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .publicationDate(dto.getPublicationDate())
                .publisher(dto.getPublisher())
                .build();

        log.debug("Getting author with id '{}'", dto.getAuthorId());
        var authorEntity = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(dto.getAuthorId()));
        bookEntity.setAuthor(authorEntity);

        return bookEntity;
    }

    public List<BookEntity> getBooksByAuthor(String authorId) {
        var authorEntity = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));
        return bookRepository.findByAuthor(authorEntity);
    }
}
