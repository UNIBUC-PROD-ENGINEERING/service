package ro.unibuc.hello.data.transaction;

import java.util.List;

public class TransactionDTO {
    public String userId;
    public List<TransactionEntry> productsList;
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
}