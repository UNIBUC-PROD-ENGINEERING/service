package ro.unibuc.hello.dto;

public class User {

    private String name;
    private String username;
    private String Id;

    public User() {}

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public User(String Id, String name, String username) {
        this.Id = Id;
        this.name = name;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("Users: " + "Name: " + name + " Username: " + username);
        return print.toString();
    }
}