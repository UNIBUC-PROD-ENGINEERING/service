package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.service.TeamService;

@Controller
public class TeamController {
    @Autowired
    private TeamService teamService;
    @GetMapping("/getTeamInfo")
    @ResponseBody
    public String getTeamInfo(@RequestParam(name="name",required=false,defaultValue = "Brooklyn Nets")String name){
        return teamService.getTeamInfo(name);
    }
}
