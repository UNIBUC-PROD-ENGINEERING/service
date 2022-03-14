package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

public class Product {

    @Id
    public String productId;

    public String modelName;
    public String colorway;
    public Integer retailPrice;


    public Product(String modelName, String colorway, Integer retailPrice) {
        this.modelName = modelName;
        this.colorway = colorway;
        this.retailPrice = retailPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getColorway() {
        return colorway;
    }

    public void setColorway(String colorway) {
        this.colorway = colorway;
    }

    public Integer getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
    }
}
