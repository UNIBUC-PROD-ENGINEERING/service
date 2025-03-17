package ro.unibuc.hello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.PartyWithSongsResponse;
import ro.unibuc.hello.data.SongEntity;
import ro.unibuc.hello.repositories.PartyRepository;

import ro.unibuc.hello.service.PartyService;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.data.FoodEntity;

import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.service.PartyService;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.repositories.SongRepository;
import ro.unibuc.hello.service.YouTubeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parties")
public class PartyController {

    private static final Logger logger = LoggerFactory.getLogger(PartyController.class);

    private final PartyRepository partyRepository;
    private final TaskRepository taskRepository;  // Repository pentru taskuri
    private final PartyService partyService;      // Adăugăm PartyService pentru logica de business
    private final SongRepository songRepository;
    private final YouTubeService youTubeService;


    // Injectăm dependențele
    public PartyController(PartyRepository partyRepository, TaskRepository taskRepository, PartyService partyService, SongRepository songRepository, YouTubeService youTubeService) {
        this.partyRepository = partyRepository;
        this.taskRepository = taskRepository;
        this.partyService = partyService;
        this.songRepository = songRepository;
        this.youTubeService = youTubeService;

    }



//     @GetMapping
//     public List<PartyEntity> getAllParties() {
//         return partyService.getAllParties();
//     }

//     @GetMapping("/{id}")
//     public PartyEntity getPartyById(@PathVariable String id) {
//         return partyService.getPartyById(id);
  
    @GetMapping
    public List<PartyWithSongsResponse> getAllParties() {
        List<PartyEntity> parties = partyRepository.findAll();
        List<PartyWithSongsResponse> partyResponses = new ArrayList<>();

        for (PartyEntity party : parties) {
            // Fetch song details for the party and concatenate title, artist, and path
            List<String> songNames = new ArrayList<>();
            for (String songId : party.getPlaylistIds()) {
                Optional<SongEntity> songOptional = songRepository.findById(songId);
                songOptional.ifPresent(song -> {
                    String songDetails = song.getTitle() + " - " + song.getArtist() + " (" + song.getPath() + ")";
                    songNames.add(songDetails);
                });
            }

            // Create the custom response object for each party
            PartyWithSongsResponse response = new PartyWithSongsResponse(
                    party.getId(),
                    party.getName(),
                    party.getDate(),
                    party.getLocationId(),
                    party.getFoodIds(),
                    party.getUserIds(),
                    songNames,  // This will contain concatenated song details
                    party.getTaskIds(),
                    party.getPartyPoints()
            );

            partyResponses.add(response);
        }

        return partyResponses;
    }


    @GetMapping("/{id}")
    public ResponseEntity<PartyWithSongsResponse> getPartyById(@PathVariable String id) {
        Optional<PartyEntity> partyOptional = partyRepository.findById(id);
        if (partyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        PartyEntity party = partyOptional.get();

        // Fetch song details for the party and concatenate title, artist, and path
        List<String> songNames = new ArrayList<>();
        for (String songId : party.getPlaylistIds()) {
            Optional<SongEntity> songOptional = songRepository.findById(songId);
            songOptional.ifPresent(song -> {
                String songDetails = song.getTitle() + " - " + song.getArtist() + " (" + song.getPath() + ")";
                songNames.add(songDetails);
            });
        }

        // Create the custom response object
        PartyWithSongsResponse response = new PartyWithSongsResponse(
                party.getId(),
                party.getName(),
                party.getDate(),
                party.getLocationId(),
                party.getFoodIds(),
                party.getUserIds(),
                songNames,  // This will contain concatenated song details
                party.getTaskIds(),
                party.getPartyPoints()
        );

        return ResponseEntity.ok(response);
    }

    // GET: Retrieve tasks for a party
    @GetMapping("/{partyId}/tasks")
    public List<TaskEntity> getTasksByParty(@PathVariable String partyId) {
        return taskRepository.findByPartyId(partyId);  // Metodă pentru a obține taskurile pentru petrecerea respectivă

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

    // POST: Adăugăm un user la o petrecere
    @PostMapping("/{partyId}/addUser/{userId}")
    public ResponseEntity<?> addUserToParty(
            @PathVariable String partyId,
            @PathVariable String userId) {
        try {
            PartyEntity updatedParty = partyService.addUserToParty(partyId, userId);

            if (updatedParty != null) {
                return ResponseEntity.ok(updatedParty);
            } else {
                return ResponseEntity.status(400).body("Petrecerea sau utilizatorul nu există sau utilizatorul este deja adăugat.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare internă a serverului: " + e.getMessage());
        }
    }

    // POST: Actualizăm punctele petrecerii după completarea unui task
    @PostMapping("/{partyId}/user/{userId}/completeTask/{taskId}")
    public ResponseEntity<PartyEntity> completeTaskAndUpdatePartyPoints(
            @PathVariable String partyId,
            @PathVariable String userId,
            @PathVariable String taskId) {

    // Apelăm metoda din PartyService pentru a actualiza punctele petrecerii
    PartyEntity updatedParty = partyService.updatePartyPointsAfterTaskCompletion(partyId, userId, taskId);

    // Dacă petrecerea a fost actualizată cu succes
    if (updatedParty != null) {
        return ResponseEntity.ok(updatedParty); // Returnăm petrecerea actualizată
    } else {
        // Dacă petrecerea nu există sau userul nu este la petrecere
        return ResponseEntity.status(404).body(null);  // Returnăm un mesaj de eroare cu status 404
    }

    @PostMapping("/{partyId}/songs")
    public ResponseEntity<?> addSongToParty(@PathVariable String partyId, @RequestBody SongEntity song) {
        Optional<PartyEntity> partyOptional = partyRepository.findById(partyId);
        if (partyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party not found");
        }

        // Fetch YouTube link
        String youtubeLink = youTubeService.searchYouTube(song.getTitle(), song.getArtist());
        if (youtubeLink == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find song on YouTube");
        }

        // Save song with YouTube path
        song.setPath(youtubeLink);
        SongEntity savedSong = songRepository.save(song);

        // Add song ID to party
        PartyEntity party = partyOptional.get();
        party.addSong(savedSong.getId());
        partyRepository.save(party);

        return ResponseEntity.ok(savedSong);
    }


    @DeleteMapping("/{partyId}/songs/{songId}")
    public ResponseEntity<?> removeSongFromParty(@PathVariable String partyId, @PathVariable String songId) {
        Optional<PartyEntity> partyOptional = partyRepository.findById(partyId);
        if (partyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party not found");
        }

        PartyEntity party = partyOptional.get();
        if (!party.getPlaylistIds().contains(songId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found in the party playlist");
        }

        party.removeSong(songId);
        partyRepository.save(party); 

        Optional<SongEntity> songOptional = songRepository.findById(songId);
        if (songOptional.isPresent()) {
            SongEntity song = songOptional.get();
            songRepository.delete(song);  
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Respond with 204 No Content

    }


}
}