package ro.unibuc.prodeng.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection = "cart")
public class CartEntity{
    @Id
    private String id;
    private String userID;
    private List<CartItem> items;
    private CartStatus status;
    private LocalDateTime createdAt;

    public enum CartStatus {
        OPEN, SUBMITTED
    }

    @Data
    public static class CartItem {
        private String componentId;
        private int quantity;
    }
}