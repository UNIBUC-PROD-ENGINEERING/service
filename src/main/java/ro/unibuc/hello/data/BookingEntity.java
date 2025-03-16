package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "bookings")
public class BookingEntity {

    @Id
    private String id;

    private LocalDate startDate;
    private LocalDate endDate;

    private String apartmentId;  // ID-ul apartamentului rezervat
    private String userId;       // ID-ul utilizatorului care face rezervarea

    // Constructori, getteri și setteri
    public BookingEntity() {}

    public BookingEntity(LocalDate startDate, LocalDate endDate, String apartmentId, String userId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.apartmentId = apartmentId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
