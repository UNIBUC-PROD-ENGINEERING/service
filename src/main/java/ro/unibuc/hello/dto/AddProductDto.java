package ro.unibuc.hello.dto;

public class AddProductDto {
    public String title;
    public String description;
    public int quantity;
    public AddProductDto(String title, String description, int quantity) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
    }
}
