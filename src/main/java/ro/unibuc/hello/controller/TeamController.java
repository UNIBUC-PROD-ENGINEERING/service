package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.TeamEntity;
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

    @GetMapping("/addTeam")
    @ResponseBody
    public String getTeamInfo(
    @RequestParam(name = "name", required = false, defaultValue = "Brooklyn Nets") String name,
    @RequestParam(name = "yearFounded", required = false, defaultValue = "0") int yearFounded,
    @RequestParam(name = "coach", required = false, defaultValue = "Unknown") String coach,
    @RequestParam(name = "players", required = false) List<Integer> playersIds
) {
    TeamEntity teamEntity =new TeamEntity(name, playersIds, yearFounded, coach);
    return teamService.addTeam(teamEntity);
}
}
