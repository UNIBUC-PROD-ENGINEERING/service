package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.PartyRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/parties")
public class PartyController {
    private final PartyRepository partyRepository;

    public PartyController(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
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
}
