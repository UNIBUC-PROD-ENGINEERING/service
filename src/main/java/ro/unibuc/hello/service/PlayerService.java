package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.PlayerRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public String getPlayerTeam(String name)throws EntityNotFoundException{
        PlayerEntity playerEntity=playerRepository.findByName(name);
        if (playerEntity==null){
            throw new EntityNotFoundException(name);
        }
        return playerEntity.getTeam();
    }

    public String addPlayer(PlayerEntity newPlayer){
        playerRepository.save(newPlayer);
        return "Player added";
    }

    public String getPlayer(String name)throws EntityNotFoundException{
        PlayerEntity playerEntity=playerRepository.findByName(name);
        if (playerEntity==null){
            throw new EntityNotFoundException(name);
        }
        return playerEntity.toString();
    }

    public void updatePlayer(
        String name, 
        String newName, 
        String newTeam, 
        double newPpg, 
        double newRpg, 
        double newApg
    )throws EntityNotFoundException{
        PlayerEntity playerEntity=playerRepository.findByName(name);
        if (playerEntity==null){
            throw new EntityNotFoundException(name);
        }
        if (!newName.isEmpty()) {
            playerEntity.setName(newName);
        }
        
        if (!newTeam.isEmpty()) {
            playerEntity.setTeam(newTeam);
        }
        
        if (newPpg != 0) {
            playerEntity.setPointsPerGame(newPpg);
        }
        
        if (newRpg != 0) {
            playerEntity.setReboundsPerGame(newRpg);
        }
        
        if (newApg != 0) {
            playerEntity.setAssistsPerGame(newApg);
        }
        playerRepository.save(playerEntity);
    }
}
