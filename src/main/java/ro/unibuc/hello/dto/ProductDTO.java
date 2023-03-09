package ro.unibuc.hello.dto;

import lombok.*;

@Builder
public class ProductDTO {
    private String productName;
    private String productDescription;
    private float price;
    private String brandName;
    private Long stock;
    private CategoryDTO categoryDTO;
}
