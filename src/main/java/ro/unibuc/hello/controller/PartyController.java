package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.SongEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.SongRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  // Add this line for logging
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parties")
public class PartyController {
    private final PartyRepository partyRepository;
    private final SongRepository songRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(PartyController.class);

    // Constructor with SongRepository injection
    public PartyController(PartyRepository partyRepository, SongRepository songRepository) {
        this.partyRepository = partyRepository;
        this.songRepository = songRepository;
    }

    // GET: Retrieve all parties
    @GetMapping
    public List<PartyEntity> getAllParties() {
        return partyRepository.findAll();
    }

    // GET: Retrieve a single party by ID
    @GetMapping("/{id}")
    public PartyEntity getPartyById(@PathVariable String id) {
        return partyRepository.findById(id).orElse(null);
    }

    // POST: Create a new party
    @PostMapping
    public PartyEntity createParty(@RequestBody PartyEntity party) {
        return partyRepository.save(party);
    }

    // PUT: Update an existing party
    @PutMapping("/{id}")
    public PartyEntity updateParty(@PathVariable String id, @RequestBody PartyEntity updatedParty) {
        updatedParty.setId(id);
        return partyRepository.save(updatedParty);
    }

    // DELETE: Remove a party
    @DeleteMapping("/{id}")
    public void deleteParty(@PathVariable String id) {
        partyRepository.deleteById(id);
    }

    @PostMapping("/{partyId}/songs")
    public ResponseEntity<?> addSongToParty(@PathVariable String partyId, @RequestBody SongEntity song) {
        Optional<PartyEntity> partyOptional = partyRepository.findById(partyId);
        if (partyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party not found");
        }

        // Search for the YouTube link
        // String youtubeLink = youTubeSearchService.searchYouTube(song.getTitle(), song.getArtist());
        // if (youtubeLink == null) {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find song on YouTube");
        // }

        // // Save song with YouTube path
        // song.setPath(youtubeLink);
        SongEntity savedSong = songRepository.save(song);

        // Add song ID to party
        PartyEntity party = partyOptional.get();
        party.addSong(savedSong.getId());
        partyRepository.save(party);

        return ResponseEntity.ok(savedSong);
    }
}
