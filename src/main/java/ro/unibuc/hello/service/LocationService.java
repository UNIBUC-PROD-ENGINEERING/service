package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.repositories.LocationRepository;
import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationEntity> getAllLocations() {
        return locationRepository.findAll();
    }

    public List<LocationEntity> getLocationsByRating(double minRating) {
        return locationRepository.findByRatingGreaterThanEqual(minRating);
    }

    public List<LocationEntity> getLocationsByPrice(double maxPrice) {
        return locationRepository.findByPriceLessThanEqual(maxPrice);
    }

    public List<LocationEntity> getLocationsByDiscountPoints(int maxPoints) {
        return locationRepository.findByDiscountPointsRequiredLessThanEqual(maxPoints);
    }
}
