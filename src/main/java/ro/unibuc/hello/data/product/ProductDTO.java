package ro.unibuc.hello.data.product;

public class ProductDTO {
    public String name;
    public float price;
    public int stockSize;

    public ProductDTO(String name, float price, int stockSize) {
        this.name = name;
        this.price = price;
        this.stockSize = stockSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStockSize() {
        return stockSize;
    }

    public void setStockSize(int stockSize) {
        this.stockSize = stockSize;
    }
}
