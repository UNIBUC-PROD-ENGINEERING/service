package ro.unibuc.hello.controller;

import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.models.Party;
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
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    // GET: Retrieve a single party by ID
    @GetMapping("/{id}")
    public Party getPartyById(@PathVariable String id) {
        return partyRepository.findById(id).orElse(null);
    }

    // POST: Create a new party
    @PostMapping
    public Party createParty(@RequestBody Party party) {
        return partyRepository.save(party);
    }

    // PUT: Update an existing party
    @PutMapping("/{id}")
    public Party updateParty(@PathVariable String id, @RequestBody Party updatedParty) {
        updatedParty.setId(id);
        return partyRepository.save(updatedParty);
    }

    // DELETE: Remove a party
    @DeleteMapping("/{id}")
    public void deleteParty(@PathVariable String id) {
        partyRepository.deleteById(id);
    }
}
