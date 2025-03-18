package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "product_orders")
public class ProductOrderEntity {
    @Id
    private String id;
    
    @DBRef
    private ProductEntity product;
    
    private int quantity;
    
    private LocalDateTime orderedAt;

    public ProductOrderEntity() {
        this.orderedAt = LocalDateTime.now();
    }
    
    public ProductOrderEntity(String id, ProductEntity product, int quantity, LocalDateTime orderedAt) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.orderedAt = orderedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDateTime getOrderedAt() { return orderedAt; }
    public void setOrderedAt(LocalDateTime orderedAt) { this.orderedAt = orderedAt; }

    @Override
    public String toString() {
        return String.format(
                "ProductOrder[id='%s', product='%s', quantity='%d', orderedAt='%s']",
                id, product.getId(), quantity, orderedAt);
    }
}