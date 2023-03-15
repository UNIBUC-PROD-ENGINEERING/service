package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.MovieEntity;
import ro.unibuc.hello.dto.MovieDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.MovieService;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    @ResponseBody
    public List<MovieDTO> getAllMovies() {
        return movieService.getAll();
    }

    @PostMapping(value = "/movies")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieEntity movie) {
        return movieService.saveMovie(movie);
    }

    @GetMapping("/movies/{id}")
    @ResponseBody
    public ResponseEntity<MovieEntity> getMovieById(@PathVariable(value = "id") String id) throws EntityNotFoundException {
        return movieService.getMovieById(id);
    }

    @PutMapping("/movies/{id}")
    @ResponseBody
    public ResponseEntity<MovieEntity> updateMovieById(@PathVariable(value = "id") String id, @RequestBody MovieEntity updatedMovie) throws EntityNotFoundException {
        return movieService.updateMovieById(id, updatedMovie);
    }

    @DeleteMapping("/movies/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteMovieById(@PathVariable(value = "id") String id) throws EntityNotFoundException {
        return movieService.deleteMovieById(id);
    }
}
