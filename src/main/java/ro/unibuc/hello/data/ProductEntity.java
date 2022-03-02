package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ProductEntity {

    @Id
    public String id;

    public String title;
    public String description;
    public int quantity;


    public ProductEntity() {}

    public ProductEntity(String title, String description, int quantity) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "Information[id='%s', title='%s', description='%s', quantity='%s']",
                id, title, description, quantity);
    }
}
