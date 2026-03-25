package ro.unibuc.prodeng.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "cart")
public class CartEntity{
    @Id
    private String id;
    private String userID;
    private List<CartItem> items;
    private CartStatus status;
    private LocalDateTime createdAt;

    public enum CartStatus { OPEN, SUBMITTED }

    @Data
    public static class CartItem {
        private String componentId;
        private int quantity;
        public CartItem() {}
        public CartItem(String componentId, int quantity) {
            this.componentId = componentId;
            this.quantity = quantity;
        }
    }
}