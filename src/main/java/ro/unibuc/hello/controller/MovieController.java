package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.MovieRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Movie;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.HelloWorldService;
import ro.unibuc.hello.service.MovieService;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/getMovieByTitle")
    @ResponseBody
    public Movie getMovieByTitle(@RequestParam(name="title", required=true, defaultValue = "Avatar") String title) throws EntityNotFoundException{
        return movieService.getMovieByTitle(title);
    }

    @PostMapping("/addMovie")
    @ResponseBody
    public boolean addMovie(@RequestParam(name="title", required = true) String title, @RequestParam(name="description", required = true) String description, @RequestParam(name="runtime", required = true) Integer runtime){
        return movieService.addMovie(new Movie(title, description, runtime));
    }
}
