package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRecordRepository extends MongoRepository<ReadingRecordEntity, String> {
    ReadingRecordEntity findByReaderAndBook(ReaderEntity reader, BookEntity book);
    List<ReadingRecordEntity> findByReader(ReaderEntity reader);
}