package ro.unibuc.tbd.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Client {

    @Id
    public String id;

    public String name;
    public String email;
    public String phoneNumber;
    public String address;

    public Client updateClient(Client other) {
        if (other.getName() != null) {
            this.name = other.name;
        }

        if (other.getEmail() != null) {
            this.email = other.email;
        }

        if (other.getPhoneNumber() != null) {
            this.phoneNumber = other.phoneNumber;
        }

        if (other.getAddress() != null) {
            this.address = other.address;
        }

        return this;
    }
}
