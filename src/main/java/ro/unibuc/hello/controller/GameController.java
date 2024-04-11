package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @GetMapping("/getScore")
    @ResponseBody
    public String getScore(@RequestParam(name="id",required = true)String id){
        return gameService.getGameScore(id);
    }

    @GetMapping
    @ResponseBody
    public String getGame(@RequestParam(name="id",required = false,defaultValue = "1")String id){
        return gameService.getGame(id);
    }

    @GetMapping("/getTeamsFromGame")
    @ResponseBody
    public String getTeamsFromGame(@RequestParam(name="id",required = false,defaultValue ="1" )String id){
        return gameService.getTeamFromGame(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public GameEntity update(@PathVariable String id, @RequestBody GameEntity newGame){
        return gameService.updateGame(id, newGame);
    }

    @PostMapping
    @ResponseBody
    public GameEntity create(@RequestBody GameEntity newGame){
        return gameService.create(newGame);
    }

    @DeleteMapping
    @ResponseBody
    public String deleteTeamById(@RequestParam(name="id",required=true)String id){
        return gameService.deleteById(id);
    }
}
