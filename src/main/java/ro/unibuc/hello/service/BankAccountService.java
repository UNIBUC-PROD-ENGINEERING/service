package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.entity.BankAccount;
import ro.unibuc.hello.entity.Client;
import ro.unibuc.hello.repository.BankAccountRepository;
import ro.unibuc.hello.repository.ClientRepository;
import ro.unibuc.hello.config.JwtUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {
    
    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, ClientRepository clientRepository, JwtUtil jwtUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccountById(String id) {
        return bankAccountRepository.findById(id);
    }

    public BankAccount saveBankAccount(String token, BankAccount bankAccount) {
        // Remove "Bearer " prefix if present
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;

        String email = jwtUtil.extractEmail(jwt);
        System.out.println("Extracted Email: " + email); 

        Optional<Client> clientOpt = clientRepository.findByEmail(email);
        if (clientOpt.isEmpty()) {
            System.out.println("Client not found for email: " + email); 
            throw new IllegalArgumentException("Client not found");
        }

        Client client = clientOpt.get();
        System.out.println("Client ID Found: " + client.getId()); 

        bankAccount.setClientId(client.getId());
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);

        client.getBankAccountIds().add(savedAccount.getId());
        clientRepository.save(client);

        return savedAccount;
    }
    
    public void deleteBankAccount(String id) {
        bankAccountRepository.deleteById(id);
    }
}
