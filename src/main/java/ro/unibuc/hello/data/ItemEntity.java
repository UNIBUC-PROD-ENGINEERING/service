package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
public class ItemEntity {

    @Id
    private String id;

    private String name;
    private String description;

    @DocumentReference
    private UserEntity owner;

    public ItemEntity() {}

    public ItemEntity(String name, String description, UserEntity owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public ItemEntity(String id, String name, String description, UserEntity owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
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

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public UserEntity getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Item name : " + name + "\nItem description : " + description + "\n";
    }
}
