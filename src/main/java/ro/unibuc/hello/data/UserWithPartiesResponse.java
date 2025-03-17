package ro.unibuc.hello.data;

import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.UserEntity;

import java.util.List;

public class UserWithPartiesResponse {
    private String userId;
    private String userName;
    private String email;
    private int points;
    private List<PartyEntity> parties;

    public UserWithPartiesResponse(UserEntity user, List<PartyEntity> parties) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.email = user.getEmail();
        this.points = user.getPoints();
        this.parties = parties;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return points;
    }

    public List<PartyEntity> getParties() {
        return parties;
    }
}
