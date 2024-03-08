package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
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
}
