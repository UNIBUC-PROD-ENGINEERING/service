package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "foods")
public class FoodEntity {
    @Id
    private String id;
    private String name;
    private double rating;
    private double price;
    private int discountPointsRequired;

    public FoodEntity() {}

    public FoodEntity(String name, double price, double rating, int discountPointsRequired) {
        this.name = name;
        this.price = price;
        this.rating = rating;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscountPointsRequired() {
        return discountPointsRequired;
    }

    public void setDiscountPointsRequired(int discountPointsRequired) {
        this.discountPointsRequired = discountPointsRequired;
    }

}