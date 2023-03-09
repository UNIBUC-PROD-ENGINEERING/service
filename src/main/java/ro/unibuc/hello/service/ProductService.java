package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.product.ProductDTO;
import ro.unibuc.hello.data.product.ProductEntity;
import ro.unibuc.hello.data.product.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void insertProduct(ProductDTO product) throws Exception {
        ProductEntity productToSave = new ProductEntity();

        productToSave.id = UUID.randomUUID().toString();
        productToSave.name = product.name;
        productToSave.price = product.price;
        productToSave.stockSize = product.stockSize;

        if (productToSave.stockSize < 0) {
            throw new Exception(HttpStatus.BAD_REQUEST.toString());
        }
        else if (productToSave.stockSize == 0) {
            productToSave.inStock = false;
        }
        else {
            productToSave.inStock = true;
        }
        productRepository.save(productToSave);
    }

    public ProductEntity getProductById(String id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public void updateProductById(String id, ProductDTO updateProduct) throws Exception {
        ProductEntity productInDb = getProductById(id);

        productInDb.name = updateProduct.name;
        productInDb.price = updateProduct.price;
        productInDb.stockSize = updateProduct.stockSize;

        if (productInDb.stockSize < 0) {
            throw new Exception(HttpStatus.BAD_REQUEST.toString());
        }
        else if (productInDb.stockSize == 0) {
            productInDb.inStock = false;
        }
        else {
            productInDb.inStock = true;
        }

        productRepository.save(productInDb);
    }

    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }
}
