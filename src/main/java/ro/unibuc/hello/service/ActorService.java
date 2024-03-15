package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.entity.Actor;
import ro.unibuc.hello.data.repository.ActorRepository;
import ro.unibuc.hello.dto.tmdb.ActorDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public Actor getActorById(String id) {
        return actorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("actor (id = %s)", id)));
    }

    public Optional<Actor> getActorByTmdbId(Long tmdbId) {
        return actorRepository.findByTmdbId(tmdbId);
    }

    public Actor saveActor(ActorDto actorDto) {
        final Actor actor = Actor.builder()
                .name(actorDto.getName())
                .tmdbId(actorDto.getTmdbId())
                .build();
        return actorRepository.save(actor);
    }

    public void deleteActor(String id) {
        if (!actorRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("actor (id = %s)", id));
        }
        actorRepository.deleteById(id);
    }
}
