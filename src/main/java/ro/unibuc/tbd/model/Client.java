package ro.unibuc.tbd.model;

import java.util.HashMap;
import java.util.Map;
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

    public Map<String, Integer> cart;

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

    public void addToCart(String mealId) {
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }

        this.cart.put(mealId, this.cart.getOrDefault(mealId, 0) + 1);
    }

    public void removeFromCart(String mealId) {
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }

        this.cart.remove(mealId);
    }

    public void clearCart() {
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }

        this.cart.clear();
    }
}
