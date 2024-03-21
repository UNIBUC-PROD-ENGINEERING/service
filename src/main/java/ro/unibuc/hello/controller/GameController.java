package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @RequestParam(name = "id", required = true) String id,
    @RequestParam(name = "date", required = true) String date,
    @RequestParam(name = "team1_id", required = true) int team1Id,
    @RequestParam(name = "team2_id", required = true) int team2Id,
    @RequestParam(name = "score", required = true) String score,
    @RequestParam(name = "spectators", required = true) int spectators
) {
    GameEntity gameEntity = new GameEntity(id,date, team1Id, team2Id, score, spectators);
    return gameService.addGame(gameEntity);
}

    @GetMapping("/getGame")
    @ResponseBody
    public String getGame(@RequestParam(name="id",required = false,defaultValue = "1")String id){
        return gameService.getGame(id);
    }

    @GetMapping("/getTeamsFromGame")
    @ResponseBody
    public String getTeamsFromGame(@RequestParam(name="id",required = false,defaultValue ="1" )String id){
        return gameService.getTeamFromGame(id);
    }

    @GetMapping("/updateGame")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(
        @RequestParam(name = "id", required = true) String id,
        @RequestParam(name = "newDate", required = false, defaultValue = "") String newDate,
        @RequestParam(name = "newTeam1_id", required = false, defaultValue = "0") int newTeam1_id,
        @RequestParam(name = "newTeam2_id", required = false, defaultValue = "0") int newTeam2_id,
        @RequestParam(name = "newScore", required = false, defaultValue = "") String newScore,
        @RequestParam(name = "newSpectators", required = false, defaultValue = "0") int newSpectators
    ) {
        gameService.updateGame(id, newDate, newTeam1_id, newTeam2_id, newScore, newSpectators);
    }
}
