package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "bids")
public class BidEntity {

    @Id
    private String id;

    private String itemId;
    private String bidderName;
    private double amount;
    private LocalDateTime createdAt;
    private String email;

    public BidEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public BidEntity(String itemId, String bidderName, double amount, String email) {
        this();
        this.itemId = itemId;
        this.bidderName = bidderName;
        this.amount = amount;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format(
                "Bid[id='%s', itemId='%s', bidderName='%s', amount='%.2f']",
                id, itemId, bidderName, amount);
    }
}