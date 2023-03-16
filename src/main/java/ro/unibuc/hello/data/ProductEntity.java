package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ProductEntity {

    @Id
    public String id;

    
    public String description;
    public String categories;

    public ProductEntity() {}

    public ProductEntity(String description, String categories) {
        
        this.description = description;
        this.categories = categories;
    }


    @Override
    public String toString() {
        return String.format(
                "Product[productName='%s', description='%s', categories='%s']",
                id, description, categories);
    }

}