package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.hello.data.loyalty.LoyaltyCardEntity;
import ro.unibuc.hello.service.LoyaltyCardService;

import java.util.List;
import java.util.Map;

@Controller
public class LoyaltyCardController {
    
    @Autowired
    private LoyaltyCardService loyaltyCardService;
    
    @PostMapping("/api/loyalty-cards/user/{userId}")
    @ResponseBody
    public LoyaltyCardEntity issueCard(@PathVariable String userId, 
                                     @RequestBody Map<String, String> requestBody) {
        try {
            LoyaltyCardEntity.CardType cardType = LoyaltyCardEntity.CardType.valueOf(requestBody.get("cardType"));
            return loyaltyCardService.issueCard(userId, cardType);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card type");
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @GetMapping("/api/loyalty-cards/{id}")
    @ResponseBody
    public LoyaltyCardEntity getCardById(@PathVariable String id) {
        try {
            return loyaltyCardService.getCardById(id);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @GetMapping("/api/loyalty-cards/user/{userId}")
    @ResponseBody
    public List<LoyaltyCardEntity> getCardsByUser(@PathVariable String userId) {
        try {
            return loyaltyCardService.getCardsByUser(userId);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @PutMapping("/api/loyalty-cards/{id}/upgrade")
    @ResponseBody
    public LoyaltyCardEntity upgradeCard(@PathVariable String id, 
                                       @RequestBody Map<String, String> requestBody) {
        try {
            LoyaltyCardEntity.CardType newType = LoyaltyCardEntity.CardType.valueOf(requestBody.get("newType"));
            return loyaltyCardService.upgradeCard(id, newType);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card type");
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @PostMapping("/api/loyalty-cards/{id}/points")
    @ResponseBody
    public LoyaltyCardEntity addPoints(@PathVariable String id, 
                                      @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer points = requestBody.get("points");
            if (points == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Points parameter is required");
            }
            return loyaltyCardService.addPoints(id, points);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @GetMapping("/api/loyalty-cards/{id}/discount")
    @ResponseBody
    public Map<String, Double> calculateDiscount(@PathVariable String id, 
                                                @RequestParam double amount) {
        try {
            double discount = loyaltyCardService.calculateDiscount(id, amount);
            return Map.of(
                "original", amount,
                "discount", discount,
                "final", amount - discount
            );
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @DeleteMapping("/api/loyalty-cards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable String id) {
        try {
            loyaltyCardService.deleteCard(id);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
}