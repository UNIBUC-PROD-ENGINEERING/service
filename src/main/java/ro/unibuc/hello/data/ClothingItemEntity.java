package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ClothingItemEntity {

    @Id
    public String id;
    public String title;
    public String description;
    public String material;
    public String size;
    public int price;
    public ClothingItemEntity() {}

    public ClothingItemEntity(String id,String title, String description, String material, String size, int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.material = material;
        this.size = size;
        this.price = price;
    }


    @Override
    public String toString() {
        return String.format(
                "ClothingItem[title='%s', description='%s', material='%s', size='%s', price='%d']",
                title, description, material, size, price);
    }
}
