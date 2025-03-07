package ro.unibuc.hello.repository;

import ro.unibuc.hello.data.BookingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<BookingEntity, String> {
}
