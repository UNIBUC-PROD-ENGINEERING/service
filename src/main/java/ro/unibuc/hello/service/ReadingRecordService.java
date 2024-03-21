package ro.unibuc.hello.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.exception.DuplicateEntityException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.data.ReaderRepository;
import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.dto.ReadingRecordCreationRequestDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadingRecordService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReadingRecordRepository readingRecordRepository;

    public ReadingRecordEntity saveReadingRecord(ReadingRecordCreationRequestDto readingRecordCreationRequestDto)
            throws DuplicateEntityException {
        log.debug("Attempting to save a reading record for bookId: {} and readerId: {}",
                readingRecordCreationRequestDto.getBookId(), readingRecordCreationRequestDto.getReaderId());

        var bookEntity = bookRepository.findById(readingRecordCreationRequestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "BookEntity not found for id: " + readingRecordCreationRequestDto.getBookId()));

        var readerEntity = readerRepository.findById(readingRecordCreationRequestDto.getReaderId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ReaderEntity not found for id: " + readingRecordCreationRequestDto.getReaderId()));

        ReadingRecordEntity recordEntity = readingRecordRepository.findByReaderAndBook(readerEntity, bookEntity);
        if (recordEntity != null) {
            log.debug("A reading record already exists for bookId: {} and readerId: {}",
                    readingRecordCreationRequestDto.getBookId(), readingRecordCreationRequestDto.getReaderId());
            throw new DuplicateEntityException("A reading record already exists for the given ids.");
        }

        var readingRecordEntity = ReadingRecordEntity.builder()
                .book(bookEntity)
                .reader(readerEntity)
                .dateStarted(LocalDate.now())
                .dateFinished(null)
                .build();

        log.debug("Reading record saved successfully for bookId: {} and readerId: {}",
                readingRecordCreationRequestDto.getBookId(), readingRecordCreationRequestDto.getReaderId());
        return readingRecordRepository.save(readingRecordEntity);
    }

    public List<ReadingRecordEntity> getAllReadingRecords() {
        log.debug("Getting all readingRecords");
        return readingRecordRepository.findAll();
    }

}
