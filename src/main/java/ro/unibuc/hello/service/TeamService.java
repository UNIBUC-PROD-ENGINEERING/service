package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.TeamEntity;
import ro.unibuc.hello.data.TeamRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    public String getTeamInfo(String name)throws EntityNotFoundException{
        TeamEntity teamEntity=teamRepository.findByName(name);
        if(teamEntity==null){
            throw new EntityNotFoundException(name);
        }
        return teamEntity.getTeamInfo();
    }
}
