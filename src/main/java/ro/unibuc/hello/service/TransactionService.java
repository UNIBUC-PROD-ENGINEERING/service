package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.product.ProductEntity;
import ro.unibuc.hello.data.product.ProductRepository;
import ro.unibuc.hello.data.promotions.PromotionEntity;
import ro.unibuc.hello.data.promotions.PromotionRepository;
import ro.unibuc.hello.data.transaction.TransactionDTO;
import ro.unibuc.hello.data.transaction.TransactionEntity;
import ro.unibuc.hello.data.transaction.TransactionEntry;
import ro.unibuc.hello.data.transaction.TransactionRepository;
import ro.unibuc.hello.data.loyalty.LoyaltyCardEntity;
import ro.unibuc.hello.data.loyalty.LoyaltyCardRepository;

import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private PromotionRepository promotionRepository;
    
    public TransactionEntity createTransaction(TransactionDTO transaction) throws Exception {
        // Verifică dacă utilizatorul există
        userService.getUserById(transaction.getUserId());
        
        // Inițializează entitatea tranzacției
        TransactionEntity transactionToSave = new TransactionEntity();
        transactionToSave.setId(UUID.randomUUID().toString());
        transactionToSave.setUserId(transaction.getUserId());
        transactionToSave.setProductsList(transaction.getProductsList());
        transactionToSave.setLoyaltyCardId(transaction.getLoyaltyCardId());
        transactionToSave.setUseDiscount(transaction.isUseDiscount());
        transactionToSave.setDate(LocalDateTime.now());
        
        // Calculează suma totală a tranzacției
        double totalAmount = 0.0;
        
        // Grupează produsele după categorie pentru aplicarea promoțiilor
        Map<String, List<ProductWithQuantity>> productsByCategory = new HashMap<>();
        
        // Procesează fiecare produs din tranzacție
        for (TransactionEntry entry : transactionToSave.getProductsList()) {
            ProductEntity product = productRepository.findById(entry.getProductId())
                    .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
            
            // Verifică stocul disponibil
            if (product.stockSize >= entry.getProductQuantity()) {
                product.stockSize -= entry.getProductQuantity();
                
                // Adaugă la suma totală (înainte de promoții)
                totalAmount += product.price * entry.getProductQuantity();
                
                // Grupează produsele pentru promoții
                String category = product.getCategory();
                if (!productsByCategory.containsKey(category)) {
                    productsByCategory.put(category, new ArrayList<>());
                }
                
                // Adaugă produsul de entry.productQuantity ori
                for (int i = 0; i < entry.getProductQuantity(); i++) {
                    productsByCategory.get(category).add(new ProductWithQuantity(product, 1));
                }
                
                productRepository.save(product);
            } else {
                throw new Exception(HttpStatus.BAD_REQUEST.toString());
            }
        }
        
        // Aplică promoțiile disponibile
        double promotionDiscount = applyPromotions(productsByCategory);
        
        // Setează suma totală
        transactionToSave.setTotalAmount(totalAmount);
        
        // Aplică discount-ul din carduri de fidelitate
        double loyaltyDiscount = 0;
        if (transaction.isUseDiscount() && transaction.getLoyaltyCardId() != null) {
            try {
                loyaltyDiscount = loyaltyCardService.calculateDiscount(transaction.getLoyaltyCardId(), totalAmount - promotionDiscount);
                // Adaugă puncte pe card
                int pointsToAdd = (int)((totalAmount - promotionDiscount - loyaltyDiscount) / 10);
                loyaltyCardService.addPoints(transaction.getLoyaltyCardId(), pointsToAdd);
            } catch (Exception e) {
                loyaltyDiscount = 0;
            }
        }
        
        // Calculează sumele finale
        transactionToSave.setPromotionDiscount(promotionDiscount);
        transactionToSave.setLoyaltyDiscount(loyaltyDiscount);
        transactionToSave.setTotalDiscount(promotionDiscount + loyaltyDiscount);
        transactionToSave.setFinalAmount(totalAmount - transactionToSave.getTotalDiscount());
        
        // Salvează tranzacția
        return transactionRepository.save(transactionToSave);
    }
    
    private double applyPromotions(Map<String, List<ProductWithQuantity>> productsByCategory) {
        double totalDiscount = 0;
        
        // Obține toate promoțiile active
        List<PromotionEntity> activePromotions = promotionRepository.findByActiveTrue();
        
        for (PromotionEntity promotion : activePromotions) {
            if (promotion.getType() == PromotionEntity.PromotionType.BUY_X_GET_Y_FREE) {
                // Aplică promoția "cumperi X, primești Y gratis"
                for (String category : promotion.getApplicableCategories()) {
                    if (productsByCategory.containsKey(category)) {
                        List<ProductWithQuantity> products = productsByCategory.get(category);
                        
                        // Sortează produsele în funcție de preț (crescător)
                        products.sort(Comparator.comparingDouble(p -> p.getProduct().getPrice()));
                        
                        int totalItems = products.size();
                        int setsCount = totalItems / (promotion.getBuyQuantity() + promotion.getFreeQuantity());
                        
                        // Aplică discount pentru produsele gratuite (cele mai ieftine)
                        for (int i = 0; i < setsCount * promotion.getFreeQuantity(); i++) {
                            int index = promotion.getBuyQuantity() * (i / promotion.getFreeQuantity() + 1) + i % promotion.getFreeQuantity();
                            if (index < totalItems) {
                                ProductWithQuantity freeProduct = products.get(index);
                                totalDiscount += freeProduct.getProduct().getPrice();
                            }
                        }
                    }
                }
            }
            // Poți adăuga și alte tipuri de promoții aici
        }
        
        return totalDiscount;
    }
    
    // Restul metodelor
    
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
        for (TransactionEntry entry : transaction.getProductsList()) {
            ProductEntity product = productRepository.findById(entry.getProductId())
                    .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
            
            product.stockSize += entry.getProductQuantity();
            productRepository.save(product);
        }
        
        // Dacă s-au adăugat puncte pe card, le scădem
        if (transaction.isUseDiscount() && transaction.getLoyaltyCardId() != null) {
            try {
                LoyaltyCardEntity card = loyaltyCardRepository.findById(transaction.getLoyaltyCardId())
                        .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
                
                // Scade punctele (1 punct pentru fiecare 10 unități monetare)
                int pointsToRemove = (int)(transaction.getFinalAmount() / 10);
                card.setPoints(Math.max(0, card.getPoints() - pointsToRemove));
                
                loyaltyCardRepository.save(card);
            } catch (Exception e) {
                // Dacă cardul nu mai există, continuă fără ajustarea punctelor
            }
        }
        
        // Șterge tranzacția
        transactionRepository.deleteById(id);
    }
    
    // Clasă helper pentru procesarea promoțiilor
    private static class ProductWithQuantity {
        private ProductEntity product;
        private int quantity;
        
        public ProductWithQuantity(ProductEntity product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
        
        public ProductEntity getProduct() {
            return product;
        }
        
        public int getQuantity() {
            return quantity;
        }
    }
}