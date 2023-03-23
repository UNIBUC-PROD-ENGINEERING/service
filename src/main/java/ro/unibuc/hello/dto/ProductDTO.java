package ro.unibuc.hello.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    @JsonInclude(Include.NON_NULL)
    private String productId;
    private String productName;
    private String productDescription;
    private float price;
    private String brandName;
    private Long stock;
    private CategoryDTO category;
}
