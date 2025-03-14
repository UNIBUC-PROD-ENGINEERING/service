package ro.unibuc.hello.data;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
public class InventoryEntity {

    @Id
    private String itemId;
    private String name;
    private Integer stock;
    private Integer threshold;

    public InventoryEntity() {}

    public InventoryEntity(String itemId, String name, int stock, int threshold) {
        this.itemId = itemId;
        this.name = name;
        this.stock = stock;
        this.threshold = threshold;
    }

    public String getItemId() {
        return itemId;
    }

    public void setId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return String.format("Inventory[itemId='%s', name='%s', stock=%d, threshold=%d]", 
                itemId, name, stock, threshold);
    }
}
