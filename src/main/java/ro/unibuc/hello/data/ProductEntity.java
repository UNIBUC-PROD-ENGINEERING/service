package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ProductEntity {

    @Id
    public String id;

    public String name;
    public String description;
    public String categories;

    public ProductEntity() {}

    public ProductEntity(String name, String description, String categories) {
        this.name = name;
        this.description = description;
        this.categories = categories;
    }


    @Override
    public String toString() {
        return String.format(
                "Product[id='%s', name='%s', description='%s', categories='%s']",
                id, name, description, categories);
    }

}