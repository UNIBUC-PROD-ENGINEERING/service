package ro.unibuc.hello.data.product;

import org.springframework.data.annotation.Id;

public class ProductEntity {
    @Id
    public String id;
    public String name;
    public float price;
    public boolean inStock;
    public int stockSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getStockSize() {
        return stockSize;
    }

    public void setStockSize(int stockSize) {
        this.stockSize = stockSize;
    }

    public ProductEntity() {
    }
    public ProductEntity(String id, String name, float price, boolean inStock, int stockSize) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.stockSize = stockSize;
    }
    @Override
    public String toString() {
        return "ProductEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", stockSize=" + stockSize +
                '}';
    }
}
