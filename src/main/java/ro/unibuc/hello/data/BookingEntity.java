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
    private String renterId;     // ID-ul utilizatorului care închiriază

    public BookingEntity() {}

    public BookingEntity(LocalDate startDate, LocalDate endDate, String apartmentId, String renterId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.apartmentId = apartmentId;
        this.renterId = renterId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getApartmentId() { return apartmentId; }
    public void setApartmentId(String apartmentId) { this.apartmentId = apartmentId; }

    public String getRenterId() { return renterId; }
    public void setRenterId(String renterId) { this.renterId = renterId; }
}
