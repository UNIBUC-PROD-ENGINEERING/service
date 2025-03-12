package ro.unibuc.hello.data.transaction;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "transactions")
public class TransactionEntity {
    @Id
    public String id;
    public String userId;
    public List<TransactionEntry> productsList;
    public String loyaltyCardId;      // ID-ul cardului de fidelitate utilizat
    public boolean useDiscount;       // Flag pentru aplicarea discount-ului
    public LocalDateTime date;        // Data tranzacției
    public double totalAmount;        // Suma totală înainte de discount
    public double discountAmount;     // Valoarea discount-ului aplicat
    public double finalAmount;        // Suma finală după aplicarea discount-ului

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<TransactionEntry> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<TransactionEntry> productsList) {
        this.productsList = productsList;
    }
    
    public String getLoyaltyCardId() {
        return loyaltyCardId;
    }

    public void setLoyaltyCardId(String loyaltyCardId) {
        this.loyaltyCardId = loyaltyCardId;
    }

    public boolean isUseDiscount() {
        return useDiscount;
    }

    public void setUseDiscount(boolean useDiscount) {
        this.useDiscount = useDiscount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
}