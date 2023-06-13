package ro.unibuc.hello.dto;

public class UserDTO {
    private String id;

    private String email;
    private String parola;

    public UserDTO(String id, String email, String parola) {
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
