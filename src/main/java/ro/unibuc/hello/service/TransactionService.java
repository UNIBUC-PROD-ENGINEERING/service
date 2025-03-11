package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.product.ProductEntity;
import ro.unibuc.hello.data.product.ProductRepository;
import ro.unibuc.hello.data.transaction.TransactionDTO;
import ro.unibuc.hello.data.transaction.TransactionEntity;
import ro.unibuc.hello.data.transaction.TransactionEntry;
import ro.unibuc.hello.data.transaction.TransactionRepository;

import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    public void createTransaction(TransactionDTO transaction) throws Exception {
        TransactionEntity transactionToSave = new TransactionEntity();

        transactionToSave.id = UUID.randomUUID().toString();
        transactionToSave.userId = transaction.userId;
        transactionToSave.productsList = transaction.productsList;

        userService.getUserById(transactionToSave.userId);

        for (TransactionEntry entry : transactionToSave.productsList)
        {
            ProductEntity product = productRepository.findById(entry.productId).orElseThrow(()
                    -> new Exception(HttpStatus.NOT_FOUND.toString()));

            if (product.stockSize >= entry.productQuantity)
            {
                product.stockSize -= entry.productQuantity;
            }
            else
            {
                throw new Exception(HttpStatus.BAD_REQUEST.toString());
            }

            productRepository.save(product);
        }

        transactionRepository.save(transactionToSave);
    }

}