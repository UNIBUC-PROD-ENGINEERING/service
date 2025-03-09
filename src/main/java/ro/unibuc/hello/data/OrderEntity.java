package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "orders")
public class OrderEntity {

    @Id
    private String id;
    private String workerId;
    private String status; // "in_progress", "completed", "error"
    private String itemId;
    private int quantity;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String error;
    private boolean stockChecked;

    public OrderEntity() {}

    public OrderEntity(String workerId, String status, String itemId, int quantity, String location) {
        this.workerId = workerId;
        this.status = status;
        this.itemId = itemId;
        this.quantity = quantity;
        this.location = location;
        this.createdAt = LocalDateTime.now();
        this.stockChecked = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getWorkerId() { return workerId; }
    public void setWorkerId(String workerId) { this.workerId = workerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public boolean isStockChecked() { return stockChecked; }
    public void setStockChecked(boolean stockChecked) { this.stockChecked = stockChecked; }

    @Override
    public String toString() {
        return String.format("Order[id='%s', workerId='%s', status='%s', item='%s', quantity=%d, location='%s']", 
                id, workerId, status, itemId, quantity, location);
    }
}
