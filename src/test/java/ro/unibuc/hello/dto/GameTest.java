package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ro.unibuc.hello.dto.Game;

class GameTest {

    Game game = new Game("Balatro", 1);

    @Test
    void testTitleGetter(){
        Assertions.assertSame("Balatro", game.getTitle());
    }
    @Test
    void testTierGetter(){
        Assertions.assertEquals(1, game.getTier());
    }
    @Test
    void testTitleSetter(){
        game.setTitle("Half-Life");
        Assertions.assertSame("Half-Life", game.getTitle());
    }
    @Test
    void testTierSetter(){
        game.setTier(3);
        Assertions.assertEquals(3, game.getTier());
    }

}