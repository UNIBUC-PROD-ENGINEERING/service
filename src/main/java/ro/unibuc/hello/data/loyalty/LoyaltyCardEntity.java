package ro.unibuc.hello.data.loyalty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "loyalty_cards")
public class LoyaltyCardEntity {
    
    public enum CardType {
        BRONZE, GOLD, PREMIUM
    }
    
    @Id
    private String id;
    
    private CardType cardType;
    private int points;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private double discountPercentage;
    private String userId; // Modificat din customerId în userId
    
    public LoyaltyCardEntity() {
    }
    
    public LoyaltyCardEntity(CardType cardType, String userId) {
        this.cardType = cardType;
        this.userId = userId;
        this.points = 0;
        this.issueDate = LocalDate.now();
        this.expiryDate = LocalDate.now().plusYears(2);
        
        // Set discount based on card type
        switch (cardType) {
            case BRONZE:
                this.discountPercentage = 5.0;
                break;
            case GOLD:
                this.discountPercentage = 10.0;
                break;
            case PREMIUM:
                this.discountPercentage = 15.0;
                break;
            default:
                this.discountPercentage = 0.0;
        }
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public CardType getCardType() {
        return cardType;
    }
    
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public void addPoints(int points) {
        this.points += points;
    }
    
    public LocalDate getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "LoyaltyCardEntity{" +
                "id='" + id + '\'' +
                ", cardType=" + cardType +
                ", points=" + points +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                ", discountPercentage=" + discountPercentage +
                ", userId='" + userId + '\'' +
                '}';
    }
}