package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.promotions.PromotionEntity;
import ro.unibuc.hello.data.promotions.PromotionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromotionService {
    
    @Autowired
    private PromotionRepository promotionRepository;
    
    public PromotionEntity createPromotion(PromotionEntity promotion) {
        return promotionRepository.save(promotion);
    }
    
    public PromotionEntity getPromotionById(String id) throws Exception {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
    }
    
    public List<PromotionEntity> getAllPromotions() {
        return promotionRepository.findAll();
    }
    
    public List<PromotionEntity> getActivePromotions() {
        return promotionRepository.findByActiveTrue();
    }
    
    public PromotionEntity updatePromotion(String id, PromotionEntity updatedPromotion) throws Exception {
        PromotionEntity promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
        
        // Actualizează câmpurile
        promotion.setName(updatedPromotion.getName());
        promotion.setDescription(updatedPromotion.getDescription());
        promotion.setType(updatedPromotion.getType());
        promotion.setBuyQuantity(updatedPromotion.getBuyQuantity());
        promotion.setFreeQuantity(updatedPromotion.getFreeQuantity());
        promotion.setDiscountValue(updatedPromotion.getDiscountValue());
        promotion.setStartDate(updatedPromotion.getStartDate());
        promotion.setEndDate(updatedPromotion.getEndDate());
        promotion.setActive(updatedPromotion.isActive());
        promotion.setApplicableCategories(updatedPromotion.getApplicableCategories());
        
        return promotionRepository.save(promotion);
    }
    
    public void deletePromotion(String id) throws Exception {
        if (!promotionRepository.existsById(id)) {
            throw new Exception(HttpStatus.NOT_FOUND.toString());
        }
        promotionRepository.deleteById(id);
    }
}