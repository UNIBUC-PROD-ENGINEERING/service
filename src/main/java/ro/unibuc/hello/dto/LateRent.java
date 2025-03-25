package ro.unibuc.hello.dto;

public class LateRent {
    private String userId;
    private String gameId;
    private String rentDate;

    public LateRent(String userId, String gameId, String rentDate) {
        this.userId = userId;
        this.gameId = gameId;
        this.rentDate = rentDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getRentDate() {
        return rentDate;
    }
}
