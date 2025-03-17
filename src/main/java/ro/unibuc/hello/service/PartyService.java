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
