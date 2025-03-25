package ro.unibuc.hello.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class RentRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Game ID is required")
    private String gameId;

    @Positive(message = "Rent Days can't be less than 1")
    private int rentDays;

    // Default constructor
    public RentRequest() {
    }

    // Constructor with fields
    public RentRequest(String userId, String gameId, int rentDays) {
        this.userId = userId;
        this.gameId = gameId;
        this.rentDays = rentDays;
    }

    // Getters and Setters
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

    public int getRentDays(){return rentDays;}

    public void setRentDays(int rentDays){this.rentDays = rentDays;}
}