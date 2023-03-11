package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/movieDescription")
    @ResponseBody
    public Movie info(@RequestParam(name="title", required=true, defaultValue = "Avatar") String title) throws EntityNotFoundException{
        return movieService.getMovieDescription(title);
    }
}
