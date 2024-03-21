package ro.unibuc.hello.utils;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.data.ReaderRepository;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.data.ReviewRepository;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.BookCreationRequestDto;
import ro.unibuc.hello.dto.ReaderCreationRequestDto;
import ro.unibuc.hello.dto.ReadingRecordCreationRequestDto;
import ro.unibuc.hello.dto.ReviewCreationRequestDto;
import ro.unibuc.hello.service.AuthorService;
import ro.unibuc.hello.service.BookService;
import ro.unibuc.hello.service.ReaderService;
import ro.unibuc.hello.service.ReadingRecordService;
import ro.unibuc.hello.service.ReviewService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Seeder implements ApplicationRunner {

        private final AuthorService authorService;
        private final ReaderService readerService;
        private final ReadingRecordService readingRecordService;
        private final BookService bookService;
        private final ReviewService reviewService;

        private final AuthorRepository authorRepository;
        private final ReaderRepository readerRepository;
        private final ReadingRecordRepository readingRecordRepository;
        private final BookRepository bookRepository;
        private final ReviewRepository reviewRepository;

        @Override
        public void run(ApplicationArguments args) {
                cleanDatabase();

                var author1 = AuthorCreationRequestDto.builder().name("Ion Creanga").nationality("romanian")
                                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1889, 12, 31))
                                .build();
                var author2 = AuthorCreationRequestDto.builder().name("Mihai Eminescu").nationality("romanian")
                                .birthDate(LocalDate.of(1850, 01, 15)).deathDate(LocalDate.of(1889, 06, 15))
                                .build();
                var author3 = AuthorCreationRequestDto.builder().name("Hector Malot").nationality("french")
                                .birthDate(LocalDate.of(1830, 05, 20)).deathDate(LocalDate.of(1907, 07, 18))
                                .build();
                var author4 = AuthorCreationRequestDto.builder().name("Victor Hugo").nationality("french")
                                .birthDate(LocalDate.of(1802, 02, 26)).deathDate(LocalDate.of(1885, 05, 22))
                                .build();

                var savedAuthor1 = authorService.saveAuthor(author1);
                authorService.saveAuthor(author2);
                var savedAuthor3 = authorService.saveAuthor(author3);
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

                var savedReader1 = readerService.saveReader(reader1);
                readerService.saveReader(reader2);
                readerService.saveReader(reader3);
                var savedReader4 = readerService.saveReader(reader4);

                var book1 = BookCreationRequestDto.builder().authorId(savedAuthor1.getAuthorId())
                                .genre("Adventure")
                                .title("Povestea lui Harap-Alb").publicationDate(LocalDate.of(1890, 9, 17))
                                .publisher("Corint").build();

                var book2 = BookCreationRequestDto.builder().authorId(savedAuthor3.getAuthorId())
                                .genre("Fiction")
                                .title("Singur pe lume").publicationDate(LocalDate.of(1990, 4, 10))
                                .publisher("Corint").build();

                var readBook1 = bookService.saveBook(book1);
                var readBook2 = bookService.saveBook(book2);

                var readingRecord1 = ReadingRecordCreationRequestDto.builder().bookId(readBook1.getBookId())
                                .readerId(savedReader1.getReaderId()).build();
                var readingRecord2 = ReadingRecordCreationRequestDto.builder().bookId(readBook2.getBookId())
                                .readerId(savedReader4.getReaderId()).build();

                var savedReadingRecord1 = readingRecordService.saveReadingRecord(readingRecord1);
                readingRecordService.saveReadingRecord(readingRecord2);

                var review1 = ReviewCreationRequestDto.builder().rating(5).reviewBody("Good book")
                                .readingRecordId(savedReadingRecord1.getReadingRecordId()).build();
                reviewService.saveReview(review1);
        }

        private void cleanDatabase() {
                readingRecordRepository.deleteAll();
                bookRepository.deleteAll();
                authorRepository.deleteAll();
                readerRepository.deleteAll();
                reviewRepository.deleteAll();
        }
}