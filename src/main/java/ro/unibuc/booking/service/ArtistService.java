package ro.unibuc.booking.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.unibuc.booking.data.ArtistEntity;
import ro.unibuc.booking.data.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    
    @Autowired
    private Bucket bucket;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    public ArtistEntity createNewArtist(ArtistEntity artist, MultipartFile[] photos) throws IOException {
        if (photos != null && photos.length > 0) {
            List<String> photoUrls = uploadImagesToFirebase(photos);
            artist.setPhotos(photoUrls);
        }
        return artistRepository.save(artist);
    }

    public ArtistEntity updateArtist(String id, ArtistEntity updatedArtist, MultipartFile[] photos) throws IOException {
        Optional<ArtistEntity> existingArtistOpt = artistRepository.findById(id);
        if (existingArtistOpt.isPresent()) {
            ArtistEntity existingArtist = existingArtistOpt.get();
            existingArtist.setName(updatedArtist.getName());
            existingArtist.setDescription(updatedArtist.getDescription());
            existingArtist.setPrices(updatedArtist.getPrices());
            existingArtist.setEventsContent(updatedArtist.getEventsContent());

            if (photos != null && photos.length > 0) {
                existingArtist.setPhotos(uploadImagesToFirebase(photos));
            }
            return artistRepository.save(existingArtist);
        }
        return null;
    }

    private List<String> uploadImagesToFirebase(MultipartFile[] photos) throws IOException {
        List<String> photoUrls = new ArrayList<>();
        for (MultipartFile photo : photos) {
            String fileName = UUID.randomUUID().toString() + "-" + photo.getOriginalFilename();
            if (!isValidExtension(fileName)) {
                throw new IllegalArgumentException("Invalid file extension: " + fileName);
            }
            Blob blob = bucket.create(fileName, photo.getBytes(), photo.getContentType());
            String publicUrl = "https://storage.googleapis.com/" + bucket.getName() + "/" + fileName;
            photoUrls.add(publicUrl);
        }
        return photoUrls;
    }

    private boolean isValidExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return false;
        }
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
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

    public List<ArtistEntity> getArtistsWithPriceBelow(String key, Number maxPrice) {
        return artistRepository.findByPriceUnder(key, maxPrice);
    }
}
