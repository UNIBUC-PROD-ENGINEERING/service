package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
public class InventoryEntity {

    @Id
    private String id;
    private String name;
    private Integer stock;
    private Integer threshold;

    public InventoryEntity() {}

    public InventoryEntity(String name, Integer stock, Integer threshold) {
        this.name = name;
        this.stock = stock;
        this.threshold = threshold;
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
        return String.format("Inventory[id='%s', name='%s', stock=%d, threshold=%d]", 
                id, name, stock, threshold);
    }
}
