package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.UserEntity;  // Importă UserEntity pentru a lucra cu utilizatori
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.repositories.UserRepository;  // Importă UserRepository
import java.util.ArrayList;  // Importă pentru a inițializa lista dacă e null
import java.util.List;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // Constructor pentru injectarea dependențelor
    public PartyService(PartyRepository partyRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public PartyEntity addUserToParty(String partyId, String userId) {
        PartyEntity party = partyRepository.findById(partyId).orElse(null);
        
        if (party != null) {
            // Verificăm dacă lista `userIds` este null și o inițializăm
            if (party.getUserIds() == null) {
                party.setUserIds(new ArrayList<>());
            }
    
            // Adăugăm utilizatorul doar dacă nu este deja în listă
            if (!party.getUserIds().contains(userId)) {
                party.getUserIds().add(userId);
                return partyRepository.save(party);  // Salvăm petrecerea actualizată
            }
        }
        return null;
    }
    

    public PartyEntity updatePartyPointsAfterTaskCompletion(String partyId, String userId, String taskId) {
        // Căutăm petrecerea
        PartyEntity partyEntity = partyRepository.findById(partyId).orElse(null);
    
        // Verificăm dacă petrecerea există
        if (partyEntity != null) {
            // Căutăm taskul completat
            TaskEntity taskEntity = taskRepository.findById(taskId).orElse(null);
    
            // Verificăm dacă taskul există și este completat
            if (taskEntity != null && taskEntity.isCompleted()) {
                // Adăugăm punctele taskului la petrecerea respectivă
                int taskPoints = taskEntity.getPoints();
                partyEntity.setPartyPoints(partyEntity.getPartyPoints() + taskPoints);
    
                // Căutăm utilizatorul care a completat taskul
                UserEntity userEntity = userRepository.findById(userId).orElse(null);
    
                // Verificăm dacă utilizatorul există
                if (userEntity != null) {
                    // Adăugăm punctele taskului la utilizator
                    userEntity.setPoints(userEntity.getPoints() + taskPoints);
    
                    // Salvăm utilizatorul actualizat
                    userRepository.save(userEntity);
                }
    
                // Salvăm petrecerea actualizată
                return partyRepository.save(partyEntity);
            }
        }
    
        // Dacă petrecerea, taskul sau utilizatorul nu există, returnăm null
        return null;
    }


    public List<PartyEntity> getAllParties() {
        return partyRepository.findAll();
    }

    public List<PartyEntity> getPartiesForUser(String userId) {
        return partyRepository.findByUserIdsContaining(userId);
    }

    public PartyEntity createParty(PartyEntity party) {
        return partyRepository.save(party);
    }

    public PartyEntity getPartyById(String id) {
        return partyRepository.findById(id).orElse(null);
    }

    public PartyEntity saveParty(PartyEntity party) {
        return partyRepository.save(party);  // Salvează petrecerea actualizată
    }
}
