package ro.unibuc.contact.dto;

public class UserAuthDTO {
    private String username;
    private String password;

    public UserAuthDTO() {
    }

    public UserAuthDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}