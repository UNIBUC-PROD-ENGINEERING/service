package ro.unibuc.hello.data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("client")
public class ClientEntity {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;

    public ClientEntity() {
    }

    public ClientEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format(
                "Client[id='%s', name='%s', email='%s']",
                id, name, email);
    }

}
