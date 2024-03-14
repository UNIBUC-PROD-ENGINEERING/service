package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.entity.Movie;
import ro.unibuc.hello.dto.tmdb.MovieApiDto;
import ro.unibuc.hello.service.MovieService;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired private MovieService movieService;

    @GetMapping("/search")
    @ResponseBody
    public List<MovieApiDto> searchMovies(@RequestParam(name="name", defaultValue="") String name) {
        return movieService.searchMovie(name);
    }

    @GetMapping
    @ResponseBody
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public Movie addMovie(@PathVariable String id) {
        final Long tmdbId = Long.valueOf(id);
        return movieService.addMovie(tmdbId);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

}
