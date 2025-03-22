package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "items")
public class ItemEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private double initialPrice;
    private LocalDateTime endTime;
    private boolean active;
    private LocalDateTime createdAt;
    private String creator;
    private Category category;

    public ItemEntity() {
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public ItemEntity(String name, String description, double initialPrice, LocalDateTime endTime, String creator, Category category) {
        this();
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
        this.endTime = endTime;
        this.creator = creator;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
                "Item[id='%s', name='%s', initialPrice='%.2f', active='%s']",
                id, name, initialPrice, active);
    }
}