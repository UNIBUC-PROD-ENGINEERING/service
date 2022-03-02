package ro.unibuc.hello.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public Greeting showAll(@RequestParam(name="title", required=false, defaultValue="The Scream") String title) {
        ArtworkEntity entity = artworkRepository.findByTitle(title);
        return new Greeting(counter.incrementAndGet(), String.format(artworkTemplate, entity.title,
                    entity.artist, entity.description, entity.image));
    }

}
