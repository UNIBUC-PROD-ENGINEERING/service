package ro.unibuc.hello.dto;

public class InventoryDTO {

    private String itemId; 
    private String name;
    private Integer stock;
    private Integer threshold;

    public InventoryDTO() {}

    public InventoryDTO(String itemId, String name, Integer stock, Integer threshold) {
        this.itemId = itemId;
        this.name = name;
        this.stock = stock;
        this.threshold = threshold;
    }

    public String getItemId() { return itemId; }  
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getThreshold() { return threshold; }
    public void setThreshold(Integer threshold) { this.threshold = threshold; }
}
