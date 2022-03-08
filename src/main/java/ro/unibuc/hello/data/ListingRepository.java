package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.Listing;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends MongoRepository<Listing, String> {
    @Query(" 'listingId' : ?0 }")
    Listing findListingById(String listingId);
}
