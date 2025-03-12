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
import ro.unibuc.hello.data.loyalty.LoyaltyCardEntity;
import ro.unibuc.hello.data.loyalty.LoyaltyCardRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LoyaltyCardService loyaltyCardService;
    
    @Autowired
    private LoyaltyCardRepository loyaltyCardRepository;
    
    public TransactionEntity createTransaction(TransactionDTO transaction) throws Exception {
        // Verifică dacă utilizatorul există
        userService.getUserById(transaction.userId);
        
        // Inițializează entitatea tranzacției
        TransactionEntity transactionToSave = new TransactionEntity();
        transactionToSave.id = UUID.randomUUID().toString();
        transactionToSave.userId = transaction.userId;
        transactionToSave.productsList = transaction.productsList;
        transactionToSave.loyaltyCardId = transaction.loyaltyCardId;
        transactionToSave.useDiscount = transaction.useDiscount;
        transactionToSave.date = LocalDateTime.now();
        
        // Calculează suma totală a tranzacției
        double totalAmount = 0.0;
        
        // Procesează fiecare produs din tranzacție
        for (TransactionEntry entry : transactionToSave.productsList) {
            ProductEntity product = productRepository.findById(entry.productId)
                    .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
            
            // Verifică stocul disponibil
            if (product.stockSize >= entry.productQuantity) {
                product.stockSize -= entry.productQuantity;
                
                // Adaugă la suma totală
                totalAmount += product.price * entry.productQuantity;
            } else {
                throw new Exception(HttpStatus.BAD_REQUEST.toString());
            }
            
            productRepository.save(product);
        }
        
        // Setează suma totală
        transactionToSave.totalAmount = totalAmount;
        
        // Aplică discount dacă este cazul
        if (transaction.useDiscount && transaction.loyaltyCardId != null) {
            try {
                // Obține discount-ul din cardul de fidelitate
                double discountAmount = loyaltyCardService.calculateDiscount(transaction.loyaltyCardId, totalAmount);
                transactionToSave.discountAmount = discountAmount;
                transactionToSave.finalAmount = totalAmount - discountAmount;
                
                // Adaugă puncte pe card (1 punct pentru fiecare 10 unități monetare cheltuite)
                int pointsToAdd = (int)(transactionToSave.finalAmount / 10);
                loyaltyCardService.addPoints(transaction.loyaltyCardId, pointsToAdd);
            } catch (Exception e) {
                // Dacă cardul nu există, continuă fără discount
                transactionToSave.discountAmount = 0;
                transactionToSave.finalAmount = totalAmount;
            }
        } else {
            transactionToSave.discountAmount = 0;
            transactionToSave.finalAmount = totalAmount;
        }
        
        // Salvează tranzacția
        return transactionRepository.save(transactionToSave);
    }
    
    public TransactionEntity getTransactionById(String id) throws Exception {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
    }
    
    public List<TransactionEntity> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public List<TransactionEntity> getTransactionsByUser(String userId) throws Exception {
        // Verifică dacă utilizatorul există
        userService.getUserById(userId);
        return transactionRepository.findByUserId(userId);
    }
    
    public List<TransactionEntity> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByDateBetween(startDate, endDate);
    }
    
    public void deleteTransaction(String id) throws Exception {
        TransactionEntity transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
        
        // Reintrodu produsele în stoc
        for (TransactionEntry entry : transaction.productsList) {
            ProductEntity product = productRepository.findById(entry.productId)
                    .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
            
            product.stockSize += entry.productQuantity;
            productRepository.save(product);
        }
        
        // Dacă s-au adăugat puncte pe card, le scădem
        if (transaction.useDiscount && transaction.loyaltyCardId != null) {
            try {
                LoyaltyCardEntity card = loyaltyCardRepository.findById(transaction.loyaltyCardId)
                        .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
                
                // Scade punctele (1 punct pentru fiecare 10 unități monetare)
                int pointsToRemove = (int)(transaction.finalAmount / 10);
                card.setPoints(Math.max(0, card.getPoints() - pointsToRemove));
                
                loyaltyCardRepository.save(card);
            } catch (Exception e) {
                // Dacă cardul nu mai există, continuă fără ajustarea punctelor
            }
        }
        
        // Șterge tranzacția
        transactionRepository.deleteById(id);
    }
}