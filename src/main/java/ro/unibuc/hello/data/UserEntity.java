package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class UserEntity {
    @Id
    private String id;

    private String email;
    private String parola;

    public UserEntity(String id, String email, String parola) {
        this.id = id;
        this.email = email;
        this.parola = parola;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
