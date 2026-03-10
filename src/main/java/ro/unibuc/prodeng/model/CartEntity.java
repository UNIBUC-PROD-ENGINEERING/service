package ro.unibuc.prodeng.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "cart")
public class CartEntitiy(
    @Id
    private String id,
    private String userID,
    private List<CartItems> items,
    private CartStatus status,
    private LocalDateTime createdAt

    public enum CartStatus {
        OPEN, SUBMITTED
    }

    @Data
    public static class CartItem {
        private String componentId;
        private int quantity;
    }
)