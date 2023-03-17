package ro.unibuc.hello.data;


import org.springframework.data.annotation.Id;

import java.util.Random;

public class ProductEntity {
    @Id
    private long id = new Random().nextLong();
    private String name;
    private int quantity;
    private String description;
    private String category;

    public ProductEntity() {
    }

    public ProductEntity(long id, String name, int quantity, String description, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
