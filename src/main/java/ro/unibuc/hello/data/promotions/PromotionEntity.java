package ro.unibuc.hello.data.promotions;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "promotions")
public class PromotionEntity {
    
    public enum PromotionType {
        BUY_X_GET_Y_FREE,  // ex: cumperi 2, primești 1 gratis
        PERCENT_DISCOUNT,  // reducere procentuală
        FIXED_AMOUNT_DISCOUNT  // reducere sumă fixă
    }
    
    @Id
    private String id;
    private String name;
    private String description;
    private PromotionType type;
    private int buyQuantity;  // câte produse trebuie cumpărate
    private int freeQuantity; // câte produse sunt gratuite
    private double discountValue; // valoarea reducerii (procent sau sumă fixă)
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private String[] applicableCategories; // categoriile de produse eligibile
    
    // Constructori
    public PromotionEntity() {
    }
    
    public PromotionEntity(String name, String description, PromotionType type, 
                          int buyQuantity, int freeQuantity, double discountValue,
                          LocalDateTime startDate, LocalDateTime endDate, 
                          boolean active, String[] applicableCategories) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.applicableCategories = applicableCategories;
    }
    
    // Getters și setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PromotionType getType() {
        return type;
    }

    public void setType(PromotionType type) {
        this.type = type;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public void setFreeQuantity(int freeQuantity) {
        this.freeQuantity = freeQuantity;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String[] getApplicableCategories() {
        return applicableCategories;
    }

    public void setApplicableCategories(String[] applicableCategories) {
        this.applicableCategories = applicableCategories;
    }
}