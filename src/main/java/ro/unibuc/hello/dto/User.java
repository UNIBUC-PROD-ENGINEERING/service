package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.UserEntity;

public class User {

    private String id;
    private String name;

    public User() {}

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(UserEntity entity) {
        this(entity.getId(), entity.getName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "Users: " + "Name: " + name);
        return print.toString();
    }
}
