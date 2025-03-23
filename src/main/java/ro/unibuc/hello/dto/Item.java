package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.ItemEntity;

public class Item {
    private String id;
    private String name;
    private String description;
    private User owner;

    public Item(){}

    public Item(String id, String name, String description, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public Item(ItemEntity entity) {
        this(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            new User(entity.getOwner())
        );
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
