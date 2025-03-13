package ro.unibuc.hello.dto;

public class Item {
    private String name;
    private String description;

    private String ownerUsername;

    public String getOwner() {
        return ownerUsername;
    }

    public void setOwner(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Item(){}

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Item(String name, String description, String ownerUsername) {
        this.name = name;
        this.description = description;
        this.ownerUsername = ownerUsername;
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
}