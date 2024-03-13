package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.service.PlayerService;

@Controller
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("/getTeam")
    @ResponseBody
    public String getTeam(@RequestParam(name="name",required=false,defaultValue="LeBron James")String name){
        return playerService.getPlayerTeam(name);
    }

    @GetMapping("/addPlayer")
    @ResponseBody
    public String addPlayer(
        @RequestParam(name = "name", required = false, defaultValue = "Dragos") String name,
        @RequestParam(name = "team", required = false, defaultValue = "defaultTeam") String team,
        @RequestParam(name = "ppg", required = false, defaultValue = "0") int ppg,
        @RequestParam(name = "rpg", required = false, defaultValue = "0") int rpg,
        @RequestParam(name = "apg", required = false, defaultValue = "0") int apg
    ) {
        PlayerEntity playerEntity = new PlayerEntity(name, team, ppg, rpg, apg);
        return playerService.addPlayer(playerEntity);
    }
}
