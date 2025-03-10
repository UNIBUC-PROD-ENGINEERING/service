package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "robots")
public class RobotEntity {

    @Id
    private String id;
    private String status;
    private String currentOrderId;
    private Integer completedOrders;
    private String errors;
    private LocalDateTime lastUpdatedAt;  

    public RobotEntity() {}

    public RobotEntity(String status, String currentOrderId, Integer completedOrders, String errors) {
        this.status = status;
        this.currentOrderId = currentOrderId;
        this.completedOrders = completedOrders;
        this.errors = errors;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(String currentOrderId) {
        this.currentOrderId = currentOrderId;
    }

    public Integer getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Integer completedOrders) {
        this.completedOrders = completedOrders;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @Override
    public String toString() {
        return String.format("Robot[id='%s', status='%s', currentOrderId='%s', completedOrders=%d, errors='%s', lastUpdatedAt='%s']",
                id, status, currentOrderId, completedOrders, errors, lastUpdatedAt);
    }
}
