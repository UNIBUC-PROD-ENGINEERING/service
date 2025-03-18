package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "support_tickets")
public class SupportTicketEntity {
    @Id
    private String id;
    
    @DBRef
    private OrderEntity order;
    
    private String complaint;
    
    private LocalDateTime createdAt;

    public SupportTicketEntity() {
        this.createdAt = LocalDateTime.now();
    }
    
    public SupportTicketEntity(String id, OrderEntity order, String complaint, LocalDateTime createdAt) {
        this.id = id;
        this.order = order;
        this.complaint = complaint;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }
    public String getComplaint() { return complaint; }
    public void setComplaint(String complaint) { this.complaint = complaint; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format(
                "SupportTicket[id='%s', order='%s', complaint='%s', createdAt='%s']",
                id, order.getId(), complaint, createdAt);
    }
}
