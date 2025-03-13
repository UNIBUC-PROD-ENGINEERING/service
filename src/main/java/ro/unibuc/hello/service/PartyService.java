package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.models.Party;
import ro.unibuc.hello.repositories.PartyRepository;
import java.util.List;

@Service
public class PartyService {

    private final PartyRepository partyRepository;

    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    public List<Party> getPartiesForUser(String userId) {
        return partyRepository.findByUserIdsContaining(userId);
    }

    public Party createParty(Party party) {
        return partyRepository.save(party);
    }
}
