package ro.unibuc.contact.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserEntity {

    @Id
    public String id;

    public String username;
    public String email;
    public String password;

    public UserEntity() {}

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User[username='%s', email='%s', password='%s']",
                id, username, email, password);
    }

}