package ro.unibuc.hello.data.transaction;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

public class TransactionEntity {
    @Id
    public String id;
    public String userId;
    public List<TransactionEntry> productsList;

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
}