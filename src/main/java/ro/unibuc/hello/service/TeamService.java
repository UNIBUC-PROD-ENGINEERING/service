package ro.unibuc.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public String addTeam(TeamEntity newTeam){
        teamRepository.save(newTeam);
        return "Team added";
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
                if (player.getPpg() > max) {
                    max = player.getPpg();
                    bestPlayer = player;
                }
            }
        }
        // Now you have the best player
        return "Best player: " + (bestPlayer != null ? bestPlayer.toString() : "No player found");
    }

    public void updateTeam(String id, String newName, int newYearFounded, String newCoach) throws EntityNotFoundException {
        TeamEntity teamEntity = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));

        if (newName != null && !newName.isEmpty()) {
            teamEntity.setName(newName);
        }

        if (newYearFounded != 0) {
            teamEntity.setYearFounded(newYearFounded);
        }

        if (newCoach != null && !newCoach.isEmpty()) {
            teamEntity.setCoach(newCoach);
        }

        teamRepository.save(teamEntity);
    }

    public String deleteByName(String name)throws EntityNotFoundException{
        TeamEntity teamEntity=teamRepository.findByName(name);
        if (teamEntity==null){
            throw new EntityNotFoundException(name);
        }
        teamRepository.deleteById(teamEntity.getId());
        return "Team deleted succesfully";
    }
}
