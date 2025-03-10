package ro.unibuc.hello.dto;

public class InventoryDTO {

    private String id;
    private String name;
    private Integer stock;
    private Integer threshold;

    public InventoryDTO() {}

    public InventoryDTO(String id, String name, Integer stock, Integer threshold) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.threshold = threshold;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getThreshold() { return threshold; }
    public void setThreshold(Integer threshold) { this.threshold = threshold; }
}
