package ro.unibuc.hello.utils;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.BookCreationRequestDto;
import ro.unibuc.hello.dto.ReaderCreationRequestDto;
import ro.unibuc.hello.service.AuthorService;
import ro.unibuc.hello.service.BookService;
import ro.unibuc.hello.service.ReaderService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Seeder implements ApplicationRunner {

        private final AuthorService authorService;
        private final ReaderService readerService;
        private final AuthorRepository authorRepository;
        private final BookService bookService;

        @Override
        public void run(ApplicationArguments args) {
                var author1 = AuthorCreationRequestDto.builder().name("Ion Creanga").nationality("romanian")
                                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1889, 12, 31)).build();
                var author2 = AuthorCreationRequestDto.builder().name("Mihai Eminescu").nationality("romanian")
                                .birthDate(LocalDate.of(1850, 01, 15)).deathDate(LocalDate.of(1889, 06, 15)).build();
                var author3 = AuthorCreationRequestDto.builder().name("Hector Malot").nationality("french")
                                .birthDate(LocalDate.of(1830, 05, 20)).deathDate(LocalDate.of(1907, 07, 18)).build();
                var author4 = AuthorCreationRequestDto.builder().name("Victor Hugo").nationality("french")
                                .birthDate(LocalDate.of(1802, 02, 26)).deathDate(LocalDate.of(1885, 05, 22)).build();

                authorService.saveAuthor(author1);
                authorService.saveAuthor(author2);
                authorService.saveAuthor(author3);
                authorService.saveAuthor(author4);

                var reader1 = ReaderCreationRequestDto.builder().name("Ionescu Ana").nationality("romanian")
                                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                                .registrationDate(LocalDate.now())
                                .build();
                var reader2 = ReaderCreationRequestDto.builder().name("Smith John").nationality("english")
                                .phoneNumber("0765112342").birthDate(LocalDate.of(1998, 12, 04))
                                .registrationDate(LocalDate.now())
                                .build();
                var reader3 = ReaderCreationRequestDto.builder().name("Popescu Mihai").nationality("romanian")
                                .phoneNumber("0789514278").birthDate(LocalDate.of(2004, 10, 11))
                                .registrationDate(LocalDate.now())
                                .build();
                var reader4 = ReaderCreationRequestDto.builder().name("Pierre Hugo").nationality("french")
                                .phoneNumber("07842561526").birthDate(LocalDate.of(2000, 01, 25))
                                .registrationDate(LocalDate.now())
                                .build();

                readerService.saveReader(reader1);
                readerService.saveReader(reader2);
                readerService.saveReader(reader3);
                readerService.saveReader(reader4);

                var authorForBook1 = authorRepository.findByName("Ion Creanga");
                var book1 = BookCreationRequestDto.builder().authorId(authorForBook1.getAuthorId()).genre("Adventure")
                                .title("Povestea lui Harap-Alb").publicationDate(LocalDate.of(1890, 9, 17))
                                .publisher("Corint").build();

                var authorForBook2 = authorRepository.findByName("Hector Malot");
                var book2 = BookCreationRequestDto.builder().authorId(authorForBook2.getAuthorId()).genre("Fiction")
                                .title("Singur pe lume").publicationDate(LocalDate.of(1990, 4, 10))
                                .publisher("Corint").build();

                bookService.saveBook(book1);
                bookService.saveBook(book2);
        }
}