package ro.unibuc.hello.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.MovieEntity;
import ro.unibuc.hello.data.MovieRepository;
import ro.unibuc.hello.dto.Movie;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie getMovieByTitle(String title) throws EntityNotFoundException {
        MovieEntity entity = movieRepository.findByTitle(title);
        if(entity == null){
            throw new EntityNotFoundException(title);
        }
        return new Movie(entity.title, entity.description, entity.runtime);
    }
    public boolean addMovie(Movie movie){
        MovieEntity entity = new MovieEntity(movie.title, movie.description, movie.runtime);
        movieRepository.save(entity);
        return true;
    }

    public boolean deleteMovie(String title){
        movieRepository.deleteByTitle(title);
        return true;
    }
}
