package ro.unibuc.hello.dto;

public class RobotDTO {

    private String id;
    private String status;
    private String currentOrderId;
    private Integer completedOrders;
    private String errors;

    public RobotDTO() {}

    public RobotDTO(String id, String status, String currentOrderId, Integer completedOrders, String errors) {
        this.id = id;
        this.status = status;
        this.currentOrderId = currentOrderId;
        this.completedOrders = completedOrders;
        this.errors = errors;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCurrentOrderId() { return currentOrderId; }
    public void setCurrentOrderId(String currentOrderId) { this.currentOrderId = currentOrderId; }

    public Integer getCompletedOrders() { return completedOrders; }
    public void setCompletedOrders(Integer completedOrders) { this.completedOrders = completedOrders; }

    public String getErrors() { return errors; }
    public void setErrors(String errors) { this.errors = errors; }
}
