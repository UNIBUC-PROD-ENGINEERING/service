package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;

@SpringBootTest
@Tag("IT")
class GameServiceIntegrationTest {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameService gameService;

    @Test
    void test_getGameScore_ReturnsCorrectScore() {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId("90");
        gameEntity.setScore("3-2"); 
        gameRepository.save(gameEntity);
        String score = gameService.getGameScore("90");

        Assertions.assertEquals("3-2", score);
    }
}