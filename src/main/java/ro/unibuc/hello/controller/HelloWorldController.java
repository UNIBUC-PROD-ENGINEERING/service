package ro.unibuc.hello.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ArtworkEntity;
import ro.unibuc.hello.data.ArtworkRepository;
import ro.unibuc.hello.dto.Greeting;

@Controller
public class HelloWorldController {

    @Autowired
    private ArtworkRepository artworkRepository;

    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s %s!";
    private static final String artworkTemplate = "%s by %s \n %s \n Link to image: ";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(helloTemplate, name));
    }

    @GetMapping("/info")
    @ResponseBody
    public Greeting listAll(@RequestParam(name="title", required=false, defaultValue="Overview") String title) {
        ArtworkEntity entity = artworkRepository.findByTitle(title);
        return new Greeting(counter.incrementAndGet(), String.format(informationTemplate, entity.title, entity.description, entity.image));
    }

    @GetMapping("/gallery")
    @ResponseBody
    public ResponseEntity<List> showAll(@RequestParam(name="title", required=false) String title) {

        try
        {
            List listOfArtworks = new ArrayList();
            if(title == null || title.isEmpty())
            {
                artworkRepository.findAll().forEach(listOfArtworks::add);
            }
            else
            {
                artworkRepository.findByTitleContaining(title).forEach(listOfArtworks::add);
            }

            if(listOfArtworks.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listOfArtworks, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/gallery/{id}")
    public ResponseEntity getArtById(@PathVariable("id") String id)
    {
        try
        {
            Optional artworkOptional = artworkRepository.findById(id);
            return new ResponseEntity<>(artworkOptional.get(), HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/gallery")
    @ResponseBody
    public ResponseEntity addArtToGallery(@RequestBody ArtworkEntity Artwork)
    {
        try
        {
            ArtworkEntity createdArt = artworkRepository.save(new ArtworkEntity(Artwork.getId(),Artwork.getTitle(), Artwork.getArtist(),
                    Artwork.getDescription(),Artwork.getImage()));
            System.out.println(createdArt);
            return new ResponseEntity<>(createdArt, HttpStatus.CREATED);

        }
        catch (Exception e)
        {
            System.out.println(e);

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/gallery/{id}")
    @Transactional
    @ResponseBody
    public ResponseEntity updateAnArtwork(@PathVariable("id") String id, @RequestBody ArtworkEntity Artwork)
    {
        Optional artworkOptional = artworkRepository.findById(id);

        if(artworkOptional.isPresent())
        {
            ArtworkEntity updatedArtwork = (ArtworkEntity) artworkOptional.get();
            updatedArtwork.setId(Artwork.getId());
            updatedArtwork.setTitle(Artwork.getTitle());
            updatedArtwork.setArtist(Artwork.getArtist());
            updatedArtwork.setDescription(Artwork.getDescription());
            return new ResponseEntity<>(artworkRepository.save(updatedArtwork), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/gallery/{id}")
    @Transactional
    public ResponseEntity deleteAnArtwork(@PathVariable("id") String id)
    {
        try
        {
            artworkRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
