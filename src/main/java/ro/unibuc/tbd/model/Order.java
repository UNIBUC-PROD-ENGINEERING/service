package ro.unibuc.tbd.model;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Order {

    @Id
    public String id;

    public String clientId;
    public Map<String, Integer> meals;
    public Float totalPrice;

    @CreatedDate
    public LocalDateTime createdDate;

    public Order updateOrder(Order other) {
        if (other.getClientId() != null) {
            this.clientId = other.clientId;
        }

        if (other.getMeals() != null) {
            this.meals = other.meals;
        }

        if (other.getTotalPrice() != null) {
            this.totalPrice = other.totalPrice;
        }

        return this;
    }
}
