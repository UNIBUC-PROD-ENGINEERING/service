package ro.unibuc.hello.dto;

public class ClothingItem {

    public String id;
    public String title;
    public String description;
    public String material;
    public String size;
    public int price;


    public ClothingItem() {
    }

    public ClothingItem(String id,String title, String description, String material, String size, int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.material = material;
        this.size = size;
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMaterial() {
        return material;
    }

    public String getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }
}
