package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.ClientEntity;
import ro.unibuc.hello.data.ClientRepository;
import ro.unibuc.hello.data.MovieEntity;
import ro.unibuc.hello.data.MovieRepository;
import ro.unibuc.hello.dto.ClientDTO;
import ro.unibuc.hello.dto.MovieDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private final AtomicLong counter = new AtomicLong();
    public List<MovieDTO> getAll() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> new MovieDTO(counter.incrementAndGet(), movie.getTitle(), movie.getYear(), movie.getDescription(), movie.getType()))
                .collect(Collectors.toList());
    }
    public ResponseEntity<MovieEntity> getMovieById(String id) {
        MovieEntity entity = movieRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException(id);
        }
        return ResponseEntity.ok().body(entity);
    }

    public ResponseEntity<MovieDTO> saveMovie(MovieEntity movie) {
        MovieEntity movieEntity = movieRepository.save(movie);
        MovieDTO movieDTO = new MovieDTO(counter.incrementAndGet(), movieEntity.getTitle(), movieEntity.getYear(), movieEntity.getDescription(), movieEntity.getType());
        return new ResponseEntity<>(movieDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<MovieEntity> updateMovieById(String id, MovieEntity updatedMovie) {
        MovieEntity movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            throw new EntityNotFoundException(id);
        }

        movie.setTitle(updatedMovie.getTitle());
        movie.setDescription(updatedMovie.getDescription());
        movie.setType(updatedMovie.getType());
        movie.setYear(updatedMovie.getYear());

        MovieEntity movieEntity = movieRepository.save(movie);
        return ResponseEntity.ok(movieEntity);
    }

    public ResponseEntity<String> deleteMovieById(String id) {
        MovieEntity movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            throw new EntityNotFoundException(id);
        }
        movieRepository.delete(movie);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
