package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "users")
public class UserEntity {
    @Id
    private String id;  // MongoDB folosește String pentru ID

    private String name;
    private String email;
    private List<String> apartments; // Lista de ID-uri ale apartamentelor închiriate

    public UserEntity() {}

    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getApartments() { return apartments; }
    public void setApartments(List<String> apartments) { this.apartments = apartments; }
}
