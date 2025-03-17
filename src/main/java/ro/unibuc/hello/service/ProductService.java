package ro.unibuc.hello.service;

import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final AtomicLong counter = new AtomicLong();

    public ProductDTO getProductById(String id) throws EntityNotFoundException {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        ProductEntity product = optionalProduct.orElseThrow(() -> new EntityNotFoundException(id));
        return new ProductDTO(product.getId(), product.getDescription(), product.getPrice(), product.getStock());
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductDTO(product.getId(), product.getDescription(), product.getPrice(), product.getStock()))
                .collect(Collectors.toList());
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        ProductEntity product = new ProductEntity(
                Long.toString(counter.incrementAndGet()),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getStock()
        );
        productRepository.save(product);
        return new ProductDTO(product.getId(), product.getDescription(), product.getPrice(), product.getStock());
    }

    public List<ProductDTO> saveAll(List<ProductDTO> productDTOs) {
        List<ProductEntity> products = productDTOs.stream()
                .map(dto -> new ProductEntity(
                        Long.toString(counter.incrementAndGet()),
                        dto.getDescription(),
                        dto.getPrice(),
                        dto.getStock()
                ))
                .collect(Collectors.toList());

        List<ProductEntity> savedProducts = productRepository.saveAll(products);

        return savedProducts.stream()
                .map(product -> new ProductDTO(product.getId(), product.getDescription(), product.getPrice(), product.getStock()))
                .collect(Collectors.toList());
    }

    public ProductDTO updateProduct(String id, ProductDTO productDTO) throws EntityNotFoundException {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        productRepository.save(product);
        
        return new ProductDTO(product.getId(), product.getDescription(), product.getPrice(), product.getStock());
    }

    public void deleteProduct(String id) throws EntityNotFoundException {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        productRepository.delete(product);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
