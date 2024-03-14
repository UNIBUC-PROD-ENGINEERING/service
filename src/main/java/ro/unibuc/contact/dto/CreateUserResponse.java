package ro.unibuc.contact.dto;

public class CreateUserResponse {

    private String userId;

    private String message;

    public CreateUserResponse() {

    }

    public CreateUserResponse(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
