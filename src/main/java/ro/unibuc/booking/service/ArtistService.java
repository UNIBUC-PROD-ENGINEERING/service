package ro.unibuc.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ro.unibuc.booking.data.ArtistEntity;
import ro.unibuc.booking.data.ArtistRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public ArtistEntity createNewArtist(ArtistEntity artist) {
       return artistRepository.save(artist);
    }

    public ArtistEntity getArtistById(String id) {
        return artistRepository.findById(id).orElse(null);
    }

    public ArtistEntity getArtistByName(String name) {
        return artistRepository.findByName(name);
    }

    public List<ArtistEntity> getAllArtists() {
        return artistRepository.findAll();
    }

    public void deleteArtistById(String id) {
        artistRepository.deleteById(id);
    }

    // Update Artist
    public ArtistEntity updateArtist(String id, ArtistEntity updatedArtist) {
        Optional<ArtistEntity> existingArtistOpt = artistRepository.findById(id);
        if (existingArtistOpt.isPresent()) {
            ArtistEntity existingArtist = existingArtistOpt.get();
            existingArtist.setName(updatedArtist.getName());
            existingArtist.setPhotos(updatedArtist.getPhotos());
            existingArtist.setDescription(updatedArtist.getDescription());
            existingArtist.setPrices(updatedArtist.getPrices());
            existingArtist.setEventsContent(updatedArtist.getEventsContent());
            return artistRepository.save(existingArtist);
        }
        return null;
    }

    // Get Artists with Price Below a Certain Value
    public List<ArtistEntity> getArtistsWithPriceBelow(String key, Number maxPrice) {
        try{
        return artistRepository.findByPriceUnder(key, maxPrice);
        } catch (Exception e) {
            return null;
        }
    }
}
