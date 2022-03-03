package ro.unibuc.hello.dto;

public class ProductAddStockDto {
    public String title;
    public int quantity;

    public ProductAddStockDto(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductAddStockDto{" +
                "title='" + title + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
