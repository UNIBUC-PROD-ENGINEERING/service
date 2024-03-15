package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.client.TheMovieDbClient;
import ro.unibuc.hello.data.entity.Actor;
import ro.unibuc.hello.data.repository.MovieRepository;
import ro.unibuc.hello.data.entity.Movie;
import ro.unibuc.hello.dto.tmdb.ActorDto;
import ro.unibuc.hello.dto.tmdb.CastDto;
import ro.unibuc.hello.dto.tmdb.GenreDto;
import ro.unibuc.hello.dto.tmdb.MovieApiDto;
import ro.unibuc.hello.dto.tmdb.PagedApiResponseDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired private TheMovieDbClient theMovieDbClient;
    @Autowired private MovieRepository movieRepository;
    @Autowired private ActorService actorService;
    @Autowired private RoleService roleService;

    public List<MovieApiDto> searchMovie(String name) {
        final PagedApiResponseDto<MovieApiDto> firstPage = theMovieDbClient.searchMovie(name);
        final List<MovieApiDto> movies = firstPage.getResults();

        for (int i = 2; i <= firstPage.getTotalPages(); i++) {
            movies.addAll(theMovieDbClient.searchMovie(name, i).getResults());
        }

        return movies;
    }

    public Movie addMovie(Long tmdbId) {
        final Optional<Movie> existingMovie = movieRepository.findByTmdbId(tmdbId);
        if (existingMovie.isPresent()) {
            throw new IllegalArgumentException("Movie already exists");
        }
        final MovieApiDto movieApiDto = theMovieDbClient.getMovieById(tmdbId);
        final List<String> genres = movieApiDto.getGenres()
                .stream()
                .map(GenreDto::getName)
                .collect(Collectors.toList());

        final Long releaseYear = Long.valueOf(movieApiDto.getReleaseDate().split("-")[0]);
        final Movie movie = Movie.builder()
                .title(movieApiDto.getTitle())
                .description(movieApiDto.getOverview())
                .genres(genres)
                .year(releaseYear)
                .popularity(movieApiDto.getPopularity())
                .tmdbId(tmdbId)
                .build();

        final Movie savedMovie = movieRepository.save(movie);
        final List<ActorDto> cast = getActors(tmdbId);

        cast.stream()
                .map(this::getActorOrAdd)
                .forEach(actor -> roleService.addRole(actor, savedMovie));

        return savedMovie;
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("movie (id = %s)", id)));
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(String id) {
        if (!movieRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("movie (id = %s)", id));
        }
        roleService.deleteRolesByMovieId(id);
        movieRepository.deleteById(id);
    }

    private Actor getActorOrAdd(ActorDto actorDto) {
        return actorService.getActorByTmdbId(actorDto.getTmdbId())
                .orElseGet(() -> actorService.saveActor(actorDto));
    }

    private List<ActorDto> getActors(Long tmdbMovieId) {
        final CastDto cast = theMovieDbClient.getCastByMovieId(tmdbMovieId);
        return cast.getCast();
    }
}
