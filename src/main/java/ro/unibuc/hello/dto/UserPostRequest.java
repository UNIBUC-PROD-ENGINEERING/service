package ro.unibuc.hello.dto;

public class UserPostRequest {

    private String name;
    private String username;
    private String password;

    public UserPostRequest() {}

    public UserPostRequest(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
   
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("Users: " + "Name: " + name + " Username: " + username);
        return print.toString();
    }
}
