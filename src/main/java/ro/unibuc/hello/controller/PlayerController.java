package ro.unibuc.hello.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;

import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.service.PlayerService;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/create")
    @ResponseBody
    public PlayerEntity postPlayer(@RequestBody PlayerEntity newPlayer){
        return playerService.createPlayer(newPlayer);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public PlayerEntity updatePlayer(@RequestBody PlayerEntity newPlayer, @PathVariable Integer id) {
        System.out.println(newPlayer.getName());
        return playerService.updatePlayer(id, newPlayer);
    }

    // @GetMapping("/{id}")
    // @ResponseBody
    // public PlayerEntity

    @GetMapping("/getTeamForPlayer")
    @ResponseBody
    public String getTeam(@RequestParam(name="name",required=false,defaultValue="LeBron James")String name){
        return playerService.getPlayerTeam(name);
    }

    @GetMapping("/addPlayer")
    @ResponseBody
    public String addPlayer(
        @RequestParam(name = "id", required = false, defaultValue = "100") Integer id,
        @RequestParam(name = "name", required = false, defaultValue = "Dragos") String name,
        @RequestParam(name = "team", required = false, defaultValue = "defaultTeam") String team,
        @RequestParam(name = "ppg", required = false, defaultValue = "0") int ppg,
        @RequestParam(name = "rpg", required = false, defaultValue = "0") int rpg,
        @RequestParam(name = "apg", required = false, defaultValue = "0") int apg
    ) {
        PlayerEntity playerEntity = new PlayerEntity(id,name, team, ppg, rpg, apg);
        return playerService.addPlayer(playerEntity);
    }

    @GetMapping("/getPlayer")
    @ResponseBody
    public String getPlayer(@RequestParam(name="name",required=false,defaultValue="LeBron James")String name){
        return playerService.getPlayer(name);
    }

    @GetMapping("/updatePlayer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePlayerName(@RequestParam(name = "name", required = true) String name,
                                @RequestParam(name = "newName", required= false, defaultValue = "") String newName,
                                @RequestParam(name = "newTeam", required = false, defaultValue = "") String newTeam,
                                @RequestParam(name = "newPpg", required = false, defaultValue = "0") double newPpg,
                                @RequestParam(name = "newRpg", required = false, defaultValue = "0") double newRpg,
                                @RequestParam(name = "newApg", required = false, defaultValue = "0") double newApg){

        playerService.updatePlayer(name, newName, newTeam, newPpg, newRpg, newApg);
    }

    @DeleteMapping("/deletePlayerByName")
    @ResponseBody String deletePlayerByName(@RequestParam(name="name")String name){
        return playerService.deleteByName(name);
    }
     

}