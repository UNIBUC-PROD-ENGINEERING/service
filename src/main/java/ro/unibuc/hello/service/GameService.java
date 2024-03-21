package ro.unibuc.hello.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.data.TeamEntity;
import ro.unibuc.hello.data.TeamRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TeamRepository teamRepository;

    public String getGameScore(String id)throws EntityNotFoundException{
        GameEntity gameEntity = gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with ID: " + id));
        
        // Assuming the game score is stored as a String in the GameEntity
        return gameEntity.getScore();
        
    }

    public String addGame(GameEntity newGame){
        gameRepository.save(newGame);
        return "Game added";
    }
    public String getGame(String id)throws EntityNotFoundException{
        Optional<GameEntity> gameEntity=gameRepository.findById(id);
        if(gameEntity==null){
            throw new EntityNotFoundException(id);
        }
        return gameEntity.toString();
    }

    public String getTeamFromGame(String id)throws EntityNotFoundException{
        GameEntity gameEntity = gameRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Game with ID " + id + " not found"));

        
        int team1_id=gameEntity.getTeam1_id();
        int team2_id=gameEntity.getTeam2_id();

        TeamEntity team1Entity = teamRepository.findById(String.valueOf(team1_id))
        .orElseThrow(() -> new EntityNotFoundException("Team with ID " + team1_id + " not found"));

        TeamEntity team2Entity = teamRepository.findById(String.valueOf(team2_id))
        .orElseThrow(() -> new EntityNotFoundException("Team with ID " + team2_id + " not found"));

        return("Game details:\n"+gameEntity.toString()+"Team 1 details\n"+team1Entity.getTeamInfo()+"Team 2 details\n"+team2Entity.getTeamInfo());
        
    }

    public void updateGame(String id, String newDate, int newTeam1_id, int newTeam2_id, String newScore, int newSpectators) throws EntityNotFoundException {
        GameEntity gameEntity = gameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));

        if (!newDate.isEmpty()) {
            gameEntity.setDate(newDate);
        }

        if (newTeam1_id != 0 && newTeam1_id != newTeam2_id ) { //&& teamRepository.findById(newTeam1_id) != null
            gameEntity.setTeam1_id(newTeam1_id);
        }

        if (newTeam2_id != 0 && newTeam1_id != newTeam2_id ) { //&& teamRepository.findById(newTeam2_id) != null
            gameEntity.setTeam2_id(newTeam2_id);
        }

        if (!newScore.isEmpty()) {
            gameEntity.setScore(newScore);
        }

        if (newSpectators != 0) {
            gameEntity.setSpectators(newSpectators);
        }

        gameRepository.save(gameEntity);
    }
}
