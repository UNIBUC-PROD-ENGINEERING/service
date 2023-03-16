package ro.unibuc.hello.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO implements Serializable {
    private String productName;
    private String productDescription;
    private float price;
    private String brandName;
    private Long stock;
    private CategoryDTO category;
}
