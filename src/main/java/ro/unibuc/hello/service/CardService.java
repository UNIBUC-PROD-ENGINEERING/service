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

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
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

    public Card saveCard(String bankAccountId, Card card) {
        // ✅ Fix incorrect class name
        Optional<BankAccount> bankAccountOpt = bankAccountRepository.findById(bankAccountId);
        if (bankAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("Bank account not found with ID: " + bankAccountId);
        }

        BankAccount bankAccount = bankAccountOpt.get();
        card.setBankAccountId(bankAccount.getId()); // ✅ Link the card to the bank account

        return cardRepository.save(card);
    }

    public void deleteCard(String id) {
        cardRepository.deleteById(id);
    }

    
}
