package ro.unibuc.hello.dto;

import java.util.List;

public class CreateOrderRequest {

    private String userId;
    private List<ProductOrderDTO> productOrders;
    private String status;

    public CreateOrderRequest() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductOrderDTO> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrderDTO> productOrders) {
        this.productOrders = productOrders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
