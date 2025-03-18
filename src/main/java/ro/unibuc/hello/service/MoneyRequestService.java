package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.entity.MoneyRequest;
import ro.unibuc.hello.entity.Transaction;
import ro.unibuc.hello.repository.MoneyRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MoneyRequestService {

    private final MoneyRequestRepository moneyRequestRepository;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    public MoneyRequestService(MoneyRequestRepository moneyRequestRepository) {
        this.moneyRequestRepository = moneyRequestRepository;
    }

    public List<MoneyRequest> getAllRequests() {
        return moneyRequestRepository.findAll();
    }

    public Optional<MoneyRequest> getRequestById(String id) {
        return moneyRequestRepository.findById(id);
    }

    public List<MoneyRequest> getRequestsForUser(String toAccountId) {
        return moneyRequestRepository.findByToAccountId(toAccountId);
    }

    public MoneyRequest createRequest(MoneyRequest request) {
        request.setStatus("PENDING");
        return moneyRequestRepository.save(request);
    }


    public MoneyRequest updateRequestStatus(String requestId, String status) {
        Optional<MoneyRequest> existingRequest = moneyRequestRepository.findById(requestId);
        if (existingRequest.isEmpty()) {
            throw new IllegalArgumentException("Request not found");
        }
    
        MoneyRequest request = existingRequest.get();
        request.setStatus(status);
        moneyRequestRepository.save(request);
    
        System.out.println("✅ Money request status updated: " + request); // Debugging Log
    
        // If approved, create a transaction
        if (status.equals("APPROVED")) {
            System.out.println("🚀 Creating a transaction for approved request...");
            
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(request.getToAccountId()); // The one sending money
            transaction.setToAccountId(request.getFromAccountId()); // The one receiving money
            transaction.setAmount(request.getAmount());
    
            transactionService.saveTransaction(transaction);
            
            System.out.println("✅ Transaction successfully created: " + transaction);
        }
    
        return request;
    }
}
