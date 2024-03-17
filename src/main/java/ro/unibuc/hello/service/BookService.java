package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.dto.BookCreationRequestDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public BookEntity saveBook(BookCreationRequestDto bookCreationRequestDto) {
        var bookEntity = mapToBookEntity(bookCreationRequestDto);
        return bookRepository.save(bookEntity);
    }

    private BookEntity mapToBookEntity(BookCreationRequestDto dto) {
        var bookEntity = BookEntity.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .publicationDate(dto.getPublicationDate())
                .publisher(dto.getPublisher())
                .build();

        // Fetch author entity by _id and set it in BookEntity
        AuthorEntity authorEntity = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + dto.getAuthorId()));
        bookEntity.setAuthor(authorEntity);

        return bookEntity;
    }
}
