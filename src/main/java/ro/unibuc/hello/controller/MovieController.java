package ro.unibuc.hello.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.hello.data.MovieRepository;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
}
