package ro.unibuc.hello.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "foods")
public class Food {
    @Id
    private String id;
    private String name;
    private float rating;
    private float price;
    private int discountPointsRequired;

    public Food() {}

    public Food(String name, int price, int discountPointsRequired) {
        this.name = name;
        this.price = price;
        this.discountPointsRequired = discountPointsRequired;
    }

    // Getters and Setters
    
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDiscountPointsRequired() {
        return discountPointsRequired;
    }

    public void setDiscountPointsRequired(int discountPointsRequired) {
        this.discountPointsRequired = discountPointsRequired;
    }

}