package ro.unibuc.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.booking.data.ArtistEntity;
import ro.unibuc.booking.service.ArtistService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseBody
    public ArtistEntity createArtist(
            @RequestParam("artist") String artistJson, 
            @RequestParam MultipartFile[] photos) throws IOException {
        
        // Convert JSON string to ArtistEntity object
        ArtistEntity artist = objectMapper.readValue(artistJson, ArtistEntity.class);
        
        return artistService.createNewArtist(artist, photos);
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

  // Update an artist with optional new images
  @PutMapping("/{id}")
  @ResponseBody
  public ArtistEntity updateArtist(
          @PathVariable String id,
          @RequestParam("artist") String artistJson, // JSON as String
          @RequestParam(value = "photos", required = false) MultipartFile[] photos) throws IOException {
      
      ArtistEntity updatedArtist = objectMapper.readValue(artistJson, ArtistEntity.class);
      
      return artistService.updateArtist(id, updatedArtist, photos);
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

