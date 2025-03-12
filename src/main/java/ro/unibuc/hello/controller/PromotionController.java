package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.hello.data.promotions.PromotionEntity;
import ro.unibuc.hello.service.PromotionService;

import java.util.List;

@Controller
@RequestMapping("/api/promotions")
public class PromotionController {
    
    @Autowired
    private PromotionService promotionService;
    
    @PostMapping
    @ResponseBody
    public PromotionEntity createPromotion(@RequestBody PromotionEntity promotion) {
        try {
            return promotionService.createPromotion(promotion);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
        }
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public PromotionEntity getPromotionById(@PathVariable String id) {
        try {
            return promotionService.getPromotionById(id);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @GetMapping
    @ResponseBody
    public List<PromotionEntity> getAllPromotions() {
        try {
            return promotionService.getAllPromotions();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
        }
    }
    
    @GetMapping("/active")
    @ResponseBody
    public List<PromotionEntity> getActivePromotions() {
        try {
            return promotionService.getActivePromotions();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
        }
    }
    
    @PutMapping("/{id}")
    @ResponseBody
    public PromotionEntity updatePromotion(@PathVariable String id, @RequestBody PromotionEntity updatedPromotion) {
        try {
            return promotionService.updatePromotion(id, updatedPromotion);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePromotion(@PathVariable String id) {
        try {
            promotionService.deletePromotion(id);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @PutMapping("/{id}/activate")
    @ResponseBody
    public PromotionEntity activatePromotion(@PathVariable String id) {
        try {
            PromotionEntity promotion = promotionService.getPromotionById(id);
            promotion.setActive(true);
            return promotionService.updatePromotion(id, promotion);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
    
    @PutMapping("/{id}/deactivate")
    @ResponseBody
    public PromotionEntity deactivatePromotion(@PathVariable String id) {
        try {
            PromotionEntity promotion = promotionService.getPromotionById(id);
            promotion.setActive(false);
            return promotionService.updatePromotion(id, promotion);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
}