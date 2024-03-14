package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.entity.Actor;
import ro.unibuc.hello.data.repository.ActorRepository;
import ro.unibuc.hello.dto.tmdb.ActorDto;

import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository movieRepository;

    public Actor getActorById(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public Optional<Actor> getActorByTmdbId(Long tmdbId) {
        return movieRepository.findByTmdbId(tmdbId);
    }

    public Actor saveActor(ActorDto actorDto) {
        final Actor actor = Actor.builder()
                .name(actorDto.getName())
                .tmdbId(actorDto.getTmdbId())
                .build();
        return movieRepository.save(actor);
    }

    public void deleteActor(Long id) {
        movieRepository.deleteById(id);
    }
}
