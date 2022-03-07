package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.Listing;

@Repository
public interface ListingRepository extends MongoRepository<Listing, String> {
}
