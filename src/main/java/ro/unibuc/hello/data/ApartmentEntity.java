package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.stream.Collectors;
@Document(collection = "apartments")
public class ApartmentEntity {
    @Id
    private String id;
    private String title;
    private String location;
    private Double pricePerNight;
    private String userId;  // ID-ul utilizatorului care deține apartamentul

    private List<String> bookings; // Lista de ID-uri ale rezervărilor
    private List<String> reviews;  // Lista de ID-uri ale recenziilor
    
    // Câmpurile existente
    private int numberOfRooms; // Numărul de camere
    private int numberOfBathrooms; // Numărul de băi
    private boolean isPetFriendly; // Dacă este pet-friendly

    // Noile câmpuri
    private List<String> amenities; // Lista de facilități (ex. "Wi-Fi", "TV", "balcon")
    private Double squareMeters; // Suprafața apartamentului în metri pătrați
    private boolean smokingAllowed; // Indică dacă fumatul este permis

    public ApartmentEntity() {}

    public ApartmentEntity(String title, String location, Double pricePerNight, String userId,
                           int numberOfRooms, int numberOfBathrooms, boolean isPetFriendly,
                           List<String> amenities, Double squareMeters, boolean smokingAllowed) {
        this.title = title;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.userId = userId;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.isPetFriendly = isPetFriendly;
        this.amenities = amenities;
        this.squareMeters = squareMeters;
        this.smokingAllowed = smokingAllowed;
    }

    // Getters și Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(Double pricePerNight) { this.pricePerNight = pricePerNight; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<String> getBookings() { return bookings; }
    public void setBookings(List<String> bookings) { this.bookings = bookings; }

    public List<String> getReviews() { return reviews; }
    public void setReviews(List<String> reviews) { this.reviews = reviews; }

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public int getNumberOfBathrooms() { return numberOfBathrooms; }
    public void setNumberOfBathrooms(int numberOfBathrooms) { this.numberOfBathrooms = numberOfBathrooms; }

    public boolean isPetFriendly() { return isPetFriendly; }
    public void setPetFriendly(boolean petFriendly) { this.isPetFriendly = petFriendly; }

    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) {
        this.amenities = amenities.stream()
            .map(String::toLowerCase) // Transformă în litere mici
            .collect(Collectors.toList());
    }
    public Double getSquareMeters() { return squareMeters; }
    public void setSquareMeters(Double squareMeters) { this.squareMeters = squareMeters; }

    public boolean isSmokingAllowed() { return smokingAllowed; }
    public void setSmokingAllowed(boolean smokingAllowed) { this.smokingAllowed = smokingAllowed; }
}