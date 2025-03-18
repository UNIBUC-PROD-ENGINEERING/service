package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.ProductOrderDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOrderService {
    
    @Autowired
    private ProductOrderRepository productOrderRepository;
    
    public List<ProductOrderDTO> getAllProductOrders() {
        return productOrderRepository.findAll().stream()
                .map(po -> new ProductOrderDTO(po.getId(), po.getProduct().getId(), po.getQuantity(), po.getOrderedAt()))
                .collect(Collectors.toList());
    }
    
    public ProductOrderDTO createProductOrder(ProductOrderDTO productOrderDTO) {
        ProductOrderEntity productOrder = new ProductOrderEntity(
                null, new ProductEntity(productOrderDTO.getProductId(), "", "", 0L, 0L), productOrderDTO.getQuantity(), productOrderDTO.getOrderedAt()
        );
        productOrderRepository.save(productOrder);
        return new ProductOrderDTO(productOrder.getId(), productOrder.getProduct().getId(), productOrder.getQuantity(), productOrder.getOrderedAt());
    }
}

