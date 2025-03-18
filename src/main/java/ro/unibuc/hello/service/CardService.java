package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.entity.Card;
import ro.unibuc.hello.repository.CardRepository;
import ro.unibuc.hello.entity.BankAccount; 
import ro.unibuc.hello.repository.BankAccountRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public CardService(CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> getCardById(String id) {
        return cardRepository.findById(id);
    }

    public List<Card> getCardsByBankAccountId(String bankAccountId) {
        return cardRepository.findByBankAccountId(bankAccountId);
    }

    public Card saveCard(Card card) {
        Optional<BankAccount> bankAccountOpt = bankAccountRepository.findById(card.getBankAccountId());
        if (bankAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("BankAccount ID not found: " + card.getBankAccountId());
        }

        return cardRepository.save(card);
    }

    public void deleteCard(String id) {
        cardRepository.deleteById(id);
    }
}

