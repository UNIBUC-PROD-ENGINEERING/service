package ro.unibuc.hello.service;

import ro.unibuc.hello.data.Game;
import ro.unibuc.hello.data.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(String id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));
    }

    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    public Game updateGame(String id, Game gameDetails) {
        Game game = getGameById(id);

        game.setName(gameDetails.getName());
        game.setPlatform(gameDetails.getPlatform());
        game.setGenre(gameDetails.getGenre());
        game.setReleasedYear(gameDetails.getReleasedYear());

        return gameRepository.save(game);
    }

    public void deleteGame(String id) {
        Game game = getGameById(id);
        gameRepository.delete(game);
    }

    public void deleteAllGames() {
        gameRepository.deleteAll();
    }
}