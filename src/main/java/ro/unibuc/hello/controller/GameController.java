package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

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
}
