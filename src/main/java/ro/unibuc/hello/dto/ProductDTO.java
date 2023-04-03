package ro.unibuc.hello.dto;

public class ProductDTO {
    private long id;
    private String name;
    private int quantity;
    private String description;
    private String category;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, int quantity, String description, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
