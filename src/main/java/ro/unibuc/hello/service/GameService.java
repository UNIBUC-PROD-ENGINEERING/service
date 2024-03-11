package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    public String getGameScore(String id)throws EntityNotFoundException{
        GameEntity gameEntity = gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with ID: " + id));
        
        // Assuming the game score is stored as a String in the GameEntity
        return gameEntity.getScore();
        
    }
}
