package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;

import ro.unibuc.hello.data.FoodEntity;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.FoodRepository;
import ro.unibuc.hello.repositories.LocationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final FoodRepository foodRepository;
    private final LocationRepository locationRepository;

    public PartyService(PartyRepository partyRepository, FoodRepository foodRepository, LocationRepository locationRepository) {
        this.partyRepository = partyRepository;
        this.foodRepository = foodRepository;
        this.locationRepository = locationRepository;
    }

    public List<PartyEntity> getAllParties() {
        return partyRepository.findAll();
    }

    public PartyEntity getPartyById(String id) {
        return partyRepository.findById(id).orElse(null);
    }    

    public List<PartyEntity> getPartiesForUser(String userId) {
        return partyRepository.findByUserIdsContaining(userId);
    }

    public PartyEntity createParty(PartyEntity party) {
        return partyRepository.save(party);
    }

    public List<FoodEntity> getAvailableFoodsForParty(String partyId, Double minRating, Double maxPrice, Integer maxPoints) {
        List<FoodEntity> foods = foodRepository.findAll();
        PartyEntity party = partyRepository.findById(partyId).orElse(null);
        int partyPoints = party.getPartyPoints();

        for (FoodEntity food : foods) {
            double discountedPrice = (partyPoints >= food.getDiscountPointsRequired()) 
                ? food.getPrice() * 0.85  
                : food.getPrice();
    
            food.setDiscountedPrice(discountedPrice); // Setăm prețul redus în obiect
        }
    
        if (minRating != null) {
            foods = foods.stream()
                    .filter(food -> food.getRating() >= minRating)
                    .toList();
        }
        if (maxPrice != null) {
            foods = foods.stream()
                    .filter(food -> food.getDiscountedPrice() <= maxPrice)
                    .toList();
        }
        if (maxPoints != null) {
            foods = foods.stream()
                    .filter(food -> food.getDiscountPointsRequired() <= maxPoints)
                    .toList();
        }
    
        return foods;
    }

    public PartyEntity addFoodToParty(String partyId, String foodId) {
        Optional<PartyEntity> partyOpt = partyRepository.findById(partyId);
        Optional<FoodEntity> foodOpt = foodRepository.findById(foodId);

        if (partyOpt.isPresent() && foodOpt.isPresent()) {
            PartyEntity party = partyOpt.get();
            party.getFoodIds().add(foodId);
            return partyRepository.save(party);
        }

        return null;  // Dacă partyId sau foodId nu există, returnăm null
    }

    public List<LocationEntity> getAvailableLocationsForParty(String partyId, Double minRating, Double maxPrice, Integer maxPoints) {
        PartyEntity party = partyRepository.findById(partyId).orElse(null);
        int partyPoints = party.getPartyPoints();
        List<LocationEntity> locations = locationRepository.findAll();

        for (LocationEntity location : locations) {
            double discountedPrice = (partyPoints >= location.getDiscountPointsRequired()) 
                ? location.getPrice() * 0.85  
                : location.getPrice();
    
            location.setDiscountedPrice(discountedPrice); // Setăm prețul redus în obiect
        }
    
        if (minRating != null) {
            locations = locations.stream()
                    .filter(location -> location.getRating() >= minRating)
                    .toList();
        }
        if (maxPrice != null) {
            locations = locations.stream()
                    .filter(location -> location.getDiscountedPrice() <= maxPrice)
                    .toList();
        }
        if (maxPoints != null) {
            locations = locations.stream()
                    .filter(location -> location.getDiscountPointsRequired() <= maxPoints)
                    .toList();
        }
    
        return locations;
    }    

    public PartyEntity addLocationToParty(String partyId, String locationId) {
        Optional<PartyEntity> partyOpt = partyRepository.findById(partyId);
        Optional<LocationEntity> locationOpt = locationRepository.findById(locationId);

        if (partyOpt.isPresent() && locationOpt.isPresent()) {
            PartyEntity party = partyOpt.get();
            party.setLocationId(locationId);  // Setează locația pentru petrecere
            return partyRepository.save(party);
        }

        return null;  // Dacă partyId sau locationId nu există
    }

}
