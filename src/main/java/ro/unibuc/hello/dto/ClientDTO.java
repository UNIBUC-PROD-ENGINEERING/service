package ro.unibuc.hello.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.data.ClientEntity;
import ro.unibuc.hello.data.DishesEntity;
import ro.unibuc.hello.data.OrderEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDTO {
    private String id;
    private String name;
    private String email;
    private String address;

    @DBRef(lazy = true)
    private ArrayList<OrderDTO> orders;

    public ClientDTO() {}

    public ClientDTO(ClientEntity client){
        id = client.getId();
        address = client.getAddress();
        name = client.getName();
        email = client.getEmail();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderDTO> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return String.format(
                "Client[name='%s', email='%s', address='%s']",
                name, email, address);
    }
}
