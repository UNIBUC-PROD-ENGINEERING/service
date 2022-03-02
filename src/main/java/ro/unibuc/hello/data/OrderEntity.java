package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

import java.util.List;

public class OrderEntity {

    @Id
    public String id;

    public String clientId;
    public List<String> meals;
    public Float totalPrice;

    OrderEntity() {}

    public OrderEntity(String id, String clientId, List<String> meals, Float totalPrice) {
        this.id = id;
        this.clientId = clientId;
        this.meals = meals;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", meals=" + meals +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
