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

@Controller
public class GamesController {

    @Autowired
    private GamesService gamesService;


    @GetMapping("/games")
    @ResponseBody
    public List<Game> getAllGames() {
        return gamesService.getAllGames();
    }

}

