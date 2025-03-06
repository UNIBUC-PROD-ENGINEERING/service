package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "apartments")
public class ApartmentEntity {
    @Id
    private String id;
    private String title;
    private String location;
    private Double pricePerNight;
    private String ownerId;  // ID-ul proprietarului (User)

    private List<String> bookings; // Lista de ID-uri ale rezervărilor
    private List<String> reviews;  // Lista de ID-uri ale recenziilor

    public ApartmentEntity() {}

    public ApartmentEntity(String title, String location, Double pricePerNight, String ownerId) {
        this.title = title;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.ownerId = ownerId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(Double pricePerNight) { this.pricePerNight = pricePerNight; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public List<String> getBookings() { return bookings; }
    public void setBookings(List<String> bookings) { this.bookings = bookings; }

    public List<String> getReviews() { return reviews; }
    public void setReviews(List<String> reviews) { this.reviews = reviews; }
}
