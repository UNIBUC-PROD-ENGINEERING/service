package ro.unibuc.hello.dto;

public class OrderDTO {

    private String id;
    private String workerId;
    private String status;
    private String itemId;
    private int quantity;
    private String location;

    public OrderDTO() {}

    public OrderDTO(String id, String workerId, String status, String itemId, int quantity, String location) {
        this.id = id;
        this.workerId = workerId;
        this.status = status;
        this.itemId = itemId;
        this.quantity = quantity;
        this.location = location;
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
}
