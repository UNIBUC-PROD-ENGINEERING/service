package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.ProductEntity;

public class ProductDto {
    public String id;

    public String title;
    public String description;
    public int quantity;

    public ProductDto(ProductEntity entity) {
        this.id = entity.id;
        this.title = entity.title;
        this.description = entity.description;
        this.quantity = entity.quantity;
    }
}
