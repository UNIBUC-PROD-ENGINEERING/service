package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.entity.Transaction;
import ro.unibuc.hello.entity.BankAccount;
import ro.unibuc.hello.entity.Client;
import ro.unibuc.hello.repository.TransactionRepository;
import ro.unibuc.hello.repository.BankAccountRepository;
import ro.unibuc.hello.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, 
                              BankAccountRepository bankAccountRepository,
                              ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        if (transaction.getFromAccountId() == null || transaction.getToAccountId() == null) {
            throw new IllegalArgumentException("Both sender and receiver accounts must be provided");
        }
    
        Optional<BankAccount> senderAccountOpt = bankAccountRepository.findById(transaction.getFromAccountId());
        Optional<BankAccount> receiverAccountOpt = bankAccountRepository.findById(transaction.getToAccountId());
    
        if (senderAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("Sender bank account not found");
        }
        if (receiverAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("Receiver bank account not found");
        }
    
        BankAccount senderAccount = senderAccountOpt.get();
        BankAccount receiverAccount = receiverAccountOpt.get();
    
        if (senderAccount.getBalance() < transaction.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds in sender’s account");
        }
    
        senderAccount.updateBalance(-transaction.getAmount());
        receiverAccount.updateBalance(transaction.getAmount());
    
        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(receiverAccount);
    
        Transaction savedTransaction = transactionRepository.save(transaction);
    
        return savedTransaction;
    }
    

    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }
}
