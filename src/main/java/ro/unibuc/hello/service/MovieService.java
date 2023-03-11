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

    public Movie getMovieDescription(String title) throws EntityNotFoundException {
        MovieEntity entity = movieRepository.findByTitle(title);
        if(entity == null){
            throw new EntityNotFoundException(title);
        }
        return new Movie(entity.id, entity.title, entity.description, entity.runtime);
    }

}
