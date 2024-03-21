package ro.unibuc.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.PlayerRepository;
import ro.unibuc.hello.data.TeamEntity;
import ro.unibuc.hello.data.TeamRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public String getTeamInfo(String name)throws EntityNotFoundException{
        TeamEntity teamEntity=teamRepository.findByName(name);
        if(teamEntity==null){
            throw new EntityNotFoundException(name);
        }
        return teamEntity.getTeamInfo();
    }

    public String getTeam(String name)throws EntityNotFoundException{
        TeamEntity teamEntity=teamRepository.findByName(name);
        if(teamEntity==null){
            throw new EntityNotFoundException(name);
        }
        return teamEntity.toString();
    } 

    public String getBestPlayer(String name) throws EntityNotFoundException {
        TeamEntity teamEntity = teamRepository.findByName(name);
        if (teamEntity == null) {
            throw new EntityNotFoundException(name);
        }
        List<Integer> ids = teamEntity.getPlayers();
        double max = 0;
        PlayerEntity bestPlayer = null;
        for (int id : ids) {
            Optional<PlayerEntity> ope = playerRepository.findById(String.valueOf(id));
            if (ope.isPresent()) {  // Check if the optional has a value
                PlayerEntity player = ope.orElseThrow();  // Convert to PlayerEntity
                if (player.getPoints_per_game() > max) {
                    max = player.getPoints_per_game();
                    bestPlayer = player;
                }
            }
        }
        // Now you have the best player
        return "Best player: " + (bestPlayer != null ? bestPlayer.toString() : "No player found");
    }

    public TeamEntity updateTeam(String id, TeamEntity newTeam){
        TeamEntity dbTeam = teamRepository.findById(id).get();

        if (newTeam.getName() != null && !newTeam.getName().isEmpty()) {dbTeam.setName(newTeam.getName());}
        if (newTeam.getCoach() != null && !newTeam.getCoach().isEmpty()) {dbTeam.setCoach(newTeam.getCoach());}
        if (newTeam.getYearFounded() != 0) {dbTeam.setYearFounded(newTeam.getYearFounded());}
        if (newTeam.getPlayers() != null) {dbTeam.setPlayers(newTeam.getPlayers());}
        
        return teamRepository.save(dbTeam);
    }

    public TeamEntity create(TeamEntity newTeam){
        return teamRepository.save(newTeam);
    }

}
