package ro.unibuc.hello.dto;

public class ProductDTO {

    private String id;
    private String description;
    private Long price;
    private Long stock;

    public ProductDTO() {}

    public ProductDTO(String description, Long price, Long stock) {
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public ProductDTO(String id, String description, Long price, Long stock) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
