package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.PartyWithSongsResponse;
import ro.unibuc.hello.data.SongEntity;
import ro.unibuc.hello.repositories.PartyRepository;
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
    private final PartyRepository partyRepository;
    private final SongRepository songRepository;
    private final YouTubeService youTubeService;

    private static final Logger logger = LoggerFactory.getLogger(PartyController.class);

    public PartyController(PartyRepository partyRepository, SongRepository songRepository, YouTubeService youTubeService) {
        this.partyRepository = partyRepository;
        this.songRepository = songRepository;
        this.youTubeService = youTubeService;
    }

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
