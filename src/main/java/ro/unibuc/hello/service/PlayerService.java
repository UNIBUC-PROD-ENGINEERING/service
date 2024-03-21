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

    public PlayerEntity createPlayer(PlayerEntity newPlayer){return playerRepository.save(newPlayer);}

    public String getPlayerTeam(String name)throws EntityNotFoundException{
        PlayerEntity playerEntity=playerRepository.findByName(name);
        if (playerEntity==null){
            throw new EntityNotFoundException(name);
        }
        return playerEntity.getTeam();
    }

    public PlayerEntity updatePlayer(Integer id, PlayerEntity newPlayer){
        // Integer id2 = Integer.parseInt(id);
        System.err.println("Here");
        PlayerEntity dbPlayer = playerRepository.findById(id);
        System.out.println("??????????"+dbPlayer.getName());

        if (newPlayer.getName() != null && !newPlayer.getName().isEmpty()) {
            dbPlayer.setName(newPlayer.getName());
        }
        
        if (newPlayer.getTeam() != null && !newPlayer.getTeam().isEmpty()) {
            dbPlayer.setTeam(newPlayer.getTeam());
        }
        
        if (newPlayer.getPpg() != 0) {
            dbPlayer.setPointsPerGame(newPlayer.getPpg());
        }
        
        if (newPlayer.getRpg() != 0) {
            dbPlayer.setReboundsPerGame(newPlayer.getRpg());
        }
        
        if (newPlayer.getApg() != 0) {
            dbPlayer.setAssistsPerGame(newPlayer.getApg());
        }
        
        return playerRepository.save(dbPlayer);
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

    public String deleteByName(String name)throws EntityNotFoundException{
        PlayerEntity playerEntity=playerRepository.findByName(name);
        if (playerEntity==null){
            throw new EntityNotFoundException(name);
        }
        playerRepository.deleteById(playerEntity.getId());
        return "Player deleted succesfully";
    }
}
