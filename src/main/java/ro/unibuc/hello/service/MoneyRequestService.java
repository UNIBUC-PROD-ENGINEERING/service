package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.entity.MoneyRequest;
import ro.unibuc.hello.entity.Transaction;
import ro.unibuc.hello.entity.BankAccount;
import ro.unibuc.hello.repository.BankAccountRepository;
import ro.unibuc.hello.repository.MoneyRequestRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MoneyRequestService {

    private final MoneyRequestRepository moneyRequestRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

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

        if (!request.getStatus().equals("PENDING")) {
            throw new IllegalArgumentException("Money request is already processed.");
        }

        request.setStatus(status);
        moneyRequestRepository.save(request);

        Optional<BankAccount> senderAccountOpt = bankAccountRepository.findById(request.getToAccountId());
        Optional<BankAccount> receiverAccountOpt = bankAccountRepository.findById(request.getFromAccountId());

        if (senderAccountOpt.isEmpty() || receiverAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("Bank account not found.");
        }

        BankAccount senderAccount = senderAccountOpt.get();  
        BankAccount receiverAccount = receiverAccountOpt.get();  

        if (status.equals("APPROVED")) {
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(request.getToAccountId()); 
            transaction.setToAccountId(request.getFromAccountId()); 
            transaction.setAmount(request.getAmount());

            Transaction savedTransaction = transactionService.saveTransaction(transaction, false);


            notificationService.createNotification(senderAccount.getClientId(),
                "Ai trimis " + transaction.getAmount() + " RON către contul " + receiverAccount.getIBAN());

            notificationService.createNotification(receiverAccount.getClientId(),
                "Ai primit " + transaction.getAmount() + " RON de la contul " + senderAccount.getIBAN());
        } 
        else if (status.equals("DECLINED")) {
            notificationService.createNotification(senderAccount.getClientId(),
                "Cererea ta de bani a fost refuzată de către utilizatorul destinat.");
        }

        return request;
    }
}
