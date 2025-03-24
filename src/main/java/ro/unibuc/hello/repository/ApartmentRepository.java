package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ro.unibuc.hello.data.ApartmentEntity;

import java.util.List;

public interface ApartmentRepository extends MongoRepository<ApartmentEntity, String> {

    // Căutăm apartamentele după numărul de băi
    List<ApartmentEntity> findByNumberOfBathrooms(int numberOfBathrooms);

    // Căutăm apartamente pet-friendly
    List<ApartmentEntity> findByIsPetFriendly(boolean isPetFriendly);

    // Căutăm apartamente după numărul de camere
    List<ApartmentEntity> findByNumberOfRooms(int numberOfRooms);

    // Filtru nou: Apartamente cu suprafață mai mare sau egală cu o valoare
    List<ApartmentEntity> findBySquareMetersGreaterThanEqual(Double squareMeters);

    // Filtru nou: Apartamente cu preț pe noapte mai mic sau egal cu o valoare
    List<ApartmentEntity> findByPricePerNightLessThanEqual(Double pricePerNight);

    // Filtru nou: Apartamente care includ anumite facilități
    List<ApartmentEntity> findByAmenitiesContaining(String amenity);

    // Filtru nou: Apartamente unde fumatul este permis/interzis
    List<ApartmentEntity> findBySmokingAllowed(boolean smokingAllowed);

    // Filtru nou: Apartamente după locație (exact match)
    List<ApartmentEntity> findByLocation(String location);

    // Filtru avansat: Apartamente cu preț și suprafață în intervale specificate
    @Query("{'pricePerNight': {'$gte': ?0, '$lte': ?1}, 'squareMeters': {'$gte': ?2, '$lte': ?3}}")
    List<ApartmentEntity> findByPricePerNightBetweenAndSquareMetersBetween(
        Double minPrice, Double maxPrice, Double minSquareMeters, Double maxSquareMeters);

    // Metoda existentă actualizată pentru case-insensitive
    @Query("{ 'location': { $regex: ?0, $options: 'i' } }")
    List<ApartmentEntity> findByLocationIgnoreCase(String location);

    @Query("{ 'amenities': { $regex: ?0, $options: 'i' } }")
    List<ApartmentEntity> findByAmenitiesContainingIgnoreCase(String amenity);
    
}