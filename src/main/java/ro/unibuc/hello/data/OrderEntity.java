package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
public class OrderEntity {
    @Id
    private String id;
    
    @DBRef
    private UserEntity user;
    
    @DBRef
    private List<ProductOrderEntity> productOrders;
    
    private String status;
    
    private LocalDateTime createdAt;
    
    private List<OrderEntity> orderHistory;

    public OrderEntity() {
        this.createdAt = LocalDateTime.now();
    }
    
    public OrderEntity(String id, UserEntity user, List<ProductOrderEntity> productOrders, String status, LocalDateTime createdAt, List<OrderEntity> orderHistory) {
        this.id = id;
        this.user = user;
        this.productOrders = productOrders;
        this.status = status;
        this.createdAt = createdAt;
        this.orderHistory = orderHistory;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
    public List<ProductOrderEntity> getProductOrders() { return productOrders; }
    public void setProductOrders(List<ProductOrderEntity> productOrders) { this.productOrders = productOrders; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<OrderEntity> getOrderHistory() { return orderHistory; }
    public void setOrderHistory(List<OrderEntity> orderHistory) { this.orderHistory = orderHistory; }

    @Override
    public String toString() {
        return String.format(
                "Order[id='%s', user='%s', status='%s', createdAt='%s', historySize='%d']",
                id, user.getId(), status, createdAt, orderHistory != null ? orderHistory.size() : 0);
    }
}