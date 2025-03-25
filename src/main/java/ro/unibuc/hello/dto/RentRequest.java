package ro.unibuc.hello.dto;

import jakarta.validation.constraints.NotBlank;

public class RentRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Game ID is required")
    private String gameId;

    // Default constructor
    public RentRequest() {
    }

    // Constructor with fields
    public RentRequest(String userId, String gameId) {
        this.userId = userId;
        this.gameId = gameId;
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
}