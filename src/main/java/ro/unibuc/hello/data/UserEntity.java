package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users") 
public class UserEntity {

    @Id
    private String id;
    private String name;
    private String role; 

    public UserEntity() {}

    public UserEntity(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public UserEntity(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return String.format("User[id='%s', name='%s', role='%s']", id, name, role);
    }
}
