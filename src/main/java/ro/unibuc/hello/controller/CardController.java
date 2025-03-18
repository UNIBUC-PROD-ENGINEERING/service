package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.entity.Card;
import ro.unibuc.hello.service.CardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/{id}")
    public Optional<Card> getCardById(@PathVariable String id) {
        return cardService.getCardById(id);
    }

    @PostMapping("/create")
    public Card createCard(@RequestBody Card card) {
        System.out.println("Received Card: " + card); // 🔍 Debugging Log
        return cardService.saveCard(card);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable String id) {
        cardService.deleteCard(id);
    }
}
