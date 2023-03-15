package ro.unibuc.hello.dto;

public class ClientDTO {
    private long id;
    private String name;
    private String email;
    public ClientDTO() {
    }

    public ClientDTO(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public ClientDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String name) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
