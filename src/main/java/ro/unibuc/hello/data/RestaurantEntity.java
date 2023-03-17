package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;

public class RestaurantEntity {

    @Id
    private String id;

    private String name;
    private String email;
    private String address;

    @DBRef(lazy = true)
    private ArrayList<OrderEntity> orders;

    public RestaurantEntity(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public RestaurantEntity(String name, String email, String address, ArrayList<OrderEntity> orders) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.orders = orders;
    }

    public RestaurantEntity(){}
    public RestaurantEntity(String id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
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

    public ArrayList<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderEntity> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return String.format(
                "Restaurant[name='%s', email='%s', address='%s']",
                name, email, address);
    }
}