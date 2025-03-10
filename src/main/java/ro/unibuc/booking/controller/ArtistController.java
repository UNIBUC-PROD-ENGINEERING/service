package ro.unibuc.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.booking.data.ArtistEntity;
import ro.unibuc.booking.service.ArtistService;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // Create a new artist
    @PostMapping
    @ResponseBody
    public ArtistEntity createArtist(@RequestBody ArtistEntity artist) {
        return artistService.createNewArtist(artist);
    }

    // Get an artist by ID
    @GetMapping("/{id}")
    @ResponseBody
    public ArtistEntity getArtistById(@PathVariable String id) {
        return artistService.getArtistById(id);
    }

    // Get an artist by Name
    @GetMapping("/search")
    @ResponseBody
    public ArtistEntity getArtistByName(@RequestParam String name) {
        return artistService.getArtistByName(name);
    }

    // Get all artists
    @GetMapping
    @ResponseBody
    public List<ArtistEntity> getAllArtists() {
        return artistService.getAllArtists();
    }

    // Update an existing artist
    @PutMapping("/{id}")
    @ResponseBody
    public ArtistEntity updateArtist(@PathVariable String id, @RequestBody ArtistEntity updatedArtist) {
        return artistService.updateArtist(id, updatedArtist);
    }

    // Delete an artist by ID
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteArtist(@PathVariable String id) {
        artistService.deleteArtistById(id);
    }

    // Get artists with price under a certain value
    @GetMapping("/price-under")
    @ResponseBody
    public List<ArtistEntity> getArtistsWithPriceUnder(
            @RequestParam String key, @RequestParam Number maxPrice) {
        return artistService.getArtistsWithPriceBelow(key, maxPrice);
    }
}
