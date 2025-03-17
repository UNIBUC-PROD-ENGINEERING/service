package ro.unibuc.hello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.service.PartyService;
import ro.unibuc.hello.data.TaskEntity;
import java.util.List;

@RestController
@RequestMapping("/parties")
public class PartyController {

    private final PartyRepository partyRepository;
    private final TaskRepository taskRepository;  // Repository pentru taskuri
    private final PartyService partyService;      // Adăugăm PartyService pentru logica de business

    // Injectăm dependențele
    public PartyController(PartyRepository partyRepository, TaskRepository taskRepository, PartyService partyService) {
        this.partyRepository = partyRepository;
        this.taskRepository = taskRepository;
        this.partyService = partyService;
    }

    // GET: Retrieve all parties
    @GetMapping
    public List<PartyEntity> getAllParties() {
        return partyRepository.findAll();
    }

    // GET: Retrieve a single party by ID
    @GetMapping("/{id}")
    public PartyEntity getPartyById(@PathVariable String id) {
        return partyRepository.findById(id).orElse(null);
    }

    // GET: Retrieve tasks for a party
    @GetMapping("/{partyId}/tasks")
    public List<TaskEntity> getTasksByParty(@PathVariable String partyId) {
        return taskRepository.findByPartyId(partyId);  // Metodă pentru a obține taskurile pentru petrecerea respectivă
    }

    // POST: Create a new party
    @PostMapping
    public PartyEntity createParty(@RequestBody PartyEntity party) {
        return partyRepository.save(party);
    }

    // PUT: Update an existing party
    @PutMapping("/{id}")
    public PartyEntity updateParty(@PathVariable String id, @RequestBody PartyEntity updatedParty) {
        updatedParty.setId(id);
        return partyRepository.save(updatedParty);
    }

    // DELETE: Remove a party
    @DeleteMapping("/{id}")
    public void deleteParty(@PathVariable String id) {
        partyRepository.deleteById(id);
    }

    // POST: Adăugăm un user la o petrecere
    @PostMapping("/{partyId}/addUser/{userId}")
    public ResponseEntity<?> addUserToParty(
            @PathVariable String partyId,
            @PathVariable String userId) {
        try {
            PartyEntity updatedParty = partyService.addUserToParty(partyId, userId);

            if (updatedParty != null) {
                return ResponseEntity.ok(updatedParty);
            } else {
                return ResponseEntity.status(400).body("Petrecerea sau utilizatorul nu există sau utilizatorul este deja adăugat.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare internă a serverului: " + e.getMessage());
        }
    }

    // POST: Actualizăm punctele petrecerii după completarea unui task
    @PostMapping("/{partyId}/user/{userId}/completeTask/{taskId}")
    public ResponseEntity<PartyEntity> completeTaskAndUpdatePartyPoints(
            @PathVariable String partyId,
            @PathVariable String userId,
            @PathVariable String taskId) {

    // Apelăm metoda din PartyService pentru a actualiza punctele petrecerii
    PartyEntity updatedParty = partyService.updatePartyPointsAfterTaskCompletion(partyId, userId, taskId);

    // Dacă petrecerea a fost actualizată cu succes
    if (updatedParty != null) {
        return ResponseEntity.ok(updatedParty); // Returnăm petrecerea actualizată
    } else {
        // Dacă petrecerea nu există sau userul nu este la petrecere
        return ResponseEntity.status(404).body(null);  // Returnăm un mesaj de eroare cu status 404
    }
}
}