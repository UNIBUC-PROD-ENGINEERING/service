package ro.unibuc.hello.dto;

import java.time.LocalDateTime;

public class ProductOrderDTO {
    private String id;
    private String productId;
    private int quantity;
    private LocalDateTime orderedAt;

    public ProductOrderDTO() {}

    public ProductOrderDTO(String id, String productId, int quantity, LocalDateTime orderedAt) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.orderedAt = orderedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDateTime getOrderedAt() { return orderedAt; }
    public void setOrderedAt(LocalDateTime orderedAt) { this.orderedAt = orderedAt; }

    @Override
    public String toString() {
        return String.format(
                "ProductOrderDTO[id='%s', productId='%s', quantity='%d', orderedAt='%s']",
                id, productId, quantity, orderedAt);
    }
}
