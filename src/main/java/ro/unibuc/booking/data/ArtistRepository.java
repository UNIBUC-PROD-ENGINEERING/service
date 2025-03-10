package ro.unibuc.booking.data;

import java.util.List;
import java.util.Map;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistRepository extends MongoRepository<ArtistEntity, String> {

    ArtistEntity findByName(String name);
    List<ArtistEntity> findByPricesContaining(Map<String, Number> price);

    @Query("{ 'prices.?0' : { $lt: ?1 } }")
    List<ArtistEntity> findByPriceUnder(String key, Number price);

    @Query("{ 'prices.?0' : { $gt: ?1 } }")
    List<ArtistEntity> findByPriceOver(String key, Number price);

    @Query("{ 'prices' : { $elemMatch: { $gte: ?0, $lte: ?1 } } }")
    List<ArtistEntity> findArtistsWithPricesBetween(Number minPrice, Number maxPrice);

}
