package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.service.PartyService;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.data.FoodEntity;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/parties")
public class PartyController {
    private final PartyService partyService;
    private final PartyRepository partyRepository;

    public PartyController(PartyService partyService, PartyRepository partyRepository) {
        this.partyService = partyService;
        this.partyRepository = partyRepository;
    }


    @GetMapping
    public List<PartyEntity> getAllParties() {
        return partyService.getAllParties();
    }

    @GetMapping("/{id}")
    public PartyEntity getPartyById(@PathVariable String id) {
        return partyService.getPartyById(id);
    }

    @PostMapping
    public PartyEntity createParty(@RequestBody PartyEntity party) {
        return partyRepository.save(party);
    }

    @PutMapping("/{id}")
    public PartyEntity updateParty(@PathVariable String id, @RequestBody PartyEntity updatedParty) {
        updatedParty.setId(id);
        return partyRepository.save(updatedParty);
    }

    @DeleteMapping("/{id}")
    public void deleteParty(@PathVariable String id) {
        partyRepository.deleteById(id);
    }

    @PostMapping("/{partyId}/foods/{foodId}")
    public ResponseEntity<PartyEntity> addFoodToParty(@PathVariable String partyId, @PathVariable String foodId) {
        PartyEntity updatedParty = partyService.addFoodToParty(partyId, foodId);
        return updatedParty != null ? ResponseEntity.ok(updatedParty) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{partyId}/locations/{locationId}")
    public ResponseEntity<PartyEntity> addLocationToParty(@PathVariable String partyId, @PathVariable String locationId) {
        PartyEntity updatedParty = partyService.addLocationToParty(partyId, locationId);
        return updatedParty != null ? ResponseEntity.ok(updatedParty) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/locations")
    public List<LocationEntity> getAvailableLocationsForParty(
            @PathVariable String id,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer maxPoints) {
        
        return partyService.getAvailableLocationsForParty(id, minRating, maxPrice, maxPoints);
    }

    @GetMapping("/{id}/foods")
    public List<FoodEntity> getAvailableFoodsForParty(
            @PathVariable String id,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer maxPoints) {
        
        return partyService.getAvailableFoodsForParty(id, minRating, maxPrice, maxPoints);
    }


}
