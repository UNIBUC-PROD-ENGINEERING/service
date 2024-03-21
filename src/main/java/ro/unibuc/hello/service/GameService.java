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

    public GameEntity updateGame(String id, GameEntity newGame){
        GameEntity dbGame = gameRepository.findById(id).get();

        if (newGame.getDate() != null && !newGame.getDate().isEmpty()) {dbGame.setDate(newGame.getDate());}
        if (newGame.getScore() != null && !newGame.getScore().isEmpty()) {dbGame.setScore(newGame.getScore());}
        if (newGame.getSpectators() != 0) {dbGame.setSpectators(newGame.getSpectators());}
        if (newGame.getTeam1_id() != 0) {dbGame.setTeam1_id(newGame.getTeam1_id());}
        if (newGame.getTeam2_id() != 0) {dbGame.setTeam2_id(newGame.getTeam2_id());}

        return gameRepository.save(dbGame);
    }

    public GameEntity create(GameEntity newGame){
        return gameRepository.save(newGame);
    }

    public String deleteById(String id)throws EntityNotFoundException{
        GameEntity gameEntity=gameRepository.findById(id).get();
        if (gameEntity==null){
            throw new EntityNotFoundException(id);
        }
        gameRepository.deleteById(gameEntity.getId());
        return "Team deleted succesfully";
    }
}
