package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GamesService;
import ro.unibuc.hello.service.GreetingsService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class GamesController {

    @Autowired
    private GamesService gamesService;


    @GetMapping("/games")
    @ResponseBody
    public List<Game> getGames(
        @RequestParam("tier") Optional<Integer> tier
    ) {
        if(tier.isPresent()){
            return gamesService.getGamesAvailable(tier.get());
        }
        
        return gamesService.getAllGames();
    }

    @PostMapping("/add-game")
    @ResponseBody
    public Game saveGame(
        @RequestParam(name="title", required=true) String title,
        @RequestParam(name="tier", required=true) int tier
    ){
        Game game = new Game(title, tier);
        return gamesService.saveGame(game);
    }

}

