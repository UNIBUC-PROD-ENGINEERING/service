package ro.unibuc.hello.controller;

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

import java.util.List;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    @ResponseBody
    public List<PlayerEntity> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @PostMapping("/create")
    @ResponseBody
    public PlayerEntity postPlayer(@RequestBody PlayerEntity newPlayer){
        return playerService.createPlayer(newPlayer);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public PlayerEntity updatePlayer(@RequestBody PlayerEntity newPlayer, @PathVariable String id) {
        return playerService.updatePlayer(id, newPlayer);
    }

    @GetMapping("/getTeamForPlayer")
    @ResponseBody
    public String getTeam(@RequestParam(name="name",required=false,defaultValue="LeBron James")String name){
        return playerService.getPlayerTeam(name);
    }

    @GetMapping("/getPlayer")
    @ResponseBody
    public String getPlayer(@RequestParam(name="name",required=false,defaultValue="LeBron James")String name){
        return playerService.getPlayer(name);
    }

    @DeleteMapping("/deletePlayerByName")
    @ResponseBody String deletePlayerByName(@RequestParam(name="name")String name){
        return playerService.deleteByName(name);
    }

    

}