package ro.unibuc.hello.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private String id;
    private String userId;
    private List<ProductOrderDTO> productOrders;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderDTO> orderHistory;

    public OrderDTO() {}

    public OrderDTO(String id, String userId, List<ProductOrderDTO> productOrders, String status, LocalDateTime createdAt, List<OrderDTO> orderHistory) {
        this.id = id;
        this.userId = userId;
        this.productOrders = productOrders;
        this.status = status;
        this.createdAt = createdAt;
        this.orderHistory = orderHistory;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<ProductOrderDTO> getProductOrders() { return productOrders; }
    public void setProductOrders(List<ProductOrderDTO> productOrders) { this.productOrders = productOrders; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<OrderDTO> getOrderHistory() { return orderHistory; }
    public void setOrderHistory(List<OrderDTO> orderHistory) { this.orderHistory = orderHistory; }
}

