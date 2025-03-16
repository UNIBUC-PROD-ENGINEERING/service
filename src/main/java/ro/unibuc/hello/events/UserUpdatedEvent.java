package ro.unibuc.hello.events;

public class UserUpdatedEvent {
    private final String userId;
    private final String newFirstName;
    private final String newLastName;

    public UserUpdatedEvent(String userId, String newFirstName, String newLastName) {
        this.userId = userId;
        this.newFirstName = newFirstName;
        this.newLastName = newLastName;
    }

    public String getUserId() {
        return userId;
    }

    public String getNewFirstName() {
        return newFirstName;
    }

    public String getNewLastName() {
        return newLastName;
    }
}