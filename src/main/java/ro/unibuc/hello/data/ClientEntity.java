package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ClientEntity {

    @Id
    public String id;

    public String name;
    public String email;
    public String phoneNumber;
    public String address;

    ClientEntity() {}

    public ClientEntity(String id, String name, String email, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
