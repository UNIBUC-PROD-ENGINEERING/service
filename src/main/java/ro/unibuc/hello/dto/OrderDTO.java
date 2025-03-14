package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.OrderStatus;

public class OrderDTO {

    private String id;
    private String robotId;
    private OrderStatus status;
    private String itemId;
    private int quantity;
    private String location;

    public OrderDTO(String id, String robotId, OrderStatus status, String itemId, int quantity, String location) {
        this.id = id;
        this.robotId = robotId;
        this.status = status;
        this.itemId = itemId;
        this.quantity = quantity;
        this.location = location;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRobotId() { return robotId; }
    public void setWorkerId(String robotId) { this.robotId = robotId; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
