package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "locations")
public class LocationEntity {
    @Id
    private String id;
    private String name;
    private String address;
    private float rating;
    private float price;
    private int discountPointsRequired;  // Points needed for a discount

    public LocationEntity() {}

    public LocationEntity(String name, String address, int price, int discountPointsRequired) {
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDiscountPointsRequired() {
        return discountPointsRequired;
    }

    public void setDiscountPointsRequired(int discountPointsRequired) {
        this.discountPointsRequired = discountPointsRequired;
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
}