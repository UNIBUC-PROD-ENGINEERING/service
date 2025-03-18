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

        System.out.println("📝 Saving transaction: " + transaction);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // ✅ Find sender's client
        Optional<BankAccount> senderAccountOpt = bankAccountRepository.findById(transaction.getFromAccountId());
        if (senderAccountOpt.isPresent()) {
            String senderClientId = senderAccountOpt.get().getClientId();
            Optional<Client> senderClientOpt = clientRepository.findById(senderClientId);
            if (senderClientOpt.isPresent()) {
                Client senderClient = senderClientOpt.get();
                senderClient.getTransactionIds().add(savedTransaction.getId());
                clientRepository.save(senderClient);
                System.out.println("✅ Added transaction to sender's client: " + senderClient.getId());
            } else {
                System.err.println("❌ Sender Client not found for ID: " + senderClientId);
            }
        } else {
            System.err.println("❌ Sender BankAccount not found for ID: " + transaction.getFromAccountId());
        }

        // ✅ Find receiver's client
        Optional<BankAccount> receiverAccountOpt = bankAccountRepository.findById(transaction.getToAccountId());
        if (receiverAccountOpt.isPresent()) {
            String receiverClientId = receiverAccountOpt.get().getClientId();
            Optional<Client> receiverClientOpt = clientRepository.findById(receiverClientId);
            if (receiverClientOpt.isPresent()) {
                Client receiverClient = receiverClientOpt.get();
                receiverClient.getTransactionIds().add(savedTransaction.getId());
                clientRepository.save(receiverClient);
                System.out.println("✅ Added transaction to receiver's client: " + receiverClient.getId());
            } else {
                System.err.println("❌ Receiver Client not found for ID: " + receiverClientId);
            }
        } else {
            System.err.println("❌ Receiver BankAccount not found for ID: " + transaction.getToAccountId());
        }

        return savedTransaction;
    }

    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }
}
