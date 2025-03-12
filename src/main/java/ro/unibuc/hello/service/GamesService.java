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
            .map(entity -> new Game(entity.getId(), entity.getTitle(), entity.getTier()))
            .collect(Collectors.toList());
    }

    public Game saveGame(Game game){
        GameEntity entity = new GameEntity();
        entity.setTier(game.getTier());
        entity.setTitle(game.getTitle());
        gameRepository.save(entity);
        return new Game(entity.getTitle(), entity.getTier());
    }

    public List<Game> getGamesAvailable(int tier){
        List<GameEntity> entities = gameRepository.findAll();

        return entities.stream()
            .filter(entity -> entity.getTier() <= tier)
            .map(entity -> new Game(entity.getId(), entity.getTitle(), entity.getTier()))
            .collect(Collectors.toList());
    }
}
