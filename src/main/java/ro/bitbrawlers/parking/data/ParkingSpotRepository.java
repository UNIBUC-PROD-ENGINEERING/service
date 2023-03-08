package ro.bitbrawlers.parking.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParkingSpotRepository extends MongoRepository<ParkingSpotEntity, Integer> {

    List<ParkingSpotEntity> findAll ();

    @Query(value = "{\"_id\": ?0}")
    ParkingSpotEntity findSpotById (Integer id);

    @Query(value = "{}",count = true)
    Integer totalSpots();
    @Query(value = "{\"person\": null}",count = true)
    Integer countEmptySpots();

}