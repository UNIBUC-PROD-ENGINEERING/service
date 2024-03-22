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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ro.unibuc.hello.data.TeamEntity;
import ro.unibuc.hello.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/getTeamInfo")
    @ResponseBody
    public String getTeamInfo(
            @RequestParam(name = "name", required = false, defaultValue = "Brooklyn Nets") String name) {
        return teamService.getTeamInfo(name);
    }

    @GetMapping("/getTeam")
    @ResponseBody
    public String getTeam(@RequestParam(name="name",required = false,defaultValue = "Los Angeles Lakers") String name){
        return teamService.getTeam(name);
    }

    @GetMapping("/getBestPlayer")
    @ResponseBody
    public String getBestPlayer(@RequestParam(name="name",required = false,defaultValue = "Los Angeles Lakers") String name){
        return teamService.getBestPlayer(name);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public TeamEntity update(@PathVariable String id, @RequestBody TeamEntity newTeam){
        return teamService.updateTeam(id, newTeam);
    }

    @PostMapping("/create")
    @ResponseBody
    public TeamEntity create(@RequestBody TeamEntity newTeam){
        return teamService.create(newTeam);
    }

    @DeleteMapping("/deleteTeamByName")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeamByName(@RequestParam(name="name",required=true)String name){
        teamService.deleteByName(name);
    }

    @DeleteMapping("/deleteTeamByName")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeamByName(@RequestParam(name="name",required=true)String name){
        teamService.deleteByName(name);
    }
}
