package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class GamesService {
    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAllGames(){
        List<GameEntity> entities = gameRepository.findAll();
        return entities.stream()
            .map(entity -> new Game(entity.getTitle(), entity.getTier()))
            .collect(Collectors.toList());
    }
}
