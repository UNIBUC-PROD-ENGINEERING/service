package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.mindrot.jbcrypt.BCrypt;

public class UserEntity {
    
    @Id
    private String id;

    private String username;
    private String password;
    private String email;

    public UserEntity() {}

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
    }

    public UserEntity(String username, String id, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
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

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("UserEntity[id=%s, username=%s, email=%s]", id, username, email);
    }
}
