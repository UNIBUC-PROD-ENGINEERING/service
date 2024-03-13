package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.service.GameService;

@Controller
public class GameController {
    @Autowired
    private GameService gameService;
    @GetMapping("/getScore")
    @ResponseBody
    public String getScore(@RequestParam(name="id",required = true)String id){
        return gameService.getGameScore(id);
    }


    @GetMapping("/addGame")
    @ResponseBody
    public String addGame(
    @RequestParam(name = "date", required = true) String date,
    @RequestParam(name = "team1_id", required = true) int team1Id,
    @RequestParam(name = "team2_id", required = true) int team2Id,
    @RequestParam(name = "score", required = true) String score,
    @RequestParam(name = "spectators", required = true) int spectators
) {
    GameEntity gameEntity = new GameEntity(date, team1Id, team2Id, score, spectators);
    return gameService.addGame(gameEntity);
}

    @GetMapping("/getGame")
    @ResponseBody
    public String getGame(@RequestParam(name="id",required = false,defaultValue = "65f1fbf8662c2a2968ceda62")String id){
        return gameService.getGame(id);
    }
}
