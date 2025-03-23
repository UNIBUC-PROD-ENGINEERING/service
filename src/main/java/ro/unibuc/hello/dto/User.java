package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.UserEntity;

public class User {

    private String id;
    private String name;
    private String username;

    public User() {}

    public User(String id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public User(UserEntity entity) {
        this(entity.getId(), entity.getName(), entity.getUsername());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder(
                "Users: " + "Name: " + name + " Username: " + username);
        return print.toString();
    }
}
