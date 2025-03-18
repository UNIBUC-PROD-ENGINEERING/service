package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Document(collection = "rentals")
public class Rental {

    @Id
    private String id;

    @NotBlank
    private String userId;

    @NotBlank
    private String gameId;

    private LocalDate rentDate;
    private LocalDate returnDate;

    public Rental() {
    }

    public Rental(String userId, String gameId, LocalDate rentDate) {
        this.userId = userId;
        this.gameId = gameId;
        this.rentDate = rentDate;
        this.returnDate = null;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
