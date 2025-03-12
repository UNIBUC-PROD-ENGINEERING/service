package ro.unibuc.hello.data.transaction;

import java.util.List;

public class TransactionDTO {
    public String userId;
    public List<TransactionEntry> productsList;
    public String loyaltyCardId;     // ID-ul cardului de fidelitate
    public boolean useDiscount;      // Flag pentru aplicarea discount-ului
    
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
}