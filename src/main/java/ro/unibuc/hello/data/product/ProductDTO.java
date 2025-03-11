package ro.unibuc.hello.data.product;

public class ProductDTO{
    public String name;
    public float price;
    public int stockSize;
    public String category;
    public String brand;
    public String description;

    public ProductDTO(String name, float price, int stockSize, String category, String brand, String description){
        this.name = name;
        this.price = price;
        this.stockSize = stockSize;
        this.category = category;
        this.brand = brand;
        this.description = description;
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

    public int getStockSize() {
        return stockSize;
    }
    public void setStockSize(int stockSize){
        this.stockSize = stockSize;
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
}