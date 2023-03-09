package ro.unibuc.hello.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductEntity {
    @Id
    private String id;
    private String productName;
    private String productDescription;
    private float price;
    private String brandName;
    private Long stock;
    @DBRef
    private CategoryEntity category;
}
