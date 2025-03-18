package ro.unibuc.hello.dto;

import java.time.LocalDateTime;

public class SupportTicketDTO {
    private String id;
    private String orderId;
    private String complaint;
    private LocalDateTime createdAt;

    public SupportTicketDTO() {}

    public SupportTicketDTO(String id, String orderId, String complaint, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.complaint = complaint;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getComplaint() { return complaint; }
    public void setComplaint(String complaint) { this.complaint = complaint; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format(
                "SupportTicketDTO[id='%s', orderId='%s', complaint='%s', createdAt='%s']",
                id, orderId, complaint, createdAt);
    }
}

