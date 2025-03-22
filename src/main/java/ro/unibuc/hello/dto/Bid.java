package ro.unibuc.hello.dto;

import java.time.LocalDateTime;

public class Bid {
    private String id;
    private String itemId;
    private String bidderName;
    private double amount;
    private LocalDateTime createdAt;
    private String itemName;
    private String email;

    public Bid() {
    }

    public Bid(String id, String itemId, String bidderName, double amount, LocalDateTime createdAt, String email) {
        this.id = id;
        this.itemId = itemId;
        this.bidderName = bidderName;
        this.amount = amount;
        this.createdAt = createdAt;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}