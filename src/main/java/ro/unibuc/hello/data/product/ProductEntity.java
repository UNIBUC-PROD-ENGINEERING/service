package ro.unibuc.hello.data.product;


import org.springframework.data.annotation.Id;

public class ProductEntity {
    @Id
    public String id;
    public String name;
    public float price;
    public boolean inStock;
    public int stockSize;
    public String category;
    public String brand;
    public String description;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public float getPrice(){
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public boolean isInStock(){
        return inStock;
    }

    public void setInStock(boolean inStock){
        this.inStock = inStock;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getBrand(){
        return brand;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public ProductEntity(){}

    public ProductEntity(String id, String name, float price, boolean inStock, int stockSize, String category, String brand, String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.stockSize = stockSize;
        this.category = category;
        this.brand = brand;
        this.description = description;
    }

    @Override
    public String toString(){
        return "ProductEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", stockSize=" + stockSize +
                ", category=" + category +
                ", brand=" + brand +
                ", description=" + description +
                '}';

    }
}
